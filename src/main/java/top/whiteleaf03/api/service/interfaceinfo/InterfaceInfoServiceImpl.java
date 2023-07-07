package top.whiteleaf03.api.service.interfaceinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.modal.vo.AliveInterfacePageSizeVo;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.QueryAliveInterfaceByPageDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
import top.whiteleaf03.api.modal.entity.User;
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
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        newInterfaceDTO.setUserId(user.getId());
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
     * 获取分页总数
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAliveInterfacePageSize() {
        Long total = interfaceInfoMapper.selectCountByStatusAndIsDelete();
        Long pageSize = (long) Math.ceil((double) total / 10);
        AliveInterfacePageSizeVo aliveInterfacePageSizeVo = new AliveInterfacePageSizeVo(pageSize);
        return ResponseResult.success(aliveInterfacePageSizeVo);
    }

    /**
     * 查询活跃接口信息
     *
     * @param queryAliveInterfaceByPageDTO 分页信息
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAliveInterfaceByPage(QueryAliveInterfaceByPageDTO queryAliveInterfaceByPageDTO) {
        List<OnlineInterfaceInfoVO> onlineInterfaceInfoVOList = interfaceInfoMapper.selectIdAndNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderByPageNumAndStatusAndIsDelete(queryAliveInterfaceByPageDTO);
        return ResponseResult.success(onlineInterfaceInfoVOList);
    }
}
