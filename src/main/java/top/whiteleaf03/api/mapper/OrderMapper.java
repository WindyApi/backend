package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.*;
import top.whiteleaf03.api.modal.entity.Order;
import top.whiteleaf03.api.modal.vo.OrderStatusVO;

import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Mapper
public interface OrderMapper {
    /**
     * 新增工单
     *
     * @param newOrderDTO 新工单信息
     */
    void insertOrder(NewOrderDTO newOrderDTO);

    /**
     * @param userId 用户id
     * @return 返回结果
     */
    Long getCountByUserId(Long userId);

    /**
     * @return 返回结果
     */
    OrderStatusVO getCountByStatus();

    /**
     * 查询自身所有工单
     *
     * @param querySelfOrderDTO 用户id和页号
     * @return 返回结果
     */
    List<Order> getSelfOrder(QuerySelfOrderDTO querySelfOrderDTO);

    /**
     * 获取所有待处理工单
     *
     * @return 返回结果
     */
    List<Order> getAllOrder(QueryAllOrderDTO queryAllOrderDTO);

    /**
     * 管理员回复工单
     *
     * @param replyOrderDTO 回复的内容
     */
    void updateReplyMessageById(ReplyOrderDTO replyOrderDTO);

    /**
     * 根据工单id获取用户id
     */
    Long getUserIdById();
}
