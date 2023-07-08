package top.whiteleaf03.api.service.userinterfaceinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.mapper.UserInterfaceRecordMapper;
import top.whiteleaf03.api.modal.dto.InsertUserInterfaceRecordDTO;
import top.whiteleaf03.api.modal.dto.InterfaceIdDTO;
import top.whiteleaf03.api.modal.entity.InterfaceInfo;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.entity.UserInterfaceRecord;
import top.whiteleaf03.api.modal.vo.InterfaceDocVO;
import top.whiteleaf03.api.modal.vo.UserInterfaceRecordVO;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.ArrayList;
import java.util.List;

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
        List<UserInterfaceRecord> userInterfaceRecords = userInterfaceRecordMapper.selectInterfaceInfoIdAndTotalNumAndLeftNumAndCreateTimeAndUpdateTime(user.getId());
        for (UserInterfaceRecord userInterfaceRecord : userInterfaceRecords) {
            String interfaceName = interfaceInfoMapper.selectNameAndDescribeAndMethodAndUrlAndParamsAndRequestHeaderAndResponseHeaderAndStatusAndCreateTimeAndUpdateTimeByIdAndIsDelete(new InterfaceIdDTO(userInterfaceRecord.getInterfaceInfoId())).getName();
            userInterfaceRecordVOs.add(new UserInterfaceRecordVO(userInterfaceRecord, interfaceName));
        }
        return ResponseResult.success(userInterfaceRecordVOs);
    }
}
