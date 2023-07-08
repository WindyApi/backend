package top.whiteleaf03.api.modal.entity;

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
public class UserInterfaceRecord {
    /**
     * 主键
     */
    private Long id;

    /**
     * 调用用户 ID
     */
    private Long userId;

    /**
     * 接口 ID
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 状态
     * 0 未删
     * 1 已删
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     * 0 未删
     * 1 已删
     */
    private Boolean isDelete;
}
