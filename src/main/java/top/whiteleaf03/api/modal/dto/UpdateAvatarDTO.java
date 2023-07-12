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
public class UpdateAvatarDTO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 头像链接
     */
    private String avatar;
}
