package top.whiteleaf03.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.whiteleaf03.api.modal.dto.*;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.UserIdAndEmailVO;

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

    /**
     * 根据id更新头像
     *
     * @param updateAvatarDTO 用户id和头像
     */
    void updateAvatarById(UpdateAvatarDTO updateAvatarDTO);

    /**
     * 根据id获取邮件地址
     *
     * @param id 用户id
     * @return 返回结果
     */
    String selectEmailById(Long id);

    /**
     * 用户修改密码
     *
     * @param editPasswordDTO 包含用户id和新密码
     */
    void updatePasswordById(EditPasswordDTO editPasswordDTO);

    /**
     * 用户修改个人信息
     *
     * @param editUserInfoDTO 要修改的信息
     */
    void updateNicknameOrGenderOrEmailById(EditUserInfoDTO editUserInfoDTO);

    /**
     * 根据账号获取id和邮箱地址
     *
     * @param account 账号
     * @return 返回结果
     */
    UserIdAndEmailVO selectIdAndEmailByAccount(String account);

    /**
     * 根据ak获取用户的id和sk
     *
     * @param accessKey accessKey
     * @return 返回用户的id和sk
     */
    UserIdAndSecretKeyDTO selectIdAndSecretKeyByAccessKey(String accessKey);
}
