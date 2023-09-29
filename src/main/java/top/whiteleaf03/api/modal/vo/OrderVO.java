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
public class OrderVO {
    /**
     * 主键自增
     */
    private Long id;

    /**
     * 紧急程度
     */
    private String level;

    /**
     * 说明信息
     */
    private String submitMessage;

    /**
     * 相关接口
     */
    private String interfaceName;

    /**
     * 答复信息
     */
    private String replyMessage;

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

    /**
     * 更新日期
     */
    private Date updateTime;

    public OrderVO(Order order, String interfaceName) {
        this.id = order.getId();
        this.level = order.getLevel();
        this.submitMessage = order.getSubmitMessage();
        this.interfaceName = interfaceName;
        this.replyMessage = order.getReplyMessage();
        this.status = order.getStatus();
        this.createTime = order.getCreateTime();
        this.updateTime = order.getUpdateTime();
    }
}
