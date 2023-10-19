package top.whiteleaf03.api.service.interfaceinvokerecord;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.modal.dto.InterfaceInvokeRecordDTO;
import top.whiteleaf03.api.util.MongoUtil;

@Service
public class InterfaceInvokeRecordServiceImpl implements InterfaceInvokeRecordService {
    private final MongoUtil mongoUtil;

    @Autowired
    public InterfaceInvokeRecordServiceImpl(MongoUtil mongoUtil) {
        this.mongoUtil = mongoUtil;
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
        mongoUtil.save(interfaceInvokeRecordDTO);
    }
}
