package top.whiteleaf03.api.service.captcha;

import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface CaptchaService {
    /**
     * 获取验证码
     *
     * @return 返回身份码和验证码
     */
    ResponseResult getCaptcha();
}
