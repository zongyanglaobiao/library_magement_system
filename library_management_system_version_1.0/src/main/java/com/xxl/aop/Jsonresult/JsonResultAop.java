package com.xxl.aop.Jsonresult;


import com.xxl.util.LogUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: xxl
 * @date: 2023/2/21
 * @ProjectDescription:  json的切面类，主要打印返回的给前端的json字符串
 */
@Aspect
@Component
public class JsonResultAop {
    Logger logger = LogUtil.getLogger(JsonResultAop.class);
    @AfterReturning(pointcut = "pointCut()",returning = "json")
    public void logJson(JoinPoint jp,String json) {
        logger.info("[json="+json+"][method="+jp.getSignature()+"]");
    }
    @Pointcut("execution(* com.xxl.util.Jsonresult..* (..))")
    public void pointCut() {}
}
