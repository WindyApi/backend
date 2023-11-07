package top.whiteleaf03.api.service.system;

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

import java.text.SimpleDateFormat;
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
    @RabbitListener(queues = "interface_invoke_record")
    public void saveRecord(String recordJson) {
        InterfaceInvokeRecordDTO interfaceInvokeRecordDTO = JSONUtil.toBean(recordJson, InterfaceInvokeRecordDTO.class);
        interfaceInvokeRecordMongoRepository.save(interfaceInvokeRecordDTO);
    }

    /**
     * 获取最近调用情况
     *
     * @return 返回接口调用情况
     */
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

    /**
     * 获取系统信息
     */
    @Override
    public ResponseResult getSystemInfo() {
        RecentRecordVO recentRecord = getRecentRecord();
        List<String> nodeInfoVOList = redisCache.getCacheObject("DockerContainerInfo");
        return ResponseResult.success(new SystemInfoVO(recentRecord, nodeInfoVOList));
    }
}
