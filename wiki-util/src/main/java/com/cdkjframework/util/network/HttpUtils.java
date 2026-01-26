package com.cdkjframework.util.network;

import com.cdkjframework.enums.HttpMethodEnums;
import com.cdkjframework.util.tool.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个简易的 HTTP/HTTPS 请求工具类，支持 GET、POST、文件上传。
 * 所有请求入口方法均为静态方法，使用链式调用。
 * <p>
 * 调用示例：
 * <p>
 * String res = HttpUtils.get()
 * .url("https://api.xxx.com/user")
 * .addHeader("token", "abc")
 * .addParam("id", "1")
 * .build()
 * .execute();
 *
 * @ProjectName: common
 * @Package: com.cdkjframework.util.network
 * @ClassName: HttpUtils
 * @Description: http工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class HttpUtils {

  /**
   * 请求地址
   */
  private String url;

  /**
   * 请求方法：GET / POST
   */
  private String method;

  /**
   * 请求头
   */
  private Map<String, String> headers = new HashMap<>();

  /**
   * 请求参数
   */
  private Map<String, String> params = new HashMap<>();

  /**
   * 上传文件
   */
  private File uploadFile;

  /**
   * 上传文件字段名
   */
  private String uploadFieldName = "file";

  /**
   * 是否为上传请求
   */
  private boolean isUpload = false;

  /**
   * 构造方法（请求方法由 get/post/upload 决定）
   *
   * @param method 请求方法
   */
  private HttpUtils(String method) {
    this.method = method;
  }

  // -------------------------------------------------------------------------
  //  静态入口方法
  // -------------------------------------------------------------------------

  /**
   * 创建 GET 请求
   *
   * @return HttpUtils 链式构建对象
   */
  public static HttpUtils get() {
    return new HttpUtils(HttpMethodEnums.GET.getValue());
  }

  /**
   * 创建 POST 请求
   *
   * @return HttpUtils 链式构建对象
   */
  public static HttpUtils post() {
    return new HttpUtils(HttpMethodEnums.POST.getValue());
  }

  /**
   * 创建文件上传请求（本质为 POST multipart/form-data）
   *
   * @return HttpUtils 链式构建对象
   */
  public static HttpUtils upload() {
    HttpUtils h = new HttpUtils(HttpMethodEnums.POST.getValue());
    h.isUpload = true;
    return h;
  }

  // -------------------------------------------------------------------------
  //  链式构建区
  // -------------------------------------------------------------------------

  /**
   * 设置请求 URL
   *
   * @param url 请求地址
   * @return 当前 HttpUtils（链式调用）
   */
  public HttpUtils url(String url) {
    this.url = url;
    return this;
  }

  /**
   * 添加请求头
   *
   * @param key   header 名
   * @param value header 值
   * @return 当前 HttpUtils（链式调用）
   */
  public HttpUtils addHeader(String key, String value) {
    this.headers.put(key, value);
    return this;
  }

  /**
   * 添加请求参数
   *
   * @param key   参数名
   * @param value 参数值
   * @return 当前 HttpUtils（链式调用）
   */
  public HttpUtils addParam(String key, String value) {
    this.params.put(key, value);
    return this;
  }

  /**
   * 设置上传文件
   *
   * @param file 文件对象
   * @return 当前 HttpUtils（链式调用）
   */
  public HttpUtils file(File file) {
    this.uploadFile = file;
    return this;
  }

  /**
   * 设置上传文件字段名（默认 file）
   *
   * @param fieldName 字段名
   * @return 当前 HttpUtils（链式调用）
   */
  public HttpUtils fileField(String fieldName) {
    this.uploadFieldName = fieldName;
    return this;
  }

  /**
   * 最终构建 HttpUtils 对象
   *
   * @return HttpUtils 本身
   */
  public HttpUtils build() {
    return this;
  }

  // -------------------------------------------------------------------------
  //  执行区
  // -------------------------------------------------------------------------

  /**
   * 执行 HTTP 请求（自动根据 GET、POST、上传模式选择）
   *
   * @return 请求响应字符串
   */
  public String execute() {
    try {
      if (isUpload) {
        return doUpload();
      } else if (HttpMethodEnums.GET.getValue().equalsIgnoreCase(method)) {
        return doGet();
      } else if (HttpMethodEnums.POST.getValue().equalsIgnoreCase(method)) {
        return doPost();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  // -------------------------------------------------------------------------
  //  GET 请求
  // -------------------------------------------------------------------------

  /**
   * 执行 GET 请求
   *
   * @return 响应内容
   * @throws Exception 网络异常
   */
  private String doGet() throws Exception {
    StringBuilder paramStr = new StringBuilder(url);
    if (!params.isEmpty()) {
      paramStr.append(StringUtils.QUESTION_MARK);
      for (Map.Entry<String, String> entry : params.entrySet()) {
        if (!paramStr.toString().endsWith(StringUtils.QUESTION_MARK)) {
          paramStr.append(StringUtils.AMPERSAND);
        }
        paramStr.append(entry.getKey())
            .append(StringUtils.EQUALS)
            .append(entry.getValue());
      }
    }

    URL urlObj = new URL(paramStr.toString());
    HttpURLConnection conn = openConnection(urlObj);
    conn.setRequestMethod(HttpMethodEnums.GET.getValue());
    setHeaders(conn);

    return readResponse(conn);
  }

  // -------------------------------------------------------------------------
  //  POST 请求
  // -------------------------------------------------------------------------

  /**
   * 执行 POST 请求（默认为 application/x-www-form-urlencoded）
   *
   * @return 响应内容
   * @throws Exception 异常
   */
  private String doPost() throws Exception {
    URL urlObj = new URL(url);
    HttpURLConnection conn = openConnection(urlObj);
    conn.setRequestMethod(HttpMethodEnums.POST.getValue());
    conn.setDoOutput(true);
    setHeaders(conn);

    StringBuilder paramStr = new StringBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (!paramStr.isEmpty()) {
        paramStr.append(StringUtils.AMPERSAND);
      }
      paramStr.append(entry.getKey())
          .append(StringUtils.EQUALS)
          .append(entry.getValue());
    }

    try (OutputStream os = conn.getOutputStream()) {
      os.write(paramStr.toString().getBytes());
    }

    return readResponse(conn);
  }

  // -------------------------------------------------------------------------
  //  文件上传 multipart/form-data
  // -------------------------------------------------------------------------

  /**
   * 执行文件上传请求
   *
   * @return 响应数据
   * @throws Exception 网络异常
   */
  private String doUpload() throws Exception {
    String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
    URL urlObj = new URL(url);
    HttpURLConnection conn = openConnection(urlObj);

    conn.setRequestMethod(HttpMethodEnums.POST.getValue());
    conn.setDoOutput(true);
    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    setHeaders(conn);

    try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {

      // 普通参数
      for (Map.Entry<String, String> entry : params.entrySet()) {
        out.writeBytes("--" + boundary + "\r\n");
        out.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
        out.writeBytes(entry.getValue() + "\r\n");
      }

      // 文件部分
      out.writeBytes("--" + boundary + "\r\n");
      out.writeBytes("Content-Disposition: form-data; name=\"" + uploadFieldName + "\"; filename=\"" + uploadFile.getName() + "\"\r\n");
      out.writeBytes("Content-Type: application/octet-stream\r\n\r\n");

      try (FileInputStream fis = new FileInputStream(uploadFile)) {
        byte[] buffer = new byte[4096];
        int len;
        while ((len = fis.read(buffer)) != -1) {
          out.write(buffer, 0, len);
        }
      }
      out.writeBytes("\r\n--" + boundary + "--\r\n");
    }

    return readResponse(conn);
  }

  // -------------------------------------------------------------------------
  //  工具方法
  // -------------------------------------------------------------------------

  /**
   * 创建 HTTP 或 HTTPS 连接（自动忽略证书）
   *
   * @param urlObj URL 对象
   * @return HttpURLConnection
   * @throws Exception 错误
   */
  private HttpURLConnection openConnection(URL urlObj) throws Exception {
    if ("https".equalsIgnoreCase(urlObj.getProtocol())) {
      trustAllHosts();
    }
    return (HttpURLConnection) urlObj.openConnection();
  }

  /**
   * 设置请求头
   *
   * @param conn HttpURLConnection
   */
  private void setHeaders(HttpURLConnection conn) {
    for (Map.Entry<String, String> h : headers.entrySet()) {
      conn.setRequestProperty(h.getKey(), h.getValue());
    }
  }

  /**
   * 读取响应内容
   *
   * @param conn HttpURLConnection
   * @return 响应字符串
   * @throws Exception IO 异常
   */
  private String readResponse(HttpURLConnection conn) throws Exception {
    InputStream is = conn.getResponseCode() >= 400
        ? conn.getErrorStream()
        : conn.getInputStream();

    if (is == null) {
      return StringUtils.EMPTY;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      return sb.toString();
    }
  }

  /**
   * 忽略 HTTPS 证书检查（用于自签名证书）
   */
  private static void trustAllHosts() {
    try {
      TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return null;
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
          }
      };

      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    } catch (Exception ignored) {
    }
  }
}
