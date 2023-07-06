package top.whiteleaf03.api.service.interfaceinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
import top.whiteleaf03.api.modal.vo.OnlineInterfaceInfoVO;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
    private final InterfaceInfoMapper interfaceInfoMapper;

    @Autowired
    public InterfaceInfoServiceImpl(InterfaceInfoMapper interfaceInfoMapper) {
        this.interfaceInfoMapper = interfaceInfoMapper;
    }

    /**
     * 新增接口
     *
     * @param newInterfaceDTO 新增接口的信息
     * @return 返回结果
     */
    @Override
    public ResponseResult insert(NewInterfaceDTO newInterfaceDTO) {
        interfaceInfoMapper.insert(newInterfaceDTO);
        return ResponseResult.success();
    }

    /**
     * 修改接口状态
     * 0 下线
     * 1 上线
     *
     * @param updateInterfaceStatusDTO 修改接口的信息
     * @return 返回结果
     */
    @Override
    public ResponseResult updateStatusById(UpdateInterfaceStatusDTO updateInterfaceStatusDTO) {
        interfaceInfoMapper.updateStatusById(updateInterfaceStatusDTO);
        return ResponseResult.success();
    }

    /**
     * 上下线接口
     *
     * @param updateInterfaceStatusDTO 接口信息
     * @return 返回结果
     */
    @Override
    public ResponseResult setStatus(UpdateInterfaceStatusDTO updateInterfaceStatusDTO) {
        interfaceInfoMapper.updateStatusById(updateInterfaceStatusDTO);
        return ResponseResult.success();
    }

    /**
     * 查询活跃接口信息
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAliveInterface() {
        List<OnlineInterfaceInfoVO> onlineInterfaceInfoVOList = interfaceInfoMapper.selectIdAndNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderByStatusAndIsDelete();
        return ResponseResult.success(onlineInterfaceInfoVOList);
    }
}
