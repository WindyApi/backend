package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
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
     * 查询活跃的接口
     *
     * @return 返回活跃接口信息
     */
    List<OnlineInterfaceInfoVO> selectIdAndNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderByStatusAndIsDelete();

    /**
     * 上线/下线接口
     *
     * @param updateInterfaceStatusDTO 接口信息
     */
    void updateStatusById(UpdateInterfaceStatusDTO updateInterfaceStatusDTO);
}
