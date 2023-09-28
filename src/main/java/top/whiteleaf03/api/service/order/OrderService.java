package top.whiteleaf03.api.service.order;

import top.whiteleaf03.api.modal.dto.NewOrderDTO;
import top.whiteleaf03.api.modal.dto.PageNumDTO;
import top.whiteleaf03.api.modal.dto.QuerySelfOrderDTO;
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
     * 获取分页总数
     *
     * @param type self: 用户获取自身工单总数 / admin: 管理员获取待审核总数
     */
    ResponseResult getPageSum(String type);

    /**
     * 查询自己的工单
     *
     * @param querySelfOrderDTO 用户id和页号
     */
    ResponseResult getAllOrder(QuerySelfOrderDTO querySelfOrderDTO);

    /**
     * 管理员获取未处理工单
     *
     * @return 返回结果
     */
    ResponseResult getAllWaitingOrder(PageNumDTO pageNumDTO);

    /**
     * 管理员回复工单
     *
     * @param replyOrderDTO 回复的内容
     * @return 返回结果
     */
    ResponseResult replyWaitingOrder(ReplyOrderDTO replyOrderDTO);
}
