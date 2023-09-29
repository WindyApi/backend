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
public class OrderStatusVO {
    /**
     * 待完成工单数
     */
    private Long waitTotal;

    /**
     * 已完成工单数
     */
    private Long endTotal;
}
