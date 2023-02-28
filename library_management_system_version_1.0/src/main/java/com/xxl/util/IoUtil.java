package com.xxl.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author: xxl
 * @date: 2023/2/25
 * @ProjectDescription: 流工具类
 */
public class IoUtil {
    public static void gbkToUtf8(HttpServletResponse response,String hint){
        try (
                ServletOutputStream outputStream = response.getOutputStream();
                //GBK转换为utf-8
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                )
        {
            outputStreamWriter.write(hint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传图片
     * @author xxl
     * @param
     * @return
     */

}
