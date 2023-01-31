package com.cdkjframework.util.network.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.constant.HttpHeaderConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.enums.HttpMethodEnums;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.GzipUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.util.http
 * @ClassName: HttpRequestUtil
 * @Description: HTTP请求
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class HttpRequestUtils {

  /**
   * 日志
   */
  private static LogUtils logUtil = LogUtils.getLogger(HttpRequestUtils.class);

  /**
   * 将返回结果返回对象
   *
   * @param httpRequestEntity 请求实体
   * @param clazz             转换对象
   * @return 返回结果
   */
  public static <T> T httpRequest(HttpRequestEntity httpRequestEntity, Class<T> clazz) throws IllegalAccessException, InstantiationException {
    T t = null;
    final String name = "java.util.ArrayList";

    //http 请求
    StringBuilder result = httpRequest(httpRequestEntity);

    if (clazz.getName().contains(name)) {
      JSONArray jsonArray = JSON.parseArray(result.toString());
      t = (T) jsonArray.toJavaList(clazz);
    } else {
      JSONObject jsonObject = JSON.parseObject(result.toString());
      t = jsonObject.toJavaObject(clazz);
    }

    //返回结果
    return t;
  }

  /**
   * http 请求
   *
   * @param httpRequestEntity 请求实体
   * @return 返回结果
   */
  public static StringBuilder httpRequest(HttpRequestEntity httpRequestEntity) {
    StringBuilder result;
    switch (httpRequestEntity.getMethod()) {
      case GET:
        result = get(httpRequestEntity);
        break;
      case FILE:
        result = postFile(httpRequestEntity);
        break;
      default:
        result = post(httpRequestEntity);
        break;
    }

    //返回结果
    return result;
  }

  /**
   * 上传文件
   *
   * @param request 请求类型
   * @return 返回结果
   */
  private static StringBuilder postFile(HttpRequestEntity request) {
    StringBuilder builder;
    try {
      String fileName = request.getName();
      if (StringUtils.isNullAndSpaceOrEmpty(fileName)) {
        throw new GlobalRuntimeException("文件名称不能为空！");
      }
      InputStream inputStream = (InputStream) request.getData();
      URL url = new URL(request.getRequestAddress());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod(request.getMethod().getValue());
      // 添加 header
      conn.setRequestProperty("Connection", "Keep-Alive");
      conn.setRequestProperty("Charset", EncodingConsts.UTF8);
      // 设置边界
      String BOUNDARY = "----------" + System.currentTimeMillis();
      conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
      Map<String, String> headerMap = request.getHeaderMap();
      if (headerMap != null) {
        Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
        for (Map.Entry<String, String> entry :
            entrySet) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }
      // 请求正文信息
      //    第一部分：
      StringBuilder sb = new StringBuilder();
      // 必须多两道线
      sb.append("--");
      sb.append(BOUNDARY);
      sb.append("\r\n");
      sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" +
          inputStream.available() + "\";filename=\"" + fileName + "\"\r\n");
      sb.append("Content-Type:application/octet-stream\r\n\r\n");

      byte[] head = sb.toString().getBytes(EncodingConsts.UTF8);
      OutputStream out = new DataOutputStream(conn.getOutputStream());
      // 输出表头
      out.write(head);
      // 文件正文部分
      // 把文件已流文件的方式 推入到url中
      DataInputStream in = new DataInputStream(inputStream);
      int bytes;
      byte[] bufferOut = new byte[IntegerConsts.BYTE_LENGTH];
      while ((bytes = in.read(bufferOut)) != IntegerConsts.MINUS_ONE) {
        out.write(bufferOut, IntegerConsts.ZERO, bytes);
      }
      in.close();
      // 结尾部分
      // 定义最后数据分隔线
      byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(EncodingConsts.UTF8);
      out.write(foot);
      out.flush();
      out.close();
      // 读取返回结果
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line = null;
      builder = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
    } catch (Exception e) {
      logUtil.error(e);
      builder = new StringBuilder();
    }
    // 返回结果
    return builder;
  }

  /**
   * POST 请求方法
   *
   * @param httpRequestEntity 请求实体
   * @return 返回结果
   */
  private static StringBuilder post(HttpRequestEntity httpRequestEntity) {
    OutputStream printWriter = null;
    BufferedReader bufferedReader = null;
    StringBuilder result = new StringBuilder();
    try {
      URL realUrl = new URL(httpRequestEntity.getRequestAddress());
      // 打开和URL之间的连接
      HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
      connection.setRequestProperty(HttpHeaderConsts.CONTENT_TYPE, httpRequestEntity.getContentType());
      // 设置通用的请求属性
      //设置 http 请求头
      setHeader(connection, httpRequestEntity);
      connection.setRequestMethod(HttpMethodEnums.POST.getValue());
      // 发送POST请求必须设置如下两行
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.connect();
      // 获取URLConnection对象对应的输出流
      printWriter = connection.getOutputStream();

      //将参数转换为 json 对象
      StringBuffer param = new StringBuffer();
      if (!CollectionUtils.isEmpty(httpRequestEntity.getObjectList())) {
        param.append(JSON.toJSONString(httpRequestEntity.getObjectList()));
      } else if (httpRequestEntity.getData() != null) {
        param.append(JSON.toJSONString(httpRequestEntity.getData()));
      }
      if (!CollectionUtils.isEmpty(httpRequestEntity.getParamsMap())) {
        Map<String, Object> params = httpRequestEntity.getParamsMap();
        if (httpRequestEntity.isJson()) {
          param.append(JSON.toJSONString(params));
        } else {
          Set<Map.Entry<String, Object>> entrySet = params.entrySet();
          for (Map.Entry entry :
              entrySet) {
            if (!param.toString().isEmpty()) {
              param.append(StringUtils.CONNECTOR);
            }
            param.append(entry.getKey()).append(StringUtils.EQUAL).append(entry.getValue());
          }
        }
      }

      //是否 gzip 加密
      if (httpRequestEntity.isCompress()) {
        String gzipParams = GzipUtils.gZip(param.toString(), httpRequestEntity.getCharset());
        printWriter.write(gzipParams.getBytes());
      } else {
        // 发送请求参数
        printWriter.write(param.toString().getBytes());
      }
      // flush输出流的缓冲
      printWriter.flush();
      // 定义BufferedReader输入流来读取URL的响应
      bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      logUtil.error(e.getMessage());
    }
    //使用finally块来关闭输出流、输入流
    finally {
      try {
        if (printWriter != null) {
          printWriter.close();
        }
      } catch (IOException e) {
        logUtil.error(e.getMessage());
      }
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        logUtil.error(e.getMessage());
      }
    }
    return result;
  }

  /**
   * GET 请求方法
   *
   * @param httpRequestEntity 请求实体
   * @return 返回结果
   */
  private static StringBuilder get(HttpRequestEntity httpRequestEntity) {
    //返回结果
    StringBuilder result = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
      //请求地址
      StringBuilder urlString = new StringBuilder(httpRequestEntity.getRequestAddress());
      //获取参数
      Map<String, Object> paramsMap = httpRequestEntity.getParamsMap();
      if (paramsMap.size() > IntegerConsts.ZERO) {
        urlString.append(StringUtils.QUESTION_MARK);
      }
      for (Map.Entry<String, Object> entry :
          paramsMap.entrySet()) {
        if (urlString.toString().contains(StringUtils.EQUAL)) {
          urlString.append(StringUtils.CONNECTOR);
        }
        urlString.append(entry.getKey()).append(StringUtils.EQUAL).append(entry.getValue());
      }
      URL realUrl = new URL(urlString.toString());
      // 打开和URL之间的连接
      HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
      //设置 http 请求头
      setHeader(connection, httpRequestEntity);
      // 建立实际的连接
      connection.connect();
      // 定义 BufferedReader输入流来读取URL的响应
      bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      logUtil.error(e.getMessage());
    }
    // 使用finally块来关闭输入流
    finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (Exception e) {
        logUtil.error(e.getMessage());
      }
    }
    //返回结果
    return result;
  }

  /**
   * 设置 http 请求头
   *
   * @param connection        URL连接
   * @param httpRequestEntity 请求头参数
   */
  public static void setHeader(HttpURLConnection connection, HttpRequestEntity httpRequestEntity) {
    Map<String, String> mapHeader = httpRequestEntity.getHeaderMap();
    Set<Map.Entry<String, String>> entrySet = mapHeader.entrySet();
    // 设置通用的请求属性
    for (Map.Entry<String, String> entry :
        entrySet) {
      connection.setRequestProperty(entry.getKey(), entry.getValue());
    }

    //验证是否开启数据压缩
    if (httpRequestEntity.isCompress()) {
      connection.setRequestProperty(HttpHeaderConsts.CONTENT_ENCODING, "gzip");
    }
  }
}
