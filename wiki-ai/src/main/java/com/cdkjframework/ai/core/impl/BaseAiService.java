package com.cdkjframework.ai.core.impl;

import com.cdkjframework.ai.constant.AiConstant;
import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.AiService;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.HttpHeaderConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.enums.HttpMethodEnums;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.network.http.HttpRequestUtils;
import com.cdkjframework.util.tool.JsonUtils;
import org.apache.hc.core5.http.Method;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 基础 AiService，包含基公共参数和公共方法
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core.impl
 * @ClassName: BaseAiService
 * @Description: 基础 AiService，包含基公共参数和公共方法
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class BaseAiService implements AiService {

  /**
   * AI 服务接口
   */
  protected final AiConfig config;

  /**
   * 构造函数
   *
   * @param config AI 服务接口
   */
  public BaseAiService(final AiConfig config) {
    this.config = config;
  }

  /**
   * 发送Get请求
   *
   * @param endpoint 请求节点
   * @return 请求响应结果
   */
  protected StringBuilder get(final String endpoint) {
    //链式构建请求
    try {
      HttpRequestEntity entity = new HttpRequestEntity();
      entity.setRequestAddress(config.getApiUrl() + endpoint);
      entity.setMethod(HttpMethodEnums.GET);

      // 设置 http 请求头
      Map<String, String> headerMap = new HashMap<>(IntegerConsts.THREE);
      headerMap.put(HttpHeaderConsts.ACCEPT, AiConstant.CONTENT_TYPE_VALUE);
      headerMap.put(AiConstant.AUTHORIZATION, AiConstant.BEARER + config.getApiKey());
      entity.setHeaderMap(headerMap);
      // 构建请求体
      return HttpRequestUtils.httpRequest(entity);
    } catch (final GlobalRuntimeException e) {
      throw new GlobalRuntimeException("Failed to send GET request: " + e.getMessage(), e);
    }
  }

  /**
   * 发送Post请求
   *
   * @param endpoint  请求节点
   * @param paramJson 请求参数json
   * @return 请求响应结果
   */
  protected StringBuilder post(final String endpoint, final String paramJson) {
    //链式构建请求
    try {
      HttpRequestEntity entity = new HttpRequestEntity();
      entity.setRequestAddress(config.getApiUrl() + endpoint);
      entity.setParams(paramJson);
      entity.setContentType(AiConstant.CONTENT_TYPE_VALUE);
      entity.setMethod(HttpMethodEnums.POST);

      // 设置 http 请求头
      Map<String, String> headerMap = new HashMap<>(IntegerConsts.THREE);
      headerMap.put(HttpHeaderConsts.CONTENT_TYPE, AiConstant.CONTENT_TYPE_VALUE);
      headerMap.put(HttpHeaderConsts.ACCEPT, AiConstant.CONTENT_TYPE_VALUE);
      headerMap.put(AiConstant.AUTHORIZATION, AiConstant.BEARER + config.getApiKey());
      entity.setHeaderMap(headerMap);
      // 构建请求体
      return HttpRequestUtils.httpRequest(entity);
    } catch (final GlobalRuntimeException e) {
      throw new GlobalRuntimeException("发送POST请求失败：" + e.getMessage(), e);
    }
  }


  /**
   * 支持流式返回的 POST 请求
   *
   * @param endpoint  请求节点
   * @param paramJson 请求参数json
   */
  protected InputStream postStream(final String endpoint, final String paramJson) {
    HttpURLConnection connection = null;
    try {
      // 创建连接
      String url = config.getApiUrl() + endpoint;
      connection = buildConnection(url);
      // 发送请求体
      try (OutputStream os = connection.getOutputStream()) {
        os.write(paramJson.getBytes());
        os.flush();
      }

      // 返回流式响应
      return connection.getInputStream();
    } catch (Exception e) {
      return null;
    } finally {
      // 关闭连接
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  /**
   * 发送表单请求
   *
   * @param endpoint 请求节点
   * @param paramMap 请求参数map
   * @return 请求响应结果
   */
  protected StringBuilder postFormData(final String endpoint, final Map<String, Object> paramMap) {
    //链式构建请求
    try {
      HttpRequestEntity entity = new HttpRequestEntity();
      entity.setRequestAddress(config.getApiUrl() + endpoint);
      entity.setParamsMap(paramMap);
      entity.setJson(Boolean.FALSE);
      entity.setMethod(HttpMethodEnums.POST);
      entity.setContentType(AiConstant.CONTENT_TYPE_MULTIPART_VALUE);

      // 设置 http 请求头
      Map<String, String> headerMap = new HashMap<>(IntegerConsts.THREE);
      headerMap.put(HttpHeaderConsts.CONTENT_TYPE, AiConstant.CONTENT_TYPE_MULTIPART_VALUE);
      headerMap.put(HttpHeaderConsts.ACCEPT, AiConstant.CONTENT_TYPE_VALUE);
      headerMap.put(AiConstant.AUTHORIZATION, AiConstant.BEARER + config.getApiKey());
      entity.setHeaderMap(headerMap);
      // 构建请求体
      return HttpRequestUtils.httpRequest(entity);
    } catch (final GlobalRuntimeException e) {
      throw new GlobalRuntimeException("发送POST请求失败：" + e.getMessage(), e);
    }
  }

  /**
   * 支持流式返回的 POST 请求
   *
   * @param endpoint 请求地址
   * @param paramMap 请求参数
   * @param callback 流式数据回调函数
   */
  protected void postStream(final String endpoint, final Map<String, Object> paramMap, Consumer<ResponseBuilder> callback) {
    HttpURLConnection connection = null;
    try {
      // 创建连接
      String url = config.getApiUrl() + endpoint;
      connection = buildConnection(url);
      // 发送请求体
      try (OutputStream os = connection.getOutputStream()) {
        String jsonString = JsonUtils.objectToJsonString(paramMap);
        os.write(jsonString.getBytes());
        os.flush();
      }

      // 读取流式响应
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          // 调用回调函数处理每一行数据
          callback.accept(ResponseBuilder.successBuilder(line));
        }
      }
    } catch (Exception e) {
      callback.accept(ResponseBuilder.failBuilder(e.getMessage()));
    } finally {
      // 关闭连接
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  /**
   * 构建HTTP连接
   *
   * @param url 请求URL
   * @return HTTP连接对象
   * @throws IOException 构建连接时发生异常
   */
  private HttpURLConnection buildConnection(String url) throws IOException {
    URL apiUrl = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
    connection.setRequestMethod(HttpMethodEnums.POST.getValue());
    connection.setRequestProperty(HttpHeaderConsts.CONTENT_TYPE, AiConstant.CONTENT_TYPE_VALUE);
    connection.setRequestProperty(AiConstant.AUTHORIZATION, AiConstant.BEARER + config.getApiKey());
    connection.setDoOutput(true);
    // 读取5分钟
    connection.setReadTimeout(config.getReadTimeout());
    // 连接3分钟
    connection.setConnectTimeout(config.getTimeout());

    // 获取连接
    return connection;
  }
}
