package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.RegisterDTO;
import top.whiteleaf03.api.modal.entity.User;

/**
 * @author WhiteLeaf03
 */
@Mapper
public interface UserMapper {
    /**
     * 新用户注册
     *
     * @param registerDTO 用户信息
     */
    void insert(RegisterDTO registerDTO);

    /**
     * 用户登录
     *
     * @param account 账号
     * @return 用户信息
     */
    User selectIdAndNicknameAndAvatarAndGenderAndRoleAndPasswordAndPasswordAndCreateTimeByAccount(String account);

    /**
     * 根据id获取用户昵称
     *
     * @param id 用户id
     * @return 用户昵称
     */
    String selectNicknameById(Long id);
}
