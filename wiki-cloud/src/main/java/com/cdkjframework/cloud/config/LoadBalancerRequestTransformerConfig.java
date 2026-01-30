package com.cdkjframework.cloud.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.cloud.config
 * @ClassName: LoadBalancerRequestTransformerConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/11/5 0:11
 * @Version: 1.0
 */
@Configuration
public class LoadBalancerRequestTransformerConfig {

  /**
   * 配置LoadBalancerRequestTransformer，用于在请求发出前进行转换，例如添加请求头[6](@ref)
   */
  @Bean
  public LoadBalancerRequestTransformer requestTransformer() {
    return new LoadBalancerRequestTransformer() {
      @Override
      public HttpRequest transformRequest(HttpRequest originalRequest, ServiceInstance instance) {
        // 创建一个请求包装器，用于添加自定义头部[6](@ref)
        return new HttpRequestWrapper(originalRequest) {
          @Override
          public HttpHeaders getHeaders() {
            // 复制原始头信息
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(super.getHeaders());
            // 添加自定义头，例如实例ID[6](@ref)
            if (instance != null) {
              headers.add("X-InstanceId", instance.getInstanceId());
            }
            return headers;
          }
        };
      }
    };
  }
}
