package top.whiteleaf03.api.service.interfaceinfo;

import top.whiteleaf03.api.modal.dto.*;
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
     * 获取分页总数
     *
     * @return 返回结果
     */
    ResponseResult queryAliveInterfacePageSize();

    /**
     * 查询活跃接口信息
     *
     * @param pageNumDTO 分页信息
     * @return 返回结果
     */
    ResponseResult queryAliveInterfaceByPage(PageNumDTO pageNumDTO);

    /**
     * 获取接口文档
     *
     * @param interfaceIdDTO 接口id
     * @return 返回结果
     */
    ResponseResult queryInterfaceDocById(InterfaceIdDTO interfaceIdDTO);

    /**
     * 获取所有接口分页总数
     *
     * @return 返回结果
     */
    ResponseResult queryAllInterfacePageSize();

    /**
     * 根据页号查询接口信息
     *
     * @param pageNumDTO 页号
     * @return 返回接口信息
     */
    ResponseResult queryAllInterfaceByPage(PageNumDTO pageNumDTO);

    /**
     * 修改接口信息
     *
     * @param updateInterfaceDTO 要修改的接口信息
     * @return 返回结果
     */
    ResponseResult updateInterfaceById(UpdateInterfaceDTO updateInterfaceDTO);

    /**
     * 搜索接口
     *
     * @param searchDTO 包含关键词
     * @return 返回结果
     */
    ResponseResult getInterfaceInfoByKeyword(SearchDTO searchDTO);
}
