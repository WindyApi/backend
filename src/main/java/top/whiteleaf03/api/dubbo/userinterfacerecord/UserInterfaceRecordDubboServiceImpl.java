package top.whiteleaf03.api.dubbo.userinterfacerecord;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.mapper.UserInterfaceRecordMapper;
import top.whiteleaf03.api.modal.dto.UserIdAndInterfaceIdDTO;

/**
 * @author WhiteLeaf03
 */
@DubboService
@Service
public class UserInterfaceRecordDubboServiceImpl implements UserInterfaceRecordDubboService {
    private final UserInterfaceRecordMapper userInterfaceRecordMapper;

    @Autowired
    public UserInterfaceRecordDubboServiceImpl(UserInterfaceRecordMapper userInterfaceRecordMapper) {
        this.userInterfaceRecordMapper = userInterfaceRecordMapper;
    }

    /**
     * 查询剩余使用次数
     *
     * @param userIdAndInterfaceIdDTO 用户id和接口id
     * @return 返回结果
     */
    @Override
    public Long selectLeftNumByUserIdAndInterfaceId(UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO) {
        return userInterfaceRecordMapper.selectLeftNumByUserIdAndInterfaceId(userIdAndInterfaceIdDTO);
    }

    /**
     * 减少使用次数
     *
     * @param userIdAndInterfaceIdDTO 用户id和接口id
     */
    @Override
    public void updateLeftNumByUserIdAndInterfaceId(UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO) {
        userInterfaceRecordMapper.updateLeftNumByUserIdAndInterfaceId(userIdAndInterfaceIdDTO);
    }
}
