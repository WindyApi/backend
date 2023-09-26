package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.modal.dto.NewOrderDTO;
import top.whiteleaf03.api.modal.dto.ReplyOrderDTO;
import top.whiteleaf03.api.service.order.OrderService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @TokenCheck("")
    @PostMapping("")
    public ResponseResult createNewOrder(@RequestBody NewOrderDTO newOrderDTO) {
        return orderService.insertOrder(newOrderDTO);
    }

    @TokenCheck("")
    @GetMapping("")
    public ResponseResult getAllOrder() {
        return orderService.getAllOrder();
    }

    @TokenCheck("admin")
    @GetMapping("all")
    public ResponseResult getAllWaitingOrder() {
        return orderService.getAllWaitingOrder();
    }

    @TokenCheck("admin")
    @PutMapping("")
    public ResponseResult replyOrder(@RequestBody ReplyOrderDTO replyOrderDTO) {
        return orderService.replyWaitingOrder(replyOrderDTO);
    }
}
