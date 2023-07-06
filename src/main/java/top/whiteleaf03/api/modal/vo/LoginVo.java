package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.whiteleaf03.api.modal.entity.User;

import java.util.Date;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
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
     * 创建时间
     */
    private Date createTime;

    /**
     * token
     */
    private String token;

    public LoginVO(User user, String token) {
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.createTime = user.getCreateTime();
        this.token = token;
    }
}
