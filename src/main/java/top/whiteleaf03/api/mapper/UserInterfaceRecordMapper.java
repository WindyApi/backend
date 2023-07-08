package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.InsertUserInterfaceRecordDTO;
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
    List<UserInterfaceRecord> selectInterfaceInfoIdAndTotalNumAndLeftNumAndCreateTimeAndUpdateTime(Long userId);
}
