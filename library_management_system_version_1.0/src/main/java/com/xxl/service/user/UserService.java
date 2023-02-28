package com.xxl.service.user;

import com.xxl.pojo.User;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:xxl
 * @date:2023/2/19
 * @ProjectDescription:  用户业务
 */
public interface UserService {
    /**
     * 添加一个用户
     * @author xxl
     * @param  user
     * @return  int
     */
    int addUser(User user);
    /**
     * 修改一个用户，需要权限
     * @author xxl
     * @param  user
     * @param  phone
     * @return  int
     */
    int updateUserByUserPhone(User user,String phone);
    /**
     * 删除用户根据手机号 ，需要权限
     * @author xxl
     * @param  userPhone
     * @return int
     */
    int deleteUser(String userPhone);
    /**
     * 根据手机号码、用户名、角色id、角色名查找用户
     * 全为空就是查询所有用户
     * @author xxl
     * @param userPhone
     * @param userName
     * @param roleId
     * @param roleName
     * @return List<User>
     */
    List<User> queryUser(@Nullable String userPhone,
                         @Nullable String userName,
                         int roleId,
                         @Nullable String roleName);
    /**
     * 登录
     * @author xxl
     * @param  userPhone
     * @param  password
     * @return User
     */
    User userLogin(String userPhone,String password);
}
