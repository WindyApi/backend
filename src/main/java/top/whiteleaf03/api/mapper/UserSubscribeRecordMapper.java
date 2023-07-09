package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.UserIdAndInterfaceIdDTO;
import top.whiteleaf03.api.modal.dto.UserSubscribeDTO;
import top.whiteleaf03.api.modal.entity.UserSubscribeRecord;

import java.util.List;

/**
 * @author WhiteLeaf03
 */
@Mapper
public interface UserSubscribeRecordMapper {
    /**
     * 新增用户订阅记录
     *
     * @param userSubscribeDTO 订阅信息
     */
    void insert(UserSubscribeDTO userSubscribeDTO);

    /**
     * 查询用户订阅记录
     *
     * @param userIdAndInterfaceIdDTO (可选) 用户id和接口id
     * @return 返回结果
     */
    List<UserSubscribeRecord> selectUserIdAndInterfaceInfoIdAndIncreaseAndPriceAndCreateTimeByUserIdOrInterfaceInfoId(UserIdAndInterfaceIdDTO userIdAndInterfaceIdDTO);
}
