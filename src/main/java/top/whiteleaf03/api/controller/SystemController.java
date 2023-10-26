package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.service.system.SystemService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/system")
public class SystemController {
    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

//    @TokenCheck({"admin"})
    @GetMapping("")
    public ResponseResult getSystemInfo() {
        return systemService.getSystemInfo();
    }
}
