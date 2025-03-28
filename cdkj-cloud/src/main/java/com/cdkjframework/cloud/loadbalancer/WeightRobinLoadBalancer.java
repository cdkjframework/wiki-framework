package com.cdkjframework.cloud.loadbalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.loadbalancer
 * @ClassName: WeightRobinLoadBalancer
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/8/14 13:53
 * @Version: 1.0
 */
public class WeightRobinLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	/**
	 * 提供者
	 */
	private ObjectProvider<ServiceInstanceListSupplier> provider;

	/**
	 * 服务名称
	 */
	private String serviceId;


	/**
	 * 构建函数
	 *
	 * @param provider  提供者
	 * @param serviceId 名称
	 */
	public WeightRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> provider, String serviceId) {
		this.provider = provider;
		this.serviceId = serviceId;
	}

	/**
	 * Choose the next server based on the load balancing algorithm.
	 *
	 * @param request - an input request
	 * @return - mono of response
	 */
	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		return null;
	}
}
