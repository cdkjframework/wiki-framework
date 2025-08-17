package com.cdkjframework.ai.core;

import java.net.Proxy;
import java.util.Map;

/**
 * AI配置类
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core
 * @ClassName: AiConfig
 * @Description: AI配置类
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface AiConfig {
    /**
     * 获取模型（厂商）名称
     *
     * @return 返回模型（厂商）名称
     */
    default String getModelName() {
        return this.getClass().getSimpleName();
    }


    /**
     * 设置apiKey
     *
     * @param apiKey apiKey
     */
    void setApiKey(String apiKey);

    /**
     * 获取apiKey
     *
     * @return apiKey
     */
    String getApiKey();

    /**
     * 设置apiUrl
     *
     * @param apiUrl api请求地址
     */
    void setApiUrl(String apiUrl);

    /**
     * 获取apiUrl
     *
     * @return apiUrl
     */
    String getApiUrl();

    /**
     * 设置model
     *
     * @param model model
     */
    void setModel(String model);

    /**
     * 返回model
     *
     * @return model
     */
    String getModel();

    /**
     * 设置动态参数
     *
     * @param key   参数字段
     * @param value 参数值
     */
    void putAddConfigByKey(String key, Object value);

    /**
     * 获取动态参数
     *
     * @param key 参数字段
     * @return 参数值
     */
    Object getAddConfigByKey(String key);

    /**
     * 获取动态参数列表
     *
     * @return 参数列表Map
     */
    Map<String, Object> getAddConfigMap();

    /**
     * 设置连接超时时间
     *
     * @param timeout 连接超时时间
     */
    void setTimeout(int timeout);

    /**
     * 获取连接超时时间
     *
     * @return timeout
     */
    int getTimeout();

    /**
     * 设置读取超时时间
     *
     * @param readTimeout 连接超时时间
     */
    void setReadTimeout(int readTimeout);

    /**
     * 获取读取超时时间
     *
     * @return readTimeout 读取超时时间
     */
    int getReadTimeout();

    /**
     * 写入代理
     */
    void setProxy(Proxy proxy);

    /**
     * 获取代理
     *
     * @return Proxy 返回代理信息
     */
    Proxy getProxy();
}
