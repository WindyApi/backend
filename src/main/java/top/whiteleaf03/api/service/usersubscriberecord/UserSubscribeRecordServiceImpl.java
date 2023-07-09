package top.whiteleaf03.api.service.usersubscriberecord;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.UserSubscribeRecordMapper;
import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserIdAndInterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserSubscribeDTO;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.entity.UserSubscribeRecord;
import top.whiteleaf03.api.service.userinterfaceinfo.UserInterfaceRecordService;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class UserSubscribeRecordServiceImpl implements UserSubscribeRecordService {
    private final UserSubscribeRecordMapper userSubscribeRecordMapper;
    private final UserInterfaceRecordService userInterfaceRecordService;

    @Autowired
    public UserSubscribeRecordServiceImpl(UserSubscribeRecordMapper userSubscribeRecordMapper, UserInterfaceRecordService userInterfaceRecordService) {
        this.userSubscribeRecordMapper = userSubscribeRecordMapper;
        this.userInterfaceRecordService = userInterfaceRecordService;
    }

    /**
     * 用户新增订阅记录
     *
     * @param userSubscribeDTO 记录信息
     * @return 返回结果
     */
    @Override
    public ResponseResult newRecord(UserSubscribeDTO userSubscribeDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        userSubscribeDTO.setUserId(user.getId());
        userSubscribeRecordMapper.insert(userSubscribeDTO);
        userInterfaceRecordService.increaseTotalNum(userSubscribeDTO);
        return ResponseResult.success();
    }

    /**
     * 用户查询自身订阅记录
     *
     * @param interfaceIdDTO 接口id，可选参数，若携带则查询指定接口的开通记录，反之查询所有接口开通记录
     * @return 返回结果
     */
    @Override
    public ResponseResult queryRecord(InterfaceIdDTO interfaceIdDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO = new UserIdAndInterfaceIdDTO(user.getId(), interfaceIdDTO.getId());
        List<UserSubscribeRecord> userSubscribeRecords = userSubscribeRecordMapper.selectUserIdAndInterfaceIdAndIncreaseAndPriceAndCreateTimeByUserIdOrInterfaceId(userIdAndInterfaceIdDTO);
        return ResponseResult.success(userSubscribeRecords);
    }

    /**
     * 管理员查询所有订阅记录
     *
     * @param interfaceIdDTO 接口id，可选参数，若携带则查询指定接口的开通记录，反之查询所有接口开通记录
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAllUserRecord(InterfaceIdDTO interfaceIdDTO) {
        UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO = new UserIdAndInterfaceIdDTO(interfaceIdDTO.getId());
        List<UserSubscribeRecord> userSubscribeRecords = userSubscribeRecordMapper.selectUserIdAndInterfaceIdAndIncreaseAndPriceAndCreateTimeByUserIdOrInterfaceId(userIdAndInterfaceIdDTO);
        return ResponseResult.success(userSubscribeRecords);
    }
}
