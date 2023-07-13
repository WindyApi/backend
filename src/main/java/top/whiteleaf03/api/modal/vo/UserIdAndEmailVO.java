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
public class UserIdAndEmailVO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户邮箱
     */
    private String email;
}
