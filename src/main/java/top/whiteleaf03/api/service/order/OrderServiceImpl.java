package top.whiteleaf03.api.service.order;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.mapper.OrderMapper;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.*;
import top.whiteleaf03.api.modal.entity.Order;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.OrderStatusVO;
import top.whiteleaf03.api.modal.vo.OrderVO;
import top.whiteleaf03.api.modal.vo.PageSizeVO;
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
    private final InterfaceInfoMapper interfaceInfoMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, UserMapper userMapper, EmailService emailService, InterfaceInfoMapper interfaceInfoMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.interfaceInfoMapper = interfaceInfoMapper;
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
     * 获取分页总数
     *
     * @param type self: 用户获取自身工单总数 / admin: 管理员获取待审核总数
     */
    @Override
    public ResponseResult getPageSum(String type) {
        // 用户查自身
        if ("self".equals(type)) {
            User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
            Long userId = user.getId();
            Long total = orderMapper.getCountByUserId(userId);
            return ResponseResult.success(new PageSizeVO(total));
        }
        // 管理员查待审批
        if ("admin".equals(type)) {
            OrderStatusVO orderStatusVO = orderMapper.getCountByStatus();
            return ResponseResult.success(orderStatusVO);
        }
        // 不可能发生吧
        return ResponseResult.error();
    }

    /**
     * 查询自己的工单
     *
     * @param querySelfOrderDTO 用户id和页号
     */
    @Override
    public ResponseResult getSelfOrder(QuerySelfOrderDTO querySelfOrderDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        querySelfOrderDTO.setUserId(user.getId());
        List<Order> orderList = orderMapper.getSelfOrder(querySelfOrderDTO);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderList) {
            String interfaceName = interfaceInfoMapper.selectNameById(order.getInterfaceId());
            if (StrUtil.isBlank(interfaceName)) {
                interfaceName = "非特定接口";
            }
            orderVOList.add(new OrderVO(order, interfaceName));
        }
        return ResponseResult.success(orderVOList);
    }

    /**
     * 管理员获取工单
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult getAllOrder(QueryAllOrderDTO queryAllOrderDTO) {
        List<Order> allWaitingOrders = orderMapper.getAllOrder(queryAllOrderDTO);
        List<WaitingOrderVO> waitingOrderVOList = new ArrayList<>();
        for (Order order : allWaitingOrders) {
            String nickname = userMapper.selectNicknameById(order.getUserId());
            String interfaceName = interfaceInfoMapper.selectNameById(order.getInterfaceId());
            if (StrUtil.isBlank(interfaceName)) {
                interfaceName = "非特定接口";
            }
            waitingOrderVOList.add(new WaitingOrderVO(order, nickname, interfaceName));
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
