package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.modal.dto.*;
import top.whiteleaf03.api.service.captcha.CaptchaService;
import top.whiteleaf03.api.service.user.UserService;
import top.whiteleaf03.api.util.ResponseResult;

import java.io.IOException;

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

    @TokenCheck({"admin", "normal"})
    @PostMapping("avatar")
    public ResponseResult editAvatar(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return userService.editAvatar(multipartFile);
    }

    @TokenCheck({"admin", "normal"})
    @GetMapping("verify")
    public ResponseResult getVerifyCodeByEmail() {
        return userService.getVerifyCodeByEmail();
    }

    @TokenCheck({"admin", "normal"})
    @PutMapping("info")
    public ResponseResult editUserInfo(@RequestBody EditUserInfoDTO editUserInfoDTO) {
        return userService.editUserInfo(editUserInfoDTO);
    }

    @TokenCheck({"admin", "normal"})
    @GetMapping("resetKey")
    public ResponseResult resetAccessKeyAndSecretKey() {
        return userService.resetAccessKeyAndSecretKey();
    }

    @TokenCheck({"admin", "normal"})
    @PutMapping("password")
    public ResponseResult editPassword(@RequestBody EditPasswordDTO editPasswordDTO) {
        return userService.editPassword(editPasswordDTO);
    }

    @GetMapping("forget")
    public ResponseResult getVerifyCode(GetVerifyCodeByAccount getVerifyCodeByAccount) {
        return userService.getVerifyCode(getVerifyCodeByAccount);
    }

    @PutMapping("forget")
    public ResponseResult resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return userService.resetPassword(resetPasswordDTO);
    }

    @TokenCheck({})
    @PostMapping("invoke")
    public ResponseResult invokeApi(@RequestBody InvokeApiDTO invokeApiDTO) {
        return userService.invokeApi(invokeApiDTO);
    }
}
