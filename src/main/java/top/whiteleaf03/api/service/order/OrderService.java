package top.whiteleaf03.api.service.order;

import top.whiteleaf03.api.modal.dto.NewOrderDTO;
import top.whiteleaf03.api.modal.dto.ReplyOrderDTO;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface OrderService {
    /**
     * 新增工单
     *
     * @param newOrderDTO 新增工单的信息
     * @return 返回结果
     */
    ResponseResult insertOrder(NewOrderDTO newOrderDTO);

    /**
     * 查询自己的工单
     */
    ResponseResult getAllOrder();

    /**
     * 管理员获取未处理工单
     *
     * @return 返回结果
     */
    ResponseResult getAllWaitingOrder();

    /**
     * 管理员回复工单
     *
     * @param replyOrderDTO 回复的内容
     * @return 返回结果
     */
    ResponseResult replyWaitingOrder(ReplyOrderDTO replyOrderDTO);
}
