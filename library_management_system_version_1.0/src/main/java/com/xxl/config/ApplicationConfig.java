package com.xxl.config;

import org.springframework.context.annotation.*;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription: 总配置类
 */
@Configuration
@Import({DaoConfig.class , SpringMvcConfig.class})
@EnableAspectJAutoProxy
@ComponentScans({
        @ComponentScan("com.xxl.controller"),
        @ComponentScan("com.xxl.service"),
        @ComponentScan("com.xxl.aop"),
        @ComponentScan("com.xxl.util")
})
public class ApplicationConfig {

}
