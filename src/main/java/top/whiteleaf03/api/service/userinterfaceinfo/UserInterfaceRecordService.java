package top.whiteleaf03.api.service.userinterfaceinfo;

import top.whiteleaf03.api.modal.dto.InsertUserInterfaceRecordDTO;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface UserInterfaceRecordService {
    /**
     * 新增用户调用记录
     *
     * @param insertUserInterfaceRecordDTO 信息
     * @return 结果
     */
    ResponseResult newUserInterfaceInfo(InsertUserInterfaceRecordDTO insertUserInterfaceRecordDTO);

    /**
     * 根据用户id查询
     *
     * @return 返回结果
     */
    ResponseResult queryByUserId();
}
