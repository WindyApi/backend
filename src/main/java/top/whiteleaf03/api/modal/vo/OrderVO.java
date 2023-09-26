package top.whiteleaf03.api.modal.vo;

import java.util.Date;

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
}
