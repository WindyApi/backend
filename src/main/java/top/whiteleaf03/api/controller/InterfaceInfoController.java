package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
import top.whiteleaf03.api.service.interfaceinfo.InterfaceInfoService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/interface")
public class InterfaceInfoController {
    private final InterfaceInfoService interfaceInfoService;

    @Autowired
    public InterfaceInfoController(InterfaceInfoService interfaceInfoService) {
        this.interfaceInfoService = interfaceInfoService;
    }

    @PostMapping("")
    public ResponseResult insert(@RequestBody NewInterfaceDTO newInterfaceDTO) {
        return interfaceInfoService.insert(newInterfaceDTO);
    }

    @PutMapping("status")
    public ResponseResult setStatus(@RequestBody UpdateInterfaceStatusDTO updateInterfaceStatusDTO) {
        return interfaceInfoService.setStatus(updateInterfaceStatusDTO);
    }

    @GetMapping("online")
    public ResponseResult queryAliveInterface() {
        return interfaceInfoService.queryAliveInterface();
    }
}
