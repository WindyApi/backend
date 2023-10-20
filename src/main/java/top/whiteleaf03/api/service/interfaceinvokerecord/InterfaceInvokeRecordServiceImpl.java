package top.whiteleaf03.api.service.interfaceinvokerecord;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.modal.dto.InterfaceInvokeRecordDTO;
import top.whiteleaf03.api.modal.vo.RecentRecordVO;
import top.whiteleaf03.api.repository.mongo.InterfaceInvokeRecordMongoRepository;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@Service
public class InterfaceInvokeRecordServiceImpl implements InterfaceInvokeRecordService {
    private final InterfaceInvokeRecordMongoRepository interfaceInvokeRecordMongoRepository;

    @Autowired
    public InterfaceInvokeRecordServiceImpl(InterfaceInvokeRecordMongoRepository interfaceInvokeRecordMongoRepository) {
        this.interfaceInvokeRecordMongoRepository = interfaceInvokeRecordMongoRepository;
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
     * 获取最近调用记录
     */
    @Override
    public ResponseResult getRecentRecord() {
        final int TIMES = 5;
        final long ONE_MINUTE_MILLIS = 60000L;

        long currentTimeMillis = System.currentTimeMillis() / 1000 * 1000;
        Long[] recentQPS = new Long[TIMES];
        Long[] recentUseTime = new Long[TIMES];
        Double[] recentAcceptRate = new Double[TIMES];
        for (int index = 0; index < TIMES; index++) {
            long maxTime = currentTimeMillis - index * ONE_MINUTE_MILLIS;
            long minTime = currentTimeMillis - (index + 1) * ONE_MINUTE_MILLIS;

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
        return ResponseResult.success(new RecentRecordVO(recentQPS, recentUseTime, recentAcceptRate));
    }
}
