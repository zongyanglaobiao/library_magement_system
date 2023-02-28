package com.xxl.config;

import com.xxl.interceptor.LoginInterceptor;
import com.xxl.interceptor.PermissionsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:  spring mvc配置类
 */
@Configuration
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    /**
     *  请求拦截器：判断用户是否是登录状态
     * @author xxl
     * @return LoginInterceptor
     */
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    /**
     * 权限
     * @author xxl
     * @return PermissionsInterceptor
     */
    @Bean
    public PermissionsInterceptor permissionsInterceptor() {
        return new PermissionsInterceptor();
    }
    /**
     * 配置拦截器
     * @author xxl
     * @param  registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截不是登录状态的请求
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor());
        //添加拦截路径("/** 为所有路径")
        registration.addPathPatterns("/**");
        //添加不拦截路径
        registration.excludePathPatterns(
                "/**/*.html",
                "/**/*.js",
                "/**/*.css"
        );
        //权限拦截
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(permissionsInterceptor());
        interceptorRegistration.addPathPatterns(
                "/user/permissions/**",
                "/announcement/permissions/**",
                "/book/permissions/**"
        );
    }
}
