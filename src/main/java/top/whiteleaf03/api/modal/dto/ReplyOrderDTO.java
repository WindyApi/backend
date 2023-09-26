package top.whiteleaf03.api.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyOrderDTO {
    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 答复信息
     */
    private String replyMessage;
}
