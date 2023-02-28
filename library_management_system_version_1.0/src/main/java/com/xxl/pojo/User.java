package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription: 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     *  不为null，为主键
     */
    private String userPhone;
    /**
     *  初始用户名
     */
    private String userName = "普通用户";
    /**
     *  不为null
     */
    private String userPassword;
    private String userGender;
    private String userBirthday;
    /**
     *  不为null
     */
    private int roleId;
    /**
     *  不为null
     */
    private String userEmail;
    /**
     *  用户角色,命名规则，除了数据库字段其他都必须加下划线并且都小写
     */
    private String role_name;

    public User(String userPhone, String userPassword, String userEmail) {
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        //默认为普通读者
        this.roleId = 2;
    }


    public User(String userPhone, String userName, String userPassword, String userGender, String userBirthday, String userEmail) {
        this(userPhone,userPassword,userEmail);
        this.userName = userName;
        this.userGender = userGender;
        this.userBirthday = userBirthday;
    }

}
