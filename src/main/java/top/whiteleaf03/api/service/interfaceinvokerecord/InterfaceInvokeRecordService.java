package top.whiteleaf03.api.service.interfaceinvokerecord;

import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface InterfaceInvokeRecordService {
    /**
     * 保存接口调用记录
     *
     * @param recordJson 调用记录 InterfaceInvokeRecordDTO的JSON格式
     */
    void saveRecord(String recordJson);

    /**
     * 获取最近调用记录
     */
    ResponseResult getRecentRecord();
}
