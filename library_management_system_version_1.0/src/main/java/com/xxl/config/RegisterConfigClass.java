package com.xxl.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:  注册配置类，给dispatcherServlet读取
 */
public class RegisterConfigClass extends AnnotationConfigWebApplicationContext {
    public RegisterConfigClass() {
        register(ApplicationConfig.class);
    }
}
