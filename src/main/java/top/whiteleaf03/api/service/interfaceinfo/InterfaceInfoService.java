package top.whiteleaf03.api.service.interfaceinfo;

import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
import top.whiteleaf03.api.modal.vo.OnlineInterfaceInfoVO;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
public interface InterfaceInfoService {
    /**
     * 新增接口
     *
     * @param newInterfaceDTO 新增接口的信息
     * @return 返回结果
     */
    ResponseResult insert(NewInterfaceDTO newInterfaceDTO);

    /**
     * 修改接口状态
     * 0 下线
     * 1 上线
     *
     * @param updateInterfaceStatusDTO 修改接口的信息
     * @return 返回结果
     */
    ResponseResult updateStatusById(UpdateInterfaceStatusDTO updateInterfaceStatusDTO);

    /**
     * 上下线接口
     *
     * @param updateInterfaceStatusDTO 接口信息
     * @return 返回结果
     */
    ResponseResult setStatus(UpdateInterfaceStatusDTO updateInterfaceStatusDTO);

    /**
     * 查询活跃接口信息
     *
     * @return 返回结果
     */
    ResponseResult queryAliveInterface();
}
