package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.NewOrderDTO;
import top.whiteleaf03.api.modal.dto.ReplyOrderDTO;
import top.whiteleaf03.api.modal.entity.Order;
import top.whiteleaf03.api.modal.vo.OrderVO;

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
     * 查询自身所有工单
     *
     * @param userId 用户id
     * @return 返回结果
     */
    List<OrderVO> getAllOrder(Long userId);

    /**
     * 获取所有待处理工单
     *
     * @return 返回结果
     */
    List<Order> getAllWaitingOrder();

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
