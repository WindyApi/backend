package top.whiteleaf03.api.modal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.whiteleaf03.api.modal.entity.Order;

import java.util.Date;

/**
 * @author WhiteLeaf03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitingOrderVO {
    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 紧急程度
     */
    private String level;

    /**
     * 说明信息
     */
    private String submitMessage;

    /**
     * 工单状态
     * 0: 尚未处理
     * 1: 已处理完毕
     */
    private Byte status;

    /**
     * 创建日期
     */
    private Date createTime;

    public WaitingOrderVO(Order order, String nickname, String interfaceName) {
        this.orderId = order.getId();
        this.nickname = nickname;
        this.interfaceName = interfaceName;
        this.level = order.getLevel();
        this.submitMessage = order.getSubmitMessage();
        this.status = order.getStatus();
        this.createTime = order.getCreateTime();
    }
}
