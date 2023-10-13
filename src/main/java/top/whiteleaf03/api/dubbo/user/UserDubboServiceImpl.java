package top.whiteleaf03.api.dubbo.user;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.UserIdAndSecretKeyDTO;

/**
 * @author WhiteLeaf03
 */
@Service
@DubboService
public class UserDubboServiceImpl implements UserDubboService {
    private final UserMapper userMapper;

    @Autowired
    public UserDubboServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据ak获取用户id和sk
     *
     * @param accessKey 用户ak
     * @return 返回结果
     */
    @Override
    public UserIdAndSecretKeyDTO selectUserIdAndSecretKeyByAccessKey(String accessKey) {
        return userMapper.selectIdAndSecretKeyByAccessKey(accessKey);
    }
}
