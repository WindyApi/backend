package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import top.whiteleaf03.api.modal.entity.User;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色：user / admin
     */
    private String role;

    /**
     * token
     */
    private String token;

    public LoginVo(User user, String token) {
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.token = token;
    }
}
