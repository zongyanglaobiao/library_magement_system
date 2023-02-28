package com.xxl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xxl
 * @date: 2023/2/26
 * @ProjectDescription: 时间工具类
 */
public class TimeUtil {
    /**
     * 得到当天时间的字符串
     * @return String
     * @author xxl
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }
    /**
     * 解析时间字符出串
     * 0:年 1：月 2：天
     * @author xxl
     * @param str
     * @return int[]
     */
    public static int[] analysisString(String str) {
        //第一次出现-
        int first = str.indexOf("-");
        //最后一次出现-
        int last = str.lastIndexOf("-");

        int year = Integer.parseInt(str.substring(0, first));
        int month = Integer.parseInt(str.substring(first+1,last));
        int day = Integer.parseInt(str.substring(last+1));

        int[] time = new int[3];
        time[0] = year;
        time[1] = month;
        time[2] = day;
        return time;
    }

    /**
     * 半个月自动删除时间的范围
     * @author xxl
     * @param
     * @return
     */
    public static String computingTime(int year,int month,int day)
    {
        //2023-1-10
        //2022-12-25
        day -= 15;
        //减十五天，小于0
        if (day <= 0) {
            day += 30 ;
            //减一个月小于0
            if (--month <= 0) {
                month += 12 ;
                year--;
            }
        }
        //判断month是否是个位数
        if (month < 10) {
            return year+"-0"+month+"-"+day;
        }
        return year+"-"+month+"-"+day;
    }
}
