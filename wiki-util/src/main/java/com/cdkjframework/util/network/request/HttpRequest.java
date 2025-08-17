//package com.cdkjframework.util.network.request;
//
//import com.cdkjframework.enums.HttpMethodEnums;
//import com.cdkjframework.util.tool.CollectUtils;
//import org.springframework.stereotype.Component;
//
//import java.net.HttpURLConnection;
//import java.net.URLStreamHandler;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @ProjectName: wiki-framework
// * @Package: com.cdkjframework.util.network.http
// * @ClassName: HttpRequest
// * @Description: http请求
// * @Author: xiaLin
// * @Version: 1.0
// */
//@Component
//public class HttpRequest {
//
//  /**
//   * 请求方式
//   */
//  private HttpMethodEnums method = HttpMethodEnums.GET;
//
//  /**
//   * 请求配置
//   */
//  private HttpConfig config = HttpConfig.create();
//
//  /**
//   * 存储头信息
//   */
//  protected Map<String, List<String>> headers = new HashMap<>();
//
//  /**
//   * 请求地址
//   */
//  private String url;
//
//  /**
//   * 编码
//   */
//  private Charset charset;
//
//  /**
//   * 连接对象
//   */
//  private HttpURLConnection httpConnection;
//
//  /**
//   * 存储表单数据
//   */
//  private Map<String, Object> form;
//  /**
//   * Cookie
//   */
//  private String cookie;
//  /**
//   * 是否为Multipart表单
//   */
//  private boolean isMultiPart;
//  /**
//   * 是否是REST请求模式
//   */
//  private boolean isRest;
//  /**
//   * 重定向次数计数器，内部使用
//   */
//  private int redirectCount;
//  /**
//   * 固定长度，用于设置HttpURLConnection.setFixedLengthStreamingMode，默认为0，表示使用默认值，默认值由HttpURLConnection内部决定，通常为0
//   */
//  private long fixedContentLength;
//
//  /**
//   * 构造
//   *
//   * @param url     请求地址
//   * @param charset 编码
//   */
//  public HttpRequest(String url, Charset charset) {
//    assert url != null;
//    this.url = url;
//    // 给定默认URL编码
//    this.charset = charset;
//  }
//
//  /**
//   * GET 请求
//   *
//   * @param url 请求地址
//   * @return 返回 HttpRequest 对象
//   */
//  public static HttpRequest get(String url) {
//    return of(url).method(HttpMethodEnums.GET);
//  }
//
//  /**
//   * GET 请求
//   *
//   * @param url 请求地址
//   * @return 返回 HttpRequest 对象
//   */
//  public static HttpRequest post(String url) {
//    return of(url).method(HttpMethodEnums.POST);
//  }
//
//  /**
//   * 构建 http 请求
//   *
//   * @param url 请求地址
//   * @return 返回 HttpRequest 对象
//   */
//  public static HttpRequest of(String url) {
//    return of(url, HttpGlobalConfig.isDecodeUrl() ? StandardCharsets.UTF_8 : null);
//  }
//
//  /**
//   * 构建 http 请求
//   *
//   * @param url     请求地址
//   * @param charset 编码
//   * @return 返回 HttpRequest 对象
//   */
//  public static HttpRequest of(String url, Charset charset) {
//    return new HttpRequest(url, charset);
//  }
//
//  /**
//   * 设置一个header<br>
//   * 覆盖模式，则替换之前的值
//   *
//   * @param name  Header名
//   * @param value Header值
//   * @return T 本身
//   */
//  public HttpRequest header(String name, String value) {
//    return header(name, value, true);
//  }
//
//  /**
//   * 设置请求体
//   *
//   * @param body 请求体
//   * @return 返回 HttpRequest 对象
//   */
////  public HttpRequest body(String body) {
////    return this.body(body, null);
////  }
//
//  /**
//   * 设置请求方法
//   *
//   * @param method HTTP方法
//   * @return 返回 HttpRequest 对象
//   */
//  public HttpRequest method(HttpMethodEnums method) {
//    this.method = method;
//    return this;
//  }
//
//  /**
//   * 设置一个header<br>
//   * 如果覆盖模式，则替换之前的值，否则加入到值列表中
//   *
//   * @param name       Header名
//   * @param value      Header值
//   * @param isOverride 是否覆盖已有值
//   * @return 返回 HttpRequest 对象
//   */
//  public HttpRequest header(String name, String value, boolean isOverride) {
//    if (null != name && null != value) {
//      final List<String> values = headers.get(name.trim());
//      if (isOverride || CollectUtils.isEmpty(values)) {
//        final ArrayList<String> valueList = new ArrayList<>();
//        valueList.add(value);
//        headers.put(name.trim(), valueList);
//      } else {
//        values.add(value.trim());
//      }
//    }
//    return this;
//  }
//}
