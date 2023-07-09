package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.InsertUserInterfaceRecordDTO;
import top.whiteleaf03.api.modal.dto.UserIdAndInterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserSubscribeDTO;
import top.whiteleaf03.api.modal.entity.UserInterfaceRecord;

import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Mapper
public interface UserInterfaceRecordMapper {
    /**
     * 新增记录
     *
     * @param insertUserInterfaceRecordDTO 记录信息
     */
    void insert(InsertUserInterfaceRecordDTO insertUserInterfaceRecordDTO);

    /**
     * 用户获取各接口调用次数
     *
     * @param userId 用户id
     * @return 返回结果
     */
    List<UserInterfaceRecord> selectInterfaceIdAndTotalNumAndLeftNumAndCreateTimeAndUpdateTime(Long userId);

    /**
     * 增加用户调用次数
     *
     * @param userSubscribeDTO 用户id和接口id
     */
    void increase(UserSubscribeDTO userSubscribeDTO);

    /**
     * 根据用户id和接口id查询用户是否开通过
     *
     * @param userIdAndInterfaceIdDTO 用户id和接口id
     * @return 返回结果
     */
    Long selectIdByUserIdAndInterfaceId(UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO);
}
