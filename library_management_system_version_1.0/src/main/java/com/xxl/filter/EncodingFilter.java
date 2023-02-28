package com.xxl.filter;

import com.xxl.interceptor.LoginInterceptor;
import com.xxl.util.LogUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

import java.io.IOException;


/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:  编码过滤器,跨域问题
 */
public class EncodingFilter implements Filter {
    private Logger log = LogUtil.getLogger(LoginInterceptor.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest rep = (HttpServletRequest) request;
        //可以跨域的请求地址
        resp.setHeader("Access-Control-Allow-Origin","http://127.0.0.1:5500");
        //跨域请求的间隔时间 0：无间隔 3600：每个小时检查签证
        resp.setHeader("Access-Control-Max-Age","0");
        //所有携带头部信息的都可以
        resp.setHeader("Access-Control-Allow-Headers","Content-Type");
        //可以携带凭证
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        //所有方法都可以
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
        //回应为json
        resp.setHeader("Content-Type","application/json;charset=utf-8");
        log.info("EncodingFilter过滤");
        chain.doFilter(request,response);

    }
}
