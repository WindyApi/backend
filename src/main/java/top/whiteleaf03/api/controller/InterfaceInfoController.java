package top.whiteleaf03.api.controller;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.modal.dto.*;
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

    @TokenCheck("admin")
    @PostMapping("")
    public ResponseResult insert(@RequestBody NewInterfaceDTO newInterfaceDTO) {
        return interfaceInfoService.insert(newInterfaceDTO);
    }

    @TokenCheck("admin")
    @PutMapping("status")
    public ResponseResult setStatus(@RequestBody UpdateInterfaceStatusDTO updateInterfaceStatusDTO) {
        return interfaceInfoService.setStatus(updateInterfaceStatusDTO);
    }

    @TokenCheck({})
    @GetMapping("online")
    public ResponseResult queryAliveInterfaceByPage(PageNumDTO pageNumDTO) {
        Integer pageNum = pageNumDTO.getPageNum();
        if (ObjectUtil.isNotNull(pageNum) && pageNum > 0) {
            //携带页号，返回指定分页的信息
            return interfaceInfoService.queryAliveInterfaceByPage(pageNumDTO);
        }
        //未携带页号，返回分页数量
        return interfaceInfoService.queryAliveInterfacePageSize();
    }

    @TokenCheck({})
    @GetMapping("")
    public ResponseResult queryInterfaceDoc(InterfaceIdDTO interfaceIdDTO) {
        return interfaceInfoService.queryInterfaceDocById(interfaceIdDTO);
    }

    @TokenCheck("admin")
    @GetMapping("all")
    public ResponseResult queryAllInterfaceByPage(PageNumDTO pageNumDTO) {
        Integer pageNum = pageNumDTO.getPageNum();
        if (ObjectUtil.isNotNull(pageNum) && pageNum > 0) {
            //携带页号，返回指定分页的信息
            return interfaceInfoService.queryAllInterfaceByPage(pageNumDTO);
        }
        //未携带页号，返回分页数量
        return interfaceInfoService.queryAllInterfacePageSize();
    }

    @TokenCheck("admin")
    @PutMapping("")
    public ResponseResult updateInterfaceById(@RequestBody UpdateInterfaceDTO updateInterfaceDTO) {
        return interfaceInfoService.updateInterfaceById(updateInterfaceDTO);
    }

    @TokenCheck({})
    @GetMapping("search")
    public ResponseResult getInterfaceInfoByKeyword(SearchDTO searchDTO) {
        return interfaceInfoService.getInterfaceInfoByKeyword(searchDTO);
    }
}
