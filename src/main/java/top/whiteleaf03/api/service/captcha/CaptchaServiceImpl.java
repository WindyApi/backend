package top.whiteleaf03.api.service.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.modal.vo.CaptchaVO;
import top.whiteleaf03.api.util.RedisCache;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.concurrent.TimeUnit;

/**
 * @author WhiteLeaf03
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    private final RedisCache redisCache;

    @Autowired
    public CaptchaServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 获取验证码
     *
     * @return 返回身份码和验证码
     */
    @Override
    public ResponseResult getCaptcha() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(180, 30, 4, 6);
        String identity = RandomUtil.randomString(16);
        redisCache.setCacheObject("[Captcha]" + identity, lineCaptcha.getCode(), 5, TimeUnit.MINUTES);
        return ResponseResult.success(new CaptchaVO(identity, lineCaptcha.getImageBase64Data()));
    }
}
