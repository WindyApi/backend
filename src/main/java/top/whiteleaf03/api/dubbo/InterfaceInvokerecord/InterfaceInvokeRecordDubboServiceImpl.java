package top.whiteleaf03.api.dubbo.InterfaceInvokerecord;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.mapper.InterfaceInvokeRecordMapper;
import top.whiteleaf03.api.modal.dto.InterfaceInvokeRecordDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@DubboService
@Service
public class InterfaceInvokeRecordDubboServiceImpl implements InterfaceInvokeRecordDubboService {
    private final LinkedBlockingQueue<InterfaceInvokeRecordDTO> interfaceInvokeRecordDTOQueue;
    private final InterfaceInvokeRecordMapper interfaceInvokeRecordMapper;

    @Autowired
    public InterfaceInvokeRecordDubboServiceImpl(InterfaceInvokeRecordMapper interfaceInvokeRecordMapper) {
        this.interfaceInvokeRecordDTOQueue = new LinkedBlockingQueue<>();
        this.interfaceInvokeRecordMapper = interfaceInvokeRecordMapper;
    }

    /**
     * 将记录添加进队列
     *
     * @param interfaceInvokeRecordDTO 调用记录
     */
    @Override
    public void insertInterfaceInvokeRecord(InterfaceInvokeRecordDTO interfaceInvokeRecordDTO) {
        try {
            interfaceInvokeRecordDTOQueue.put(interfaceInvokeRecordDTO);
        } catch (InterruptedException e) {
            log.error("调用记录存入队列时异常!{}", e.getMessage());
        }
    }

    /**
     * 从队列中获取记录并存进数据库
     */
    @Override
    @Scheduled(fixedRate = 60000)
    public void getInterfaceInvokeRecordsFromQueue() {
        List<InterfaceInvokeRecordDTO> interfaceInvokeRecordDTOS = new ArrayList<>();
        interfaceInvokeRecordDTOQueue.drainTo(interfaceInvokeRecordDTOS);
        if (interfaceInvokeRecordDTOS.isEmpty()) {
            return;
        }
        interfaceInvokeRecordMapper.insert(interfaceInvokeRecordDTOS);
    }
}
