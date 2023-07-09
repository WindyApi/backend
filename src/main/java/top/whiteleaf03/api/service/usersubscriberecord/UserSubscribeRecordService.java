package top.whiteleaf03.api.service.usersubscriberecord;

import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserSubscribeDTO;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface UserSubscribeRecordService {
    /**
     * 用户新增订阅记录
     *
     * @param userSubscribeDTO 记录信息
     * @return 返回结果
     */
    ResponseResult newRecord(UserSubscribeDTO userSubscribeDTO);

    /**
     * 用户查询自身订阅记录
     *
     * @param interfaceIdDTO 接口id，可选参数，若携带则查询指定接口的开通记录，反之查询所有接口开通记录
     * @return 返回结果
     */
    ResponseResult queryRecord(InterfaceIdDTO interfaceIdDTO);

    /**
     * 管理员查询所有订阅记录
     *
     * @param interfaceIdDTO 接口id，可选参数，若携带则查询指定接口的开通记录，反之查询所有接口开通记录
     * @return 返回结果
     */
    ResponseResult queryAllUserRecord(InterfaceIdDTO interfaceIdDTO);
}
