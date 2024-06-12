package com.cdkjframework.swagger.config;

import com.cdkjframework.swagger.SwaggerStartTrigger;
import com.fasterxml.classmate.TypeResolver;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.swagger.config
 * @ClassName: SwaggerMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:21
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SwaggerConfig.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(SwaggerMarkerConfiguration.Marker.class)
public class SwaggerAutoConfiguration {

  /**
   * 读取配置文件
   */
  private final SwaggerConfig swaggerConfig;

  /**
   * 类型解析程序
   */
  @Resource(name = "typeResolver")
  private TypeResolver resolver;

  @Bean
  public TypeResolver typeResolver() {
    return new TypeResolver();
  }

  /**
   * swagger启动触发器
   *
   * @return 返回结果
   */
  @Bean(initMethod = "start")
  @ConditionalOnMissingBean
  public SwaggerStartTrigger swaggerStartTrigger() {
    return new SwaggerStartTrigger(swaggerConfig, resolver);
  }
}
