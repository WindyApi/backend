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
public class UserIdAndSecretKeyVO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 密钥
     */
    private String secretKey;
}
