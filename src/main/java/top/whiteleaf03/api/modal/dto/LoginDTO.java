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
public class LoginDTO {
    /**
     * 账号
     */
    @NonNull
    private String account;

    /**
     * 密码
     */
    @NonNull
    private String password;
}
