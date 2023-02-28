package com.xxl.dao.user;

import com.xxl.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author:xxl
 * @date:2023/2/19
 * @ProjectDescription:  用户表操作
 */
@Repository
public interface UserMapper {
    /**
     * 添加一个用户
     * @author xxl
     * @param  map
     * @return  int
     */
    int addUser(@Param("map") Map<String,Object> map);
    /**
     * 修改一个用户，需要权限
     * @author xxl
     * @param  map
     * @return  int
     */
    int updateUserByUserPhone(Map<String,Object> map);
    /**
     * 删除一个用户，需要权限
     * @author xxl
     * @param  userPhone 根据手机号码
     * @return  int
     */
    int deleteUser(@Param("userPhone") String userPhone);
    /**
     * 根据手机号码、用户名、角色id、角色名查找用户
     * @author xxl
     * @param userPhone
     * @param userName
     * @param roleId
     * @param roleName
     * @return List<User>
     */
    List<User> queryUser(@Param("userPhone")String userPhone,
                         @Param("userName")String userName,
                         @Param("roleId")int roleId,
                         @Param("roleName") String roleName);
    /**
     * 登录
     * @author xxl
     * @param  userPhone
     * @param  password
     * @return User
     */
    User userLogin(@Param("userPhone")String userPhone,
                   @Param("password")String password);
}
