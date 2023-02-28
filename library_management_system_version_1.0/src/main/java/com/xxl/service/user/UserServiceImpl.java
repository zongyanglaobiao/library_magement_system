package com.xxl.service.user;

import com.xxl.dao.user.UserMapper;
import com.xxl.pojo.User;
import com.xxl.util.LogUtil;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:  用户业务类,把所有可能发生的错误集中在业务层
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService{
    Logger log = LogUtil.getLogger(UserServiceImpl.class);
    UserMapper userMapper;

    @Resource
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int addUser(User user) {
        HashMap<String, Object> map = null;
        try {
            Class<? extends User> aClass = user.getClass();
            map = new HashMap<>();
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                String fieldName = declaredField.getName();
                //把字段名转换为数据库col名
                for (char c : fieldName.toCharArray()) {
                    if (65 <= c && c <= 90){
                        int index = fieldName.lastIndexOf(c);
                        //第一个大写字母开始截取
                        String rearName = fieldName.substring(index);
                        //第一个大写字母前面
                        String frontName = fieldName.substring(0, index);
                        fieldName = frontName+"_"+rearName;
                        Object fieldValue = declaredField.get(user);
                        map.put(fieldName.toLowerCase(),fieldValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        log.info(map.toString());
        return  userMapper.addUser(map);
    }

    @Override
    public int updateUserByUserPhone(User user, String phone) {
        HashMap<String, Object> map = null;
        try {
            map = new HashMap<>();
            Class<? extends User> aClass = user.getClass();
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                String name = declaredField.getName();
                Object o = declaredField.get(user);
                map.put(name,o);
            }
            //通过用户phone来查找用户
            map.put("phone",phone);
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        return userMapper.updateUserByUserPhone(map);
    }

    @Override
    public int deleteUser(String userPhone) {
        return userMapper.deleteUser(userPhone);
    }

    @Override
    public List<User> queryUser(String userPhone, String userName, int roleId, String roleName) {
        return userMapper.queryUser(userPhone,userName,roleId,roleName);
    }

    @Override
    public User userLogin(String userPhone, String password) {
        return userMapper.userLogin(userPhone,password);
    }
}
