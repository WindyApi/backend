package top.whiteleaf03.api.service.user;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import top.whiteleaf03.api.config.GlobalConfig;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.mapper.UserInterfaceRecordMapper;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.*;
import top.whiteleaf03.api.modal.entity.InterfaceInfo;
import top.whiteleaf03.api.util.*;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.LoginVO;
import top.whiteleaf03.api.modal.vo.RegisterVO;
import top.whiteleaf03.api.modal.vo.UserIdAndEmailVO;
import top.whiteleaf03.api.service.email.EmailService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final GlobalConfig globalConfig;
    private final UserMapper userMapper;
    private final RedisCache redisCache;
    private final EmailService emailService;
    private final InterfaceInfoMapper interfaceInfoMapper;
    private final UserInterfaceRecordMapper userInterfaceRecordMapper;

    @Autowired
    public UserServiceImpl(GlobalConfig globalConfig, UserMapper userMapper, RedisCache redisCache, EmailService emailService, InterfaceInfoMapper interfaceInfoMapper, UserInterfaceRecordMapper userInterfaceRecordMapper) {
        this.globalConfig = globalConfig;
        this.userMapper = userMapper;
        this.redisCache = redisCache;
        this.emailService = emailService;
        this.interfaceInfoMapper = interfaceInfoMapper;
        this.userInterfaceRecordMapper = userInterfaceRecordMapper;
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
        if (Guest.ACCOUNT.equals(loginDTO.getAccount())) {
            //游客登录 直接放行
            String token = TokenUtils.createToken(Guest.UID);

            //保存token和用户信息
            redisCache.setCacheObject("[OnlineUserToken]" + Guest.ID, token, 1, TimeUnit.HOURS);
            redisCache.setCacheObject("[OnlineUserInfo]" + Guest.ID, Guest.ENTITY, 1, TimeUnit.HOURS);

            return ResponseResult.success(new LoginVO(Guest.ENTITY, token));
        }

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

    /**
     * 用户修改头像
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult editAvatar(MultipartFile multipartFile) throws IOException {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        Map<String, String> headers = new HashMap<String, String>() {{
            put("Content-Type", "multipart/form-data");
            put("Authorization", "AevGYHT5j3p1CR7bLH8MDznfLA10CEMZ");
            put("User-Agent", "java");
        }};
        HttpRequest request = HttpRequest.post("https://sm.ms/api/v2/upload")
                .addHeaders(headers);
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        request.form("smfile", convFile, multipartFile.getOriginalFilename());
        String result = request.execute().body();
        String regex = "\"url\":\"(https?:\\\\/\\\\/[^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
        String url;
        if (matcher.find()) {
            url = matcher.group(1);
        } else {
            return ResponseResult.error();
        }
        url = url.replace("\\", "");
        userMapper.updateAvatarById(new UpdateAvatarDTO(user.getId(), url));
        return ResponseResult.success(url);
    }

    /**
     * 通过邮箱获取验证码
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult getVerifyCodeByEmail() {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        String email = userMapper.selectEmailById(user.getId());
        String verifyCode = RandomUtil.randomNumbers(6);
        redisCache.setCacheObject("[VerifyCode]" + user.getId(), verifyCode, 5, TimeUnit.HOURS);
        emailService.sentEmail(new EmailInfoDTO(email, "重置密钥", "您的验证码为:" + verifyCode + "\n5分钟内有效"));
        return ResponseResult.success();
    }

    /**
     * 用户修改个人信息
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult editUserInfo(EditUserInfoDTO editUserInfoDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        String verifyCode = redisCache.getCacheObject("[VerifyCode]" + user.getId());
        if (ObjectUtil.isNotNull(verifyCode) && verifyCode.equals(editUserInfoDTO.getVerifyCode())) {
            editUserInfoDTO.setId(user.getId());
            userMapper.updateNicknameOrGenderOrEmailById(editUserInfoDTO);
            return ResponseResult.success("");
        }
        return ResponseResult.error("验证码错误");
    }

    /**
     * 用户重置ak和sk
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult resetAccessKeyAndSecretKey() {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        String accessKey = DigestUtil.sha1Hex(globalConfig.getSalt() + RandomUtil.randomString(12));
        String secretKey = DigestUtil.sha256Hex(globalConfig.getSalt() + RandomUtil.randomString(12));
        String email = userMapper.selectEmailById(user.getId());
        emailService.sentEmail(new EmailInfoDTO(email, "重置密钥", "您的新密钥，请妥善保管!\n[accessKey]" + accessKey + "\n[secretKey]" + secretKey));
        return ResponseResult.success();
    }

    /**
     * 用户修改密码
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult editPassword(EditPasswordDTO editPasswordDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        String verifyCode = redisCache.getCacheObject("[VerifyCode]" + user.getId());
        if (ObjectUtil.isNotNull(verifyCode) && verifyCode.equals(editPasswordDTO.getVerifyCode())) {
            editPasswordDTO.setId(user.getId());
            editPasswordDTO.setNewPassword(DigestUtil.bcrypt(editPasswordDTO.getNewPassword()));
            userMapper.updatePasswordById(editPasswordDTO);
            return ResponseResult.success();
        }
        return ResponseResult.error("验证码错误");
    }

    /**
     * 通过用户名获取验证码
     *
     * @param getVerifyCodeByAccount 账号
     * @return 返回结果
     */
    @Override
    public ResponseResult getVerifyCode(GetVerifyCodeByAccount getVerifyCodeByAccount) {
        if (Guest.ACCOUNT.equals(getVerifyCodeByAccount.getAccount())) {
            //游客账户不允许重置
            return ResponseResult.refuse("游客账户不允许重置");
        }
        String account = getVerifyCodeByAccount.getAccount();
        UserIdAndEmailVO userIdAndEmailVO = userMapper.selectIdAndEmailByAccount(account);
        String email = userIdAndEmailVO.getEmail();
        Long id = userIdAndEmailVO.getId();
        String verifyCode = RandomUtil.randomNumbers(6);
        redisCache.setCacheObject("[VerifyCode]" + account + verifyCode, id, 5, TimeUnit.MINUTES);
        emailService.sentEmail(new EmailInfoDTO(email, "重置密码", "您的验证码为:" + verifyCode + "\n5分钟内有效"));
        return ResponseResult.success();
    }

    /**
     * 用户忘记密码
     *
     * @param resetPasswordDTO 账号名 验证码 新密码
     * @return 返回结果
     */
    @Override
    public ResponseResult resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (Guest.ACCOUNT.equals(resetPasswordDTO.getAccount())) {
            //游客账户不允许重置
            return ResponseResult.refuse("游客账户不允许重置");
        }
        String account = resetPasswordDTO.getAccount();
        String verifyCode = resetPasswordDTO.getVerifyCode();
        Long id = redisCache.getCacheObject("[VerifyCode]" + account + verifyCode);
        if (Objects.isNull(id)) {
            return ResponseResult.error("验证码错误");
        }
        String newPassword = DigestUtil.bcrypt(resetPasswordDTO.getNewPassword());
        userMapper.updatePasswordById(new EditPasswordDTO(id, newPassword));
        return ResponseResult.success();
    }

    /**
     * 用户调用API
     *
     * @param invokeApiDTO 包含相关信息
     * @return 返回调用结果
     */
    @Override
    public ResponseResult invokeApi(InvokeApiDTO invokeApiDTO) {
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectMethodAndUrlByInterfaceId(invokeApiDTO.getInterfaceId());
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        Long userId = user.getId();
        
        // 判断剩余次数
        UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO = new UserIdAndInterfaceIdDTO(userId, invokeApiDTO.getInterfaceId());
        Long leftNum = userInterfaceRecordMapper.selectLeftNumByUserIdAndInterfaceId(userIdAndInterfaceIdDTO);
        if (ObjectUtil.isNotNull(leftNum) && leftNum > 0) {
            userInterfaceRecordMapper.updateLeftNumByUserIdAndInterfaceId(userIdAndInterfaceIdDTO);
        } else {
            return ResponseResult.refuse("接口尚未开通或调用次数已用完");
        }
        AccessKeyAndSecretKeyDTO accessKeyAndSecretKeyDTO = userMapper.selectAccessKeyAndSecretKeyById(userId);
        String result = invokeAndGetResult(invokeApiDTO, accessKeyAndSecretKeyDTO, interfaceInfo);
        return JSONUtil.toBean(result, ResponseResult.class);
    }

    private String invokeAndGetResult(InvokeApiDTO invokeApiDTO, AccessKeyAndSecretKeyDTO accessKeyAndSecretKeyDTO, InterfaceInfo interfaceInfo) {
        WindyApiClient windyApiClient = new WindyApiClient(globalConfig.getGatewayHost(), accessKeyAndSecretKeyDTO);
        String result;
        if ("GET".equals(interfaceInfo.getMethod())) {
            HashMap<String, Object> params = invokeApiDTO.getParamsJson();
            if (Objects.isNull(params)) {
                params = new HashMap<>();
            }
            result = windyApiClient.invokeApiByGet(params, interfaceInfo.getUrl());
        } else {
            HashMap<String, Object> data = invokeApiDTO.getDataJson();
            if (Objects.isNull(data)) {
                data = new HashMap<>();
            }
            result = windyApiClient.invokeApiByPost(data, interfaceInfo.getUrl());
        }
        return result;
    }
}
