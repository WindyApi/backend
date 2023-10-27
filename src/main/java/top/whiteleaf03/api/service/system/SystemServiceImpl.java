package top.whiteleaf03.api.service.system;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.modal.dto.InterfaceInvokeRecordDTO;
import top.whiteleaf03.api.modal.vo.NodeInfoVO;
import top.whiteleaf03.api.modal.vo.RecentRecordVO;
import top.whiteleaf03.api.modal.vo.SystemInfoVO;
import top.whiteleaf03.api.repository.mongo.InterfaceInvokeRecordMongoRepository;
import top.whiteleaf03.api.util.RedisCache;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.*;

/**
 * @author WhiteLeaf03
 */
@Service
public class SystemServiceImpl implements SystemService {
    private final InterfaceInvokeRecordMongoRepository interfaceInvokeRecordMongoRepository;
    private final RedisCache redisCache;

    @Autowired
    public SystemServiceImpl(InterfaceInvokeRecordMongoRepository interfaceInvokeRecordMongoRepository, RedisCache redisCache) {
        this.interfaceInvokeRecordMongoRepository = interfaceInvokeRecordMongoRepository;
        this.redisCache = redisCache;
    }

    /**
     * 保存接口调用记录
     *
     * @param recordJson 调用记录 InterfaceInvokeRecordDTO的JSON格式
     */
    @Override
//    @RabbitListener(queues = "interface_invoke_record")
    public void saveRecord(String recordJson) {
        InterfaceInvokeRecordDTO interfaceInvokeRecordDTO = JSONUtil.toBean(recordJson, InterfaceInvokeRecordDTO.class);
        interfaceInvokeRecordMongoRepository.save(interfaceInvokeRecordDTO);
    }

    private RecentRecordVO getRecentRecord() {
        final int TIMES = 5;
        final long ONE_MINUTE_MILLIS = 60000L;
        long currentTimeMillis = System.currentTimeMillis() / 60000 * 60000;
        Long[] recentQPS = new Long[TIMES];
        Long[] recentUseTime = new Long[TIMES];
        Double[] recentAcceptRate = new Double[TIMES];
        for (int index = 0; index < TIMES; index++) {
            long maxTime = currentTimeMillis - (index - 1) * ONE_MINUTE_MILLIS;
            long minTime = currentTimeMillis - index * ONE_MINUTE_MILLIS;

            // 计算QPS
            long qps;
            try {
                qps = interfaceInvokeRecordMongoRepository.calculateTotalSumInRange(maxTime, minTime);
            } catch (NullPointerException e) {
                qps = 0L;
            }
            recentQPS[index] = qps;

            // 计算平均耗时
            long totalUseTime;
            try {
                totalUseTime = interfaceInvokeRecordMongoRepository.calculateTotalUseTimeInRange(maxTime, minTime);
            } catch (NullPointerException e) {
                totalUseTime = 0L;
            }
            recentUseTime[index] = recentQPS[index] == 0 ? 0 : totalUseTime / recentQPS[index];

            // 计算通过率 保留4位小数
            long totalAccept;
            try {
                totalAccept = interfaceInvokeRecordMongoRepository.calculateTotalAcceptInRange(maxTime, minTime);
            } catch (NullPointerException e) {
                totalAccept = 0;
            }
            recentAcceptRate[index] = recentQPS[index] == 0 ? 0 : (double) totalAccept / recentQPS[index];
        }
        return new RecentRecordVO(recentQPS, recentUseTime, recentAcceptRate);
    }

    static final List<Map<String, String>> NODE_LIST = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("name", "接口中心");
            put("address", "165.154.161.106:8011");
        }});
        add(new HashMap<String, String>() {{
            put("name", "用户中心");
            put("address", "165.154.161.106:8001");
        }});
        add(new HashMap<String, String>() {{
            put("name", "网关");
            put("address", "165.154.161.106:8081");
        }});
    }};

    private Double getValue(String jsonStr) {
        JSONObject json = JSONUtil.parseObj(jsonStr);
        JSONObject measurement = json.getJSONArray("measurements").getJSONObject(0);
        return measurement.getDouble("value");
    }

    private String getNodeInfo() {
        List<NodeInfoVO> NodeInfoVOList = new ArrayList<>();
        for (Map<String, String> map : NODE_LIST) {
            String memoryInfoJson = HttpUtil.get(map.get("address") + "/actuator/metrics/jvm.memory.used");
            Double memoryUsed = getValue(memoryInfoJson);
            String cpuInfoJson = HttpUtil.get(map.get("address") + "/actuator/metrics/system.cpu.usage");
            Double cpuUsed = getValue(cpuInfoJson);
            NodeInfoVOList.add(new NodeInfoVO(map.get("name"), map.get("address").substring(0, map.get("address").indexOf(":")), memoryUsed, cpuUsed));
        }
        return JSONUtil.toJsonStr(NodeInfoVOList);
    }

    // 每5秒执行一次
    @Scheduled(fixedRate = 5000)
    private void cacheSystemInfo() {
        List<String> recentNodeInfo = redisCache.getCacheObject("nodeInfoVOList");
        if (Objects.isNull(recentNodeInfo)) {
            recentNodeInfo = new ArrayList<>(10);
        }
        // 队列满了 移掉最后一个
        if (recentNodeInfo.size() == 10) {
            recentNodeInfo.remove(9);
        }
        recentNodeInfo.add(0, getNodeInfo());
        redisCache.setObject("nodeInfoVOList", recentNodeInfo);
    }

    /**
     * 获取系统信息
     */
    @Override
    public ResponseResult getSystemInfo() {
        RecentRecordVO recentRecord = getRecentRecord();
        List<String> nodeInfoVOList = redisCache.getCacheObject("nodeInfoVOList");
        return ResponseResult.success(new SystemInfoVO(recentRecord, nodeInfoVOList));
    }
}
