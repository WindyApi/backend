package top.whiteleaf03.api.service.user;

import org.springframework.web.multipart.MultipartFile;
import top.whiteleaf03.api.modal.dto.EditPasswordDTO;
import top.whiteleaf03.api.modal.dto.EditUserInfoDTO;
import top.whiteleaf03.api.modal.dto.LoginDTO;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.util.ResponseResult;

import java.io.IOException;

/**
 * @author WhiteLeaf03
 */
public interface UserService {
    /**
     * 新用户注册
     *
     * @param registerDTO 注册信息
     * @return 返回结果
     */
    ResponseResult register(RegisterDTO registerDTO);

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 返回结果
     */
    ResponseResult login(LoginDTO loginDTO);

    /**
     * 用户修改头像
     *
     * @return 返回结果
     */
    ResponseResult editAvatar(MultipartFile multipartFile) throws IOException;

    /**
     * 通过邮箱获取验证码
     *
     * @return 返回结果
     */
    ResponseResult getVerifyCodeByEmail();

    /**
     * 用户修改个人信息
     *
     * @param editUserInfoDTO 修改的信息
     * @return 返回结果
     */
    ResponseResult editUserInfo(EditUserInfoDTO editUserInfoDTO);

    /**
     * 用户重置ak和sk
     *
     * @return 返回结果
     */
    ResponseResult resetAccessKeyAndSecretKey();

    /**
     * 用户修改密码
     *
     * @param editPasswordDTO 验证码和新密码
     * @return 返回结果
     */
    ResponseResult editPassword(EditPasswordDTO editPasswordDTO);
}
