package com.xxl.util;


import org.apache.log4j.Logger;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription: 打印日志工具类
 */
public class LogUtil {
    public static Logger getLogger(Class cls) {
        return Logger.getLogger(cls);
    }

}
