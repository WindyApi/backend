package top.whiteleaf03.api.service;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.config.GlobalConfig;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.LoginDTO;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.LoginVo;
import top.whiteleaf03.api.modal.vo.RegisterVo;
import top.whiteleaf03.api.util.ResponseResult;
import top.whiteleaf03.api.util.TokenUtils;

import java.util.Objects;

/**
 * @author WhiteLeaf03
 */
@Service
public class UserServiceImpl implements UserService {
    private final GlobalConfig globalConfig;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(GlobalConfig globalConfig, UserMapper userMapper) {
        this.globalConfig = globalConfig;
        this.userMapper = userMapper;
    }

    /**
     * 新用户注册
     *
     * @param registerDTO 注册信息
     * @return 返回结果
     */
    @Override
    public ResponseResult register(RegisterDTO registerDTO) {
        //对密码进行加密
        registerDTO.setPassword(DigestUtil.bcrypt(registerDTO.getPassword()));

        //生成accessKey和secretKey
        String accessKey = DigestUtil.sha1Hex(globalConfig.getSalt() + registerDTO.getAccount());
        String secretKey = DigestUtil.sha256Hex(globalConfig.getSalt() + registerDTO.getPassword());
        registerDTO.setAccessKey(accessKey);
        registerDTO.setSecretKey(secretKey);

        //将用户信息插入数据库
        userMapper.insert(registerDTO);

        return ResponseResult.success(new RegisterVo(accessKey, secretKey));
    }

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 返回结果
     */
    @Override
    public ResponseResult login(LoginDTO loginDTO) {
        //根据账号查询用户
        User user = userMapper.selectIdAndNicknameAndAvatarAndGenderAndRoleAndPasswordAndPassword(loginDTO.getAccount());

        if (Objects.isNull(user)) {
            //未查询到用户
            return ResponseResult.error("用户不存在或已被封号");
        }

        //查询到用户
        if (DigestUtil.bcryptCheck(loginDTO.getPassword(), user.getPassword())) {
            //密码正确
            String token = TokenUtils.createToken(user.getId().toString());
            return ResponseResult.success(new LoginVo(user, token));
        } else {
            //密码错误
            return ResponseResult.authFailed("密码错误");
        }
    }
}
