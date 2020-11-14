package com.cdkjframework.util.tool;

import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.util.log.LogUtils;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ResultUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ResultUtils {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(ResultUtils.class.getName());

    /**
     * 使用response输出JSON
     *
     * @param response 响应
     * @param result 数据
     */
    public static void responseJson(ServletResponse response, Object result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding(EncodingConsts.UTF8);
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JsonUtils.objectToJsonString(result));
        } catch (Exception e) {
            logUtils.error(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
