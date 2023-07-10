package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    /**
     * 用户昵称
     */
    @NonNull
    private String nickname;

    /**
     * 账号
     */
    @NonNull
    private String account;

    /**
     * 性别
     */
    @NonNull
    private Integer gender;

    /**
     * 密码
     */
    @NonNull
    private String password;

    /**
     * 邮箱
     */
    @NonNull
    private String email;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;
}
