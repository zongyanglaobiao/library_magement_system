package com.xxl.interceptor;

import com.xxl.pojo.User;
import com.xxl.util.ConstantUtil;
import com.xxl.util.IoUtil;
import com.xxl.util.Jsonresult.JsonResultImpl;
import com.xxl.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription: 权限过滤器
 */
public class PermissionsInterceptor implements HandlerInterceptor {
    Logger log = LogUtil.getLogger(PermissionsInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User attribute = (User) session.getAttribute(ConstantUtil.USER_INFO);
        log.info("PermissionsInterceptor拦截,[控制器方法="+handler+"]【用户信息="+attribute.toString()+"]");
        if (attribute.getRoleId() == ConstantUtil.ADMIN_PERMISSIONS) {
            return true;
        }
        IoUtil.gbkToUtf8(response,JsonResultImpl.failResult("权限不够"));
        return false;
    }
}
