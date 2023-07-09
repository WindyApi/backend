package top.whiteleaf03.api.service.userinterfaceinfo;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.mapper.UserInterfaceRecordMapper;
import top.whiteleaf03.api.modal.dto.InsertUserInterfaceRecordDTO;
import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserIdAndInterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserSubscribeDTO;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.entity.UserInterfaceRecord;
import top.whiteleaf03.api.modal.vo.UserInterfaceRecordVO;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class UserInterfaceRecordServiceImpl implements UserInterfaceRecordService {
    private final UserInterfaceRecordMapper userInterfaceRecordMapper;
    private final InterfaceInfoMapper interfaceInfoMapper;

    @Autowired
    public UserInterfaceRecordServiceImpl(UserInterfaceRecordMapper userInterfaceRecordMapper, InterfaceInfoMapper interfaceInfoMapper) {
        this.userInterfaceRecordMapper = userInterfaceRecordMapper;
        this.interfaceInfoMapper = interfaceInfoMapper;
    }

    /**
     * 新增用户调用记录
     *
     * @param insertUserInterfaceRecordDTO 信息
     * @return 结果
     */
    @Override
    public ResponseResult newUserInterfaceInfo(InsertUserInterfaceRecordDTO insertUserInterfaceRecordDTO) {
        userInterfaceRecordMapper.insert(insertUserInterfaceRecordDTO);
        return ResponseResult.success();
    }

    /**
     * 根据用户id查询
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult queryByUserId() {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        List<UserInterfaceRecordVO> userInterfaceRecordVOs = new ArrayList<>();
        List<UserInterfaceRecord> userInterfaceRecords = userInterfaceRecordMapper.selectInterfaceIdAndTotalNumAndLeftNumAndCreateTimeAndUpdateTime(user.getId());
        for (UserInterfaceRecord userInterfaceRecord : userInterfaceRecords) {
            String interfaceName = interfaceInfoMapper.selectNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderAndStatusAndCreateTimeAndUpdateTimeByIdAndIsDelete(new InterfaceIdDTO(userInterfaceRecord.getInterfaceId())).getName();
            userInterfaceRecordVOs.add(new UserInterfaceRecordVO(userInterfaceRecord, interfaceName));
        }
        return ResponseResult.success(userInterfaceRecordVOs);
    }

    /**
     * 增加可调用次数
     *
     * @param userSubscribeDTO 包含用户id 接口id 新增次数
     * @return 返回结果
     */
    @Override
    public ResponseResult increaseTotalNum(UserSubscribeDTO userSubscribeDTO) {
        Long id = userInterfaceRecordMapper.selectIdByUserIdAndInterfaceId(new UserIdAndInterfaceIdDTO(userSubscribeDTO.getUserId(), userSubscribeDTO.getInterfaceId()));
        log.info(String.valueOf(id));
        if (Objects.isNull(id)) {
            //用户未开通过 增加记录
            userInterfaceRecordMapper.insert(new InsertUserInterfaceRecordDTO(userSubscribeDTO));
        } else {
            //用户已开通过 增加次数
            userInterfaceRecordMapper.increase(userSubscribeDTO);
        }
        return ResponseResult.success();
    }
}
