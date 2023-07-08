package top.whiteleaf03.api.service.interfaceinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.entity.InterfaceInfo;
import top.whiteleaf03.api.modal.vo.PageSizeVO;
import top.whiteleaf03.api.modal.dto.NewInterfaceDTO;
import top.whiteleaf03.api.modal.dto.PageNumDTO;
import top.whiteleaf03.api.modal.dto.UpdateInterfaceStatusDTO;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.InterfaceDocVO;
import top.whiteleaf03.api.modal.vo.InterfaceInfoVO;
import top.whiteleaf03.api.modal.vo.OnlineInterfaceInfoVO;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
    private final InterfaceInfoMapper interfaceInfoMapper;
    private final UserMapper userMapper;

    @Autowired
    public InterfaceInfoServiceImpl(InterfaceInfoMapper interfaceInfoMapper, UserMapper userMapper) {
        this.interfaceInfoMapper = interfaceInfoMapper;
        this.userMapper = userMapper;
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
        return ResponseResult.success(new PageSizeVO(total));
    }

    /**
     * 查询活跃接口信息
     *
     * @param pageNumDTO 分页信息
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAliveInterfaceByPage(PageNumDTO pageNumDTO) {
        List<OnlineInterfaceInfoVO> onlineInterfaceInfoVOList = interfaceInfoMapper.selectIdAndNameAndDescribeByPageNumAndStatusAndIsDelete(pageNumDTO);
        return ResponseResult.success(onlineInterfaceInfoVOList);
    }

    /**
     * 获取接口文档
     *
     * @param interfaceIdDTO 接口id
     * @return 返回结果
     */
    @Override
    public ResponseResult queryInterfaceDocById(InterfaceIdDTO interfaceIdDTO) {
        InterfaceDocVO interfaceDocVO = interfaceInfoMapper.selectNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderAndStatusAndCreateTimeAndUpdateTimeByIdAndIsDelete(interfaceIdDTO);
        return ResponseResult.success(interfaceDocVO);
    }

    /**
     * 获取所有接口分页总数
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAllInterfacePageSize() {
        Long total = interfaceInfoMapper.selectCountByIsDelete();
        return ResponseResult.success(new PageSizeVO(total));
    }

    /**
     * 根据页号查询接口信息
     *
     * @param pageNumDTO 页号
     * @return 返回接口信息
     */
    @Override
    public ResponseResult queryAllInterfaceByPage(PageNumDTO pageNumDTO) {
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.selectNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderAndStatusAndUserIdAndCreateTimeAndUpdateTimeByPageNumAndIsDelete(pageNumDTO);
        List<InterfaceInfoVO> interfaceInfoVOs = new ArrayList<>();
        for (InterfaceInfo interfaceInfo : interfaceInfos) {
            interfaceInfoVOs.add(new InterfaceInfoVO(interfaceInfo, userMapper.selectNicknameById(interfaceInfo.getId())));
        }
        return ResponseResult.success(interfaceInfoVOs);
    }
}
