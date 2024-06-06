package com.cdkjframework.cloud.loadbalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.cloud.loadBalancer
 * @ClassName: RoundLoadBalancerConfiguration
 * @Author: xiaLin
 * @Description: 自定义圆形负载平衡器配置
 * @Date: 2024/6/4 21:52
 * @Version: 1.0
 */
@Configuration
public class RoundLoadBalancerConfiguration {

  /**
   * 圆形负载均衡
   *
   * @param environment               环境
   * @param loadBalancerClientFactory 负载均衡
   * @return 服务实例
   */

  @Bean
  public ReactorLoadBalancer<ServiceInstance> roundRobinLoadBalancer(Environment environment,
                                                                     LoadBalancerClientFactory loadBalancerClientFactory) {
    // 属性名称
    String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
    // 提供程序
    ObjectProvider<ServiceInstanceListSupplier> provider = loadBalancerClientFactory
        .getLazyProvider(name, ServiceInstanceListSupplier.class);
    return new RoundRobinLoadBalancer(provider, name);
  }
}
