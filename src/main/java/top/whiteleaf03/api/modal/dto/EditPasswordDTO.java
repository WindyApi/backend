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
public class EditPasswordDTO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 新密码
     */
    private String newPassword;

    public EditPasswordDTO(Long id, String newPassword) {
        this.id = id;
        this.newPassword = newPassword;
    }
}
