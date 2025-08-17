package com.cdkjframework.ai.core.impl;

import com.cdkjframework.ai.core.AiConfig;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础配置类
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core.impl
 * @ClassName: BaseAiConfig
 * @Description: 基础配置类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class BaseAiConfig implements AiConfig {

  /**
   * apiKey
   */
  protected volatile String apiKey;
  /**
   * API请求地址
   */
  protected volatile String apiUrl;
  /**
   * 具体模型
   */
  protected volatile String model;
  /**
   * 动态扩展字段
   */
  protected Map<String, Object> additionalConfig = new ConcurrentHashMap<>();
  /**
   * 连接超时时间
   */
  protected volatile int timeout = 180000;
  /**
   * 读取超时时间
   */
  protected volatile int readTimeout = 300000;

  /**
   * 代理
   */
  protected volatile Proxy proxy;

  /**
   * 设置apiKey
   *
   * @param apiKey apiKey
   */
  @Override
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * 获取apiKey
   *
   * @return apiKey
   */
  @Override
  public String getApiKey() {
    return apiKey;
  }

  /**
   * 设置apiUrl
   *
   * @param apiUrl api请求地址
   */
  @Override
  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  /**
   * 获取apiUrl
   *
   * @return apiUrl
   */
  @Override
  public String getApiUrl() {
    return apiUrl;
  }

  /**
   * 设置model
   *
   * @param model model
   */
  @Override
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * 返回model
   *
   * @return model
   */
  @Override
  public String getModel() {
    return model;
  }

  /**
   * 设置动态参数
   *
   * @param key   参数字段
   * @param value 参数值
   */
  @Override
  public void putAddConfigByKey(String key, Object value) {
    additionalConfig.put(key, value);
  }

  /**
   * 获取动态参数
   *
   * @param key 参数字段
   * @return 参数值
   */
  @Override
  public Object getAddConfigByKey(String key) {
    return additionalConfig.get(key);
  }

  /**
   * 获取动态参数列表
   *
   * @return 参数列表Map
   */
  @Override
  public Map<String, Object> getAddConfigMap() {
    return additionalConfig;
  }

  /**
   * 设置连接超时时间
   *
   * @param timeout 连接超时时间
   */
  @Override
  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  /**
   * 获取连接超时时间
   *
   * @return timeout
   */
  @Override
  public int getTimeout() {
    return timeout;
  }

  /**
   * 设置读取超时时间
   *
   * @param readTimeout 连接超时时间
   */
  @Override
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  /**
   * 获取读取超时时间
   *
   * @return readTimeout 读取超时时间
   */
  @Override
  public int getReadTimeout() {
    return readTimeout;
  }

  /**
   * 写入代理
   *
   * @param proxy
   */
  @Override
  public void setProxy(Proxy proxy) {
    this.proxy = proxy;
  }

  /**
   * 获取代理
   *
   * @return Proxy 返回代理信息
   */
  @Override
  public Proxy getProxy() {
    return proxy;
  }
}
