package top.whiteleaf03.api.service.system;

import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface SystemService {
    /**
     * 保存接口调用记录
     *
     * @param recordJson 调用记录 InterfaceInvokeRecordDTO的JSON格式
     */
    void saveRecord(String recordJson);

    /**
     * 获取系统信息
     */
    ResponseResult getSystemInfo();
}
