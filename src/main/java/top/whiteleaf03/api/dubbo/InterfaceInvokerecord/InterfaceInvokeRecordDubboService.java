package top.whiteleaf03.api.dubbo.InterfaceInvokerecord;

import top.whiteleaf03.api.modal.dto.InterfaceInvokeRecordDTO;

/**
 * @author WhiteLeaf03
 */
public interface InterfaceInvokeRecordDubboService {
    /**
     * 将记录添加进队列
     *
     * @param interfaceInvokeRecordDTO 调用记录
     */
    void insertInterfaceInvokeRecord(InterfaceInvokeRecordDTO interfaceInvokeRecordDTO);

    /**
     * 从队列中获取记录并存进数据库
     */
    void getInterfaceInvokeRecordsFromQueue();
}
