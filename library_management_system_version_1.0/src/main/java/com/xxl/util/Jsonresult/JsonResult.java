package com.xxl.util.Jsonresult;

/**
 * @author:xxl
 * @date:2023/2/21
 * @ProjectDescription:  返回json
 */
public interface JsonResult {
    /**
     * 返回失败的json
     * @author xxl
     * @param  msg
     * @return String
     */
    String failResult_(Object msg);
    /**
     * 返回成功json
     * @author xxl
     * @param  msg
     * @return String
     */
    String successResult_(Object msg);
}
