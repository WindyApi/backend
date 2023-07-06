package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.whiteleaf03.api.modal.dto.LoginDTO;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.service.UserService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 新用户注册
     *
     * @param registerDTO 注册信息
     * @return 返回结果
     */
    @PostMapping("register")
    public ResponseResult register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 返回结果
     */
    @PostMapping("login")
    public ResponseResult login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
}
