package com.cdkjframework.cloud.config;

import com.cdkjframework.constant.IntegerConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.config
 * @ClassName: CloudConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/8/14 13:12
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cloud")
public class CloudConfig {

	/**
	 * 连接超时（毫秒）
	 */
	private Integer connectTimeout = IntegerConsts.ONE_THOUSAND * IntegerConsts.SEVENTY;

	/**
	 * 读超时（毫秒）
	 */
	private Integer readTimeout = IntegerConsts.ONE_THOUSAND * IntegerConsts.SEVENTY;

	/**
	 * 周期
	 */
	private long period = IntegerConsts.ONE_HUNDRED;

	/**
	 * 最大周期
	 */
	private long maxPeriod = IntegerConsts.TEN;

	/**
	 * 最大尝试次数
	 */
	private int maxAttempts = IntegerConsts.THREE;

	/**
	 * 是否跟随重定向
	 */
	private boolean followRedirects = Boolean.TRUE;

	/**
	 * 负载均衡类型
	 * 0 轮询（Round Robin）：按顺序循环将请求分发给每个服务实例
	 * 1 随机（Random）：随机选择一个服务实例来处理请求
	 * 2 最少活跃调用（Least Active）：选择当前连接数或活跃请求数最少的服务实例
	 * 3 加权轮询（Weight Round Robin）：根据服务实例的权重来分配请求，权重越高的实例接收的请求越多
	 */
	private Integer loadBalancer = IntegerConsts.ZERO;
}
