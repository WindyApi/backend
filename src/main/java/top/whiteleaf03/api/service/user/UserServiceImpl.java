package top.whiteleaf03.api.service.user;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.config.GlobalConfig;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.EmailInfoDTO;
import top.whiteleaf03.api.modal.dto.LoginDTO;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.LoginVO;
import top.whiteleaf03.api.modal.vo.RegisterVO;
import top.whiteleaf03.api.service.email.EmailService;
import top.whiteleaf03.api.util.RedisCache;
import top.whiteleaf03.api.util.ResponseResult;
import top.whiteleaf03.api.util.TokenUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author WhiteLeaf03
 */
@Service
public class UserServiceImpl implements UserService {
    private final GlobalConfig globalConfig;
    private final UserMapper userMapper;
    private final RedisCache redisCache;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(GlobalConfig globalConfig, UserMapper userMapper, RedisCache redisCache, EmailService emailService) {
        this.globalConfig = globalConfig;
        this.userMapper = userMapper;
        this.redisCache = redisCache;
        this.emailService = emailService;
    }

    /**
     * 新用户注册
     *
     * @param registerDTO 注册信息
     * @return 返回结果
     */
    @Override
    public ResponseResult register(RegisterDTO registerDTO) {
        //验证码校验
        String code = redisCache.getCacheObject("[Captcha]" + registerDTO.getIdentity());
        redisCache.deleteObject("[Captcha]" + registerDTO.getIdentity());
        if (Objects.isNull(code)) {
            return ResponseResult.error("验证码不存在");
        }
        if (!code.equals(registerDTO.getCaptcha())) {
            return ResponseResult.error("验证码错误");
        }

        //对密码进行加密
        registerDTO.setPassword(DigestUtil.bcrypt(registerDTO.getPassword()));

        //生成accessKey和secretKey
        String accessKey = DigestUtil.sha1Hex(globalConfig.getSalt() + registerDTO.getAccount());
        String secretKey = DigestUtil.sha256Hex(globalConfig.getSalt() + registerDTO.getPassword());
        registerDTO.setAccessKey(accessKey);
        registerDTO.setSecretKey(secretKey);

        //发送accessKey和secretKey给用户注册的邮箱
        emailService.sentEmail(new EmailInfoDTO(registerDTO.getEmail(), "欢迎新用户", "请妥善保管好您的密钥!\n[accessKey]" + accessKey + "\n[secretKey]" + secretKey));

        //将用户信息插入数据库
        userMapper.insert(registerDTO);

        return ResponseResult.success(new RegisterVO(accessKey, secretKey));
    }

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 返回结果
     */
    @Override
    public ResponseResult login(LoginDTO loginDTO) {
        //验证码校验
        String code = redisCache.getCacheObject("[Captcha]" + loginDTO.getIdentity());
        redisCache.deleteObject("[Captcha]" + loginDTO.getIdentity());
        if (Objects.isNull(code)) {
            return ResponseResult.error("验证码不存在");
        }
        if (!code.equals(loginDTO.getCaptcha())) {
            return ResponseResult.error("验证码错误");
        }

        //根据账号查询用户
        User user = userMapper.selectIdAndNicknameAndAvatarAndGenderAndRoleAndPasswordAndPasswordAndCreateTimeByAccount(loginDTO.getAccount());

        if (Objects.isNull(user)) {
            //未查询到用户
            return ResponseResult.error("用户不存在或已被封号");
        }

        //查询到用户
        if (DigestUtil.bcryptCheck(loginDTO.getPassword(), user.getPassword())) {
            //密码正确
            String token = TokenUtils.createToken(user.getId().toString());

            //保存token和用户信息
            redisCache.setCacheObject("[OnlineUserToken]" + user.getId(), token, 1, TimeUnit.HOURS);
            redisCache.setCacheObject("[OnlineUserInfo]" + user.getId(), user, 1, TimeUnit.HOURS);

            return ResponseResult.success(new LoginVO(user, token));
        } else {
            //密码错误
            return ResponseResult.authFailed("密码错误");
        }
    }
}
