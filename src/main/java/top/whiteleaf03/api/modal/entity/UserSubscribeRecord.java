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
public class UserSubscribeRecord {
    /**
     * 主键自增
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * 增加次数
     */
    private Integer increase;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 创建日期
     */
    private Date createTime;
}
