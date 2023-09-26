package top.whiteleaf03.api.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.OrderMapper;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.EmailInfoDTO;
import top.whiteleaf03.api.modal.dto.NewOrderDTO;
import top.whiteleaf03.api.modal.dto.ReplyOrderDTO;
import top.whiteleaf03.api.modal.entity.Order;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.OrderVO;
import top.whiteleaf03.api.modal.vo.WaitingOrderVO;
import top.whiteleaf03.api.service.email.EmailService;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, UserMapper userMapper, EmailService emailService) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    /**
     * 新增工单
     *
     * @param newOrderDTO 新增工单的信息
     * @return 返回结果
     */
    @Override
    public ResponseResult insertOrder(NewOrderDTO newOrderDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        newOrderDTO.setUserId(user.getId());
        orderMapper.insertOrder(newOrderDTO);
        emailService.sentEmail(new EmailInfoDTO("2876202234@qq.com", "[WindyApi开放平台]新的待处理工单", "[紧急程度]\n" + newOrderDTO.getLevel()));
        return ResponseResult.success();
    }

    /**
     * 查询自己的工单
     */
    @Override
    public ResponseResult getAllOrder() {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        List<OrderVO> orderVOList = orderMapper.getAllOrder(user.getId());
        return ResponseResult.success(orderVOList);
    }

    /**
     * 管理员获取未处理工单
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult getAllWaitingOrder() {
        List<Order> allWaitingOrders = orderMapper.getAllWaitingOrder();
        List<WaitingOrderVO> waitingOrderVOList = new ArrayList<>();
        for (Order order : allWaitingOrders) {
            waitingOrderVOList.add(new WaitingOrderVO(order, userMapper.selectNicknameById(order.getUserId())));
        }
        return ResponseResult.success(waitingOrderVOList);
    }

    /**
     * 管理员回复工单
     *
     * @param replyOrderDTO 回复的内容
     * @return 返回结果
     */
    @Override
    public ResponseResult replyWaitingOrder(ReplyOrderDTO replyOrderDTO) {
        orderMapper.updateReplyMessageById(replyOrderDTO);
        Long userId = orderMapper.getUserIdById();
        String userEmail = userMapper.selectEmailById(userId);
        emailService.sentEmail(new EmailInfoDTO(userEmail, "[WindyApi开放平台]工单已处理", "您提交的工单管理员已处理，管理员回复如下\n" + replyOrderDTO.getReplyMessage() + "\n感谢您对平台的支持"));
        return ResponseResult.success();
    }
}
