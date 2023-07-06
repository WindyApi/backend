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
    User selectIdAndNicknameAndAvatarAndGenderAndRoleAndPasswordAndPassword(String account);
}
