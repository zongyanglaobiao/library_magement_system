package com.xxl.util.Jsonresult;

import com.alibaba.fastjson2.JSON;
import com.xxl.aop.Jsonresult.JsonResultAop;
import com.xxl.util.LogUtil;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: xxl
 * @date: 2023/2/19
 * @ProjectDescription:  封装json结果一般格式为
 * 成功
 * {
 *     "date":"2001-324-45"
 *     "code":200
 *     "msg":"操作成功"
 *     "data":"xxx"
 * }
  失败
 * {
 *     "date":"2001-324-45"
 *     "code":500
 *     "msg":"xxx"
 * }
 */
@Component
public class JsonResultImpl implements Serializable,JsonResult{
    private static final String MESSAGE = "msg";
    private static final String CODE = "code";
    private static final String DATE = "date";
    private  static final String DATA_CONTENT = "data";
    private static  final int FAIL_CODE = 500;
    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "操作成功";
    /**
     *  时间
     */
    private Logger logger = LogUtil.getLogger(JsonResultAop.class);

    @Override
    public String failResult_(Object msg) {
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        HashMap<String,Object> map = new HashMap<>();
        //时间
        map.put(DATE,date);
        //返回码
        map.put(CODE,FAIL_CODE);
        //返回提示信息
        map.put(MESSAGE,msg);
        return JSON.toJSONString(map);
    }

    @Override
    public String successResult_(Object msg) {
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        HashMap<String,Object> map = new HashMap<>();
        //时间
        map.put(DATE,date);
        //返回码
        map.put(CODE,SUCCESS_CODE);
        //返回提示信息
        map.put(MESSAGE,SUCCESS_MESSAGE);
        //返回数据
        map.put(DATA_CONTENT, msg);
        return JSON.toJSONString(map);
    }
    private  static JsonResult JSON_RESULT;
    @Resource
    public  void setJsonResult(JsonResult jsonResult) {
        JSON_RESULT = jsonResult;
    }

    public static String failResult(Object msg) {
        return JSON_RESULT.failResult_(msg);
    }

    public static String successResult(Object obj) {
        return JSON_RESULT.successResult_(obj);
    }
}
