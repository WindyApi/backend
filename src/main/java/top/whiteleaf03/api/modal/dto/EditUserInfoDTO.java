package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserInfoDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     * 0: 男
     * 1: 女
     * -1: 保密
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;
}
