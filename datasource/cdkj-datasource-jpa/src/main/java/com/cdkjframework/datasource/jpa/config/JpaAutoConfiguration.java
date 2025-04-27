package com.cdkjframework.datasource.jpa.config;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.jpa.connectivity.JpaConfiguration;
import com.cdkjframework.datasource.jpa.connectivity.JpaDruidDbConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.datasource.jpa.config
 * @ClassName: JpaAutoConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/5 17:29
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
    CustomConfig.class,
    JpaConfig.class,
    DataSourceConfig.class
})
@ImportAutoConfiguration(value = {JpaDruidDbConfiguration.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(JpaMarkerConfiguration.Marker.class)
public class JpaAutoConfiguration {

  /**
   * 配置信息
   */
  private final JpaConfig jpaConfig;

  /**
   * mybatis 启动触发器
   *
   * @return 返回结果
   */
  @Bean
  @ConditionalOnMissingBean(JpaDruidDbConfiguration.class)
  public JpaConfiguration mybatisDruidDbStartTrigger() {
    return new JpaConfiguration(jpaConfig);
  }
}
