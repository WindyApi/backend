package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.service.userinterfaceinfo.UserInterfaceRecordService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/user_interface_record")
public class UserInterfaceRecordController {
    private final UserInterfaceRecordService userInterfaceRecordService;

    @Autowired
    public UserInterfaceRecordController(UserInterfaceRecordService userInterfaceRecordService) {
        this.userInterfaceRecordService = userInterfaceRecordService;
    }

    @TokenCheck({})
    @GetMapping("")
    ResponseResult queryByUserId() {
        return userInterfaceRecordService.queryByUserId();
    }
}
