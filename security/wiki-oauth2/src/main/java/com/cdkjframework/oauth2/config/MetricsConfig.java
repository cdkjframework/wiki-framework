package com.cdkjframework.oauth2.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 指标配置
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.config
 * @ClassName: MetricsConfig
 * @Description: 指标配置
 * @Author: xiaLin
 * @Date: 2025/9/1 22:22
 * @Version: 1.0
 */
@Configuration
public class MetricsConfig {

  /**
   * 可选：为所有 MeterRegistry 添加公共标签（不会引入循环依赖）
   */
  @Bean
  public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
    return registry -> registry.config().commonTags("application", "example-oauth2");
  }

  /**
   * 令牌请求计数器
   *
   * @param registry 度量注册表
   * @return 计数器
   */
  @Bean
  public Counter tokenRequestCounter(MeterRegistry registry) {
    return Counter.builder("token_requests_total").description("Token endpoint requests").register(registry);
  }

  /**
   * 令牌颁发计数器
   *
   * @param registry 度量注册表
   * @return 计数器
   */
  @Bean
  public Counter tokenIssuedCounter(MeterRegistry registry) {
    return Counter.builder("token_issued_total").description("Access tokens issued").register(registry);
  }

  /**
   * 令牌颁发延迟计时器
   *
   * @param registry 度量注册表
   * @return 计时器
   */
  @Bean
  public Timer tokenIssueTimer(MeterRegistry registry) {
    return Timer.builder("token_latency_seconds").description("Token issue latency").publishPercentiles(0.5, 0.95).register(registry);
  }
}