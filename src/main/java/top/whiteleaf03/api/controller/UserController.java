package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.whiteleaf03.api.modal.dto.LoginDTO;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.service.captcha.CaptchaService;
import top.whiteleaf03.api.service.user.UserService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CaptchaService captchaService;

    @Autowired
    public UserController(UserService userService, CaptchaService captchaService) {
        this.userService = userService;
        this.captchaService = captchaService;
    }

    @PostMapping("register")
    public ResponseResult register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping("login")
    public ResponseResult login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("captcha")
    public ResponseResult getCaptcha() {
        return captchaService.getCaptcha();
    }
}
