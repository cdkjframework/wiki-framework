package com.cdkjframework.cloud.loadbalancer;

import com.cdkjframework.cloud.config.CloudConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.loadbalancer
 * @ClassName: LoadBalancerConfiguration
 * @Description: 负载均衡配置
 * @Author: xiaLin
 * @Date: 2024/8/14 13:12
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class LoadBalancerConfiguration {

	/**
	 * 配置
	 */
	private final CloudConfig config;

	/**
	 * 随机负载均衡
	 *
	 * @param environment               环境
	 * @param loadBalancerClientFactory 负载均衡
	 * @return 服务实例
	 */
	@Bean
	public ReactorLoadBalancer<ServiceInstance> loadBalancer(Environment environment,
																													 LoadBalancerClientFactory loadBalancerClientFactory) {
		// 属性名称
		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

		// 提供程序
		ObjectProvider<ServiceInstanceListSupplier> provider = loadBalancerClientFactory
				.getLazyProvider(name, ServiceInstanceListSupplier.class);
		switch (config.getLoadBalancer()) {
			case 1:
				return new RandomLoadBalancer(provider, name);
			case 2:
				return new LeastActiveLoadBalancer(provider, name);
			case 3:
				return new WeightRobinLoadBalancer(provider, name);
			default:
				return new RoundRobinLoadBalancer(provider, name);
		}
	}
}
