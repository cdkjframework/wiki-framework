package com.cdkjframework.util.network;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.JsonUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p> 使用response输出JSON </p>
 *
 * @author : xialin
 * @description :
 * @date : 2019/10/11 17:27
 */

public class ResponseUtils {

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(ResponseUtils.class);

  /**
   * 使用 response 输出JSON
   *
   * @param message 消息
   */
  public static void out(String message) {
    out(HttpServletUtils.getResponse(), message);
  }

  /**
   * 使用 response 输出JSON
   *
   * @param builder 结果
   */
  public static void out(ResponseBuilder builder) {
    out(HttpServletUtils.getResponse(), JsonUtils.objectToJsonString(builder));
  }

  /**
   * 使用 response 输出JSON
   *
   * @param response 响应
   * @param builder  结果
   */
  public static void out(ServletResponse response, ResponseBuilder builder) {
    out(response, JsonUtils.objectToJsonString(builder));
  }

  /**
   * 响应内容
   *
   * @param httpServletResponse 响应
   * @param message             消息
   * @param status              状态
   */
  public static void getResponse(HttpServletResponse httpServletResponse, String message, Integer status) {
    PrintWriter writer = null;
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json; charset=utf-8");
    try {
      writer = httpServletResponse.getWriter();
      writer.print(JsonUtils.objectToJsonString(new ResponseBuilder(status, message, null)));
    } catch (IOException e) {
      logUtils.error(e.getMessage());
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }

  /**
   * 输出响应消息
   *
   * @param response 响应
   * @param message  消息
   */
  private static void out(ServletResponse response, String message) {
    PrintWriter out = null;
    try {
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      out = response.getWriter();
      out.println(message);
    } catch (Exception e) {
      logUtils.error(e, "输出JSON出错");
    } finally {
      if (out != null) {
        out.flush();
        out.close();
      }
    }
  }
}
