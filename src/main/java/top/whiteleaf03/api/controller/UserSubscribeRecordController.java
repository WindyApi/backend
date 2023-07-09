package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserSubscribeDTO;
import top.whiteleaf03.api.service.usersubscriberecord.UserSubscribeRecordService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/user_subscribe_record")
public class UserSubscribeRecordController {
    private final UserSubscribeRecordService userSubscribeRecordService;

    @Autowired
    public UserSubscribeRecordController(UserSubscribeRecordService userSubscribeRecordService) {
        this.userSubscribeRecordService = userSubscribeRecordService;
    }

    @TokenCheck("")
    @PostMapping("")
    public ResponseResult newRecord(@RequestBody UserSubscribeDTO userSubscribeDTO) {
        return userSubscribeRecordService.newRecord(userSubscribeDTO);
    }

    @TokenCheck("")
    @GetMapping("")
    public ResponseResult queryRecord(InterfaceIdDTO interfaceIdDTO) {
        return userSubscribeRecordService.queryRecord(interfaceIdDTO);
    }

    @TokenCheck("admin")
    @GetMapping("all")
    public ResponseResult queryAllUserRecord(InterfaceIdDTO interfaceIdDTO) {
        return userSubscribeRecordService.queryAllUserRecord(interfaceIdDTO);
    }
}
