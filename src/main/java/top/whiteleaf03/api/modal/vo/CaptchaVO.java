package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaVO {
    /**
     * 身份码
     */
    private String identity;

    /**
     * 验证码图片base64格式
     */
    private String captchaBase64Data;
}
