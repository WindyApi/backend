package top.whiteleaf03.api.service.user;

import top.whiteleaf03.api.modal.dto.LoginDTO;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.util.ResponseResult;

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
}
