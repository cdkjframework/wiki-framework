package com.cdkjframework.util.network.request;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.ArrayUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;

/**
 * http 全局配置
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.network.request
 * @ClassName: HttpGlobalConfig
 * @Description: http 全局配置
 * @Author: xiaLin
 * @Version: 1.0
 */
public class HttpGlobalConfig implements Serializable {

  /**
   * UID
   */
  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 单位默认毫秒
   * 若设置为 -1 将永不超时，0时超时被解释为无限超时。
   * 底层调用：{@link HttpURLConnection#setReadTimeout(int)} 同时设置: 读取超时
   * 底层调用：{@link HttpURLConnection#setConnectTimeout(int)} 同时设置: 连接超时
   */
  @Getter
  private static int timeout = 60000;
  /**
   * 是否允许PATCH请求
   */
  @Getter
  private static boolean isAllowPatch = Boolean.FALSE;

  /**
   * 边界符
   */
  @Getter
  private static String boundary = "--------------------Wiki_" + GeneratedValueUtils.getRandom(IntegerConsts.SIXTEEN);

  /**
   * 最大重定向次数
   */
  @Getter
  private static int maxRedirectCount = IntegerConsts.ZERO;

  /**
   * 是否忽略EOF错误
   */
  @Getter
  private static boolean ignoreEofError = Boolean.TRUE;

  /**
   * 解码Url
   */
  @Getter
  private static boolean decodeUrl = Boolean.FALSE;
  /**
   * 是否信任任何主机
   */
  @Getter
  private static boolean trustAnyHost = Boolean.TRUE;

  /**
   * 设置超时
   *
   * @param customTimeout 自定义超时
   */
  synchronized public static void setTimeout(int customTimeout) {
    timeout = customTimeout;
  }

  /**
   * 设置边界符
   *
   * @param customBoundary 自定义边界符
   */
  synchronized public static void setBoundary(String customBoundary) {
    boundary = customBoundary;
  }

  /**
   * 设置最大重定向次数
   *
   * @param customMaxRedirectCount 最大重定向次数
   */
  synchronized public static void setMaxRedirectCount(int customMaxRedirectCount) {
    maxRedirectCount = customMaxRedirectCount;
  }

  /**
   * 设置是否忽略EOF错误
   *
   * @param customIgnoreEofError 是否忽略EOF错误
   */
  synchronized public static void setIgnoreEofError(boolean customIgnoreEofError) {
    ignoreEofError = customIgnoreEofError;
  }

  /**
   * 设置解码Url
   *
   * @param customDecodeUrl 是否解码Url
   */
  synchronized public static void setDecodeUrl(boolean customDecodeUrl) {
    decodeUrl = customDecodeUrl;
  }

  /**
   * 允许PATCH请求
   */
  synchronized public static void allowPatch() {
    if (isAllowPatch) {
      return;
    }
    final Field methodsField = ReflectionUtils.getDeclaredField(HttpURLConnection.class, "methods");
    if (null == methodsField) {
      throw new GlobalRuntimeException("Java版本中没有静态字段[方法]: [{}]", System.getProperty("java.version"));
    }

    // 去除final修饰
    ReflectionUtils.removeFinalModify(methodsField);
    final String[] methods = {
        "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"
    };
    ReflectionUtils.setFieldValue(null, methodsField, methods);

    // 检查注入是否成功
    final Object staticFieldValue = ReflectionUtils.getStaticFieldValue(methodsField);
    if (!ArrayUtils.equals(methods, staticFieldValue)) {
      throw new GlobalRuntimeException("向字段[methods]注入值失败！");
    }

    isAllowPatch = true;
  }
}
