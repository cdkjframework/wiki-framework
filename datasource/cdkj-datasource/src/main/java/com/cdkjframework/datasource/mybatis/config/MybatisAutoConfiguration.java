package com.cdkjframework.datasource.mybatis.config;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.mybatis.connectivity.MybatisConfiguration;
import com.cdkjframework.datasource.mybatis.connectivity.MybatisDruidDbConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.datasource.mybatis.config
 * @ClassName: MybatisAutoConfiguration
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
        MybatisConfig.class,
        DataSourceConfig.class
})
@ImportAutoConfiguration({MybatisConfiguration.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(MybatisMarkerConfiguration.Marker.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MybatisAutoConfiguration {

  /**
   * 配置信息
   */
  private final MybatisConfig mybatisConfig;

  /**
   * 基础配置
   */
  private final DataSourceConfig dataSourceConfig;
  /**
   * 自定义配置
   */
  private final CustomConfig customConfig;

  /**
   * mybatis 启动触发器
   *
   * @return 返回结果
   */
  @Bean(initMethod = "mybatisDataSource")
  @ConditionalOnMissingBean
  public MybatisDruidDbConfiguration mybatisDruidDbStartTrigger() {
    return new MybatisDruidDbConfiguration(mybatisConfig, dataSourceConfig, customConfig);
  }
}
