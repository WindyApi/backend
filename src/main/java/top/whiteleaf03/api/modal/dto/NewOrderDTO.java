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
public class NewOrderDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 紧急程度
     */
    private String level;

    /**
     * 说明信息
     */
    private String submitMessage;

    public NewOrderDTO(String level, String submitMessage) {
        this.level = level;
        this.submitMessage = submitMessage;
    }
}
