package com.xxl.interceptor;

import com.xxl.util.ConstantUtil;
import com.xxl.util.IoUtil;
import com.xxl.util.Jsonresult.JsonResultImpl;
import com.xxl.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription: 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    private Logger log = LogUtil.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("拦截请求url="+requestURI);
        HttpSession session = request.getSession();
        log.info("sessionId="+session.getId());
        //拦截ajax的option(用于试探服务端是否有反应)请求
        //equalsIgnoreCase()忽略大小写
        if (request.getMethod().equalsIgnoreCase(ConstantUtil.REQUEST_METHOD_OPTION)) {
            log.info("拦截到option请求，"+request.getMethod());
            return false;
        }
        Object attribute = session.getAttribute(ConstantUtil.USER_INFO);
        //有用户信息
        if (attribute != null) {
            return true;
        }
        //不拦截登录、注册
        String requestUrl = "login";
        String register = "register";
        if (requestURI.contains(requestUrl)) {
            return true;
        }
        if (requestURI.contains(register)) {
            return true;
        }
        //没有用户信息，转发到登录页面
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConstantUtil.HINT,"请先登录");
        map.put(ConstantUtil.ADDRESS,ConstantUtil.URL_LOGIN);
        //返回提示信息
        String s = JsonResultImpl.failResult(map);
        IoUtil.gbkToUtf8(response,s);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
