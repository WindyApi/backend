package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.whiteleaf03.api.service.interfaceinvokerecord.InterfaceInvokeRecordService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/system")
public class SystemController {
    private final InterfaceInvokeRecordService interfaceInvokeRecordService;

    @Autowired
    public SystemController(InterfaceInvokeRecordService interfaceInvokeRecordService) {
        this.interfaceInvokeRecordService = interfaceInvokeRecordService;
    }

    @GetMapping("")
    public ResponseResult getRecentRecord() {
        return interfaceInvokeRecordService.getRecentRecord();
    }
}
