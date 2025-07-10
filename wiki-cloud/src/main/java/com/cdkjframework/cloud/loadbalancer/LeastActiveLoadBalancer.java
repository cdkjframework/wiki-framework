package com.cdkjframework.cloud.loadbalancer;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

/**
 * 最低活动负载平衡器
 *
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.loadbalancer
 * @ClassName: LeastActiveLoadBalancer
 * @Description: 最低活动负载平衡器
 * @Author: xiaLin
 * @Date: 2024/8/14 13:41
 * @Version: 1.0
 */
public class LeastActiveLoadBalancer implements ReactorServiceInstanceLoadBalancer {

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
  public LeastActiveLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> provider, String serviceId) {
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
    // 获取服务实例列表
    List<ServiceInstance> instances = provider.getIfAvailable().get(request).blockFirst();

    // 根据活跃调用数选择服务实例
    ServiceInstance selectedInstance = instances.stream()
        .min(Comparator.comparingInt(this::getActivityCount))
        .orElse(null);

    // 构造响应并返回
    if (selectedInstance != null) {
      return Mono.just(new DefaultResponse(selectedInstance));
    } else {
      return Mono.empty(); // 或返回其他错误响应
    }
  }

  /**
   * 假设有一个方法可以获取服务实例的活跃度
   *
   * @param instance 实例
   * @return 返回结果数量
   */
  private int getActivityCount(ServiceInstance instance) {
    // 这里应该是一个实际的查询逻辑，可能是调用监控API、查询缓存等
    // 返回服务实例的当前活跃度
//		String url = instance.getHost() + StringUtils.COLON + instance.getPort() + instance.getUri().getPath();
    return IntegerConsts.ONE;
  }
}
