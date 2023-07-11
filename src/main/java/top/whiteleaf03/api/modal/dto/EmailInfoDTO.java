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
public class EmailInfoDTO {
    /**
     * 收件人邮箱
     */
    private String target;

    /**
     * 发送标题
     */
    private String title;

    /**
     * 发送内容
     */
    private String message;
}
