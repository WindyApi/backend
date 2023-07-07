package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.PageNumDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
import top.whiteleaf03.api.modal.entity.InterfaceInfo;
import top.whiteleaf03.api.modal.vo.InterfaceDocVO;
import top.whiteleaf03.api.modal.vo.OnlineInterfaceInfoVO;

import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Mapper
public interface InterfaceInfoMapper {
    /**
     * 新建接口
     *
     * @param newInterfaceDTO 接口信息
     */
    void insert(NewInterfaceDTO newInterfaceDTO);

    /**
     * 获取分页数量
     *
     * @return 返回分页总数
     */
    Long selectCountByStatusAndIsDelete();

    /**
     * 分页查询活跃的接口
     *
     * @param pageNumDTO 包含页号
     * @return 返回活跃接口信息
     */
    List<OnlineInterfaceInfoVO> selectIdAndNameAndDescribeByPageNumAndStatusAndIsDelete(PageNumDTO pageNumDTO);

    /**
     * 获取接口文档
     *
     * @param interfaceIdDTO 包含页号
     * @return 返回活跃接口信息
     */
    InterfaceDocVO selectNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderAndStatusAndCreateTimeAndUpdateTimeByIdAndIsDelete(InterfaceIdDTO interfaceIdDTO);

    /**
     * 上线/下线接口
     *
     * @param updateInterfaceStatusDTO 接口信息
     */
    void updateStatusById(UpdateInterfaceStatusDTO updateInterfaceStatusDTO);

    /**
     * 获取所有接口分页数量
     *
     * @return 返回分页总数
     */
    Long selectCountByIsDelete();

    /**
     * 分页获取接口详细信息
     *
     * @param pageNumDTO 页号
     * @return 返回接口信息
     */
    List<InterfaceInfo> selectNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderAndStatusAndUserIdAndCreateTimeAndUpdateTimeByPageNumAndIsDelete(PageNumDTO pageNumDTO);
}
