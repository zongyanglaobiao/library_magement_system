package com.xxl.util;

import java.io.Serializable;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:
 */
public class ConstantUtil implements Serializable {
    public final static String URL = "http://localhost:8080/";
    public final static String USER_INFO = "user_info";
    public final static String URL_LOGIN = URL+"library_management_system/user/login";
    public final static String HINT ="提示信息";
    public final static String ADDRESS ="地址";
    public final static String REQUEST_METHOD_OPTION = "options";
    /**
     *  管理者权限为1
     */
    public final static int ADMIN_PERMISSIONS = 1;
    /**
     *  公告的主键只有一个，且只有一条
     */
    public final static int ANNOUNCEMENT_ID = 1;
    /**
     *  已经借出去
     */
    public final static int BORROWED = 1;
    /**
     *  没有借出去
     */
    public final static int BORROW = 0;
    /**
     * 存储图片地址
     */
    public final static String ImgPath = "D:\\Program Files (x86)\\idea\\IDEAproject\\library_management_system\\library_management_system_version_1.0\\src\\main\\webapp\\WEB-INF\\bookimg\\";

}
