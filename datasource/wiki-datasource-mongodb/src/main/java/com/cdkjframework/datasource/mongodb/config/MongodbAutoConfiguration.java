package com.cdkjframework.datasource.mongodb.config;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.datasource.mongodb.connectivity.MongoConfiguration;
import com.cdkjframework.datasource.mongodb.number.MongoNumberUtils;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.datasource.mongodb.repository.impl.MongoRepository;
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
 * @ProjectName: wiki-framework
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
@EnableConfigurationProperties({MongoConfig.class, CustomConfig.class})
@ImportAutoConfiguration(value = {MongoConfiguration.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(MongoMarkerConfiguration.Marker.class)
public class MongodbAutoConfiguration {

  /**
   * mongodb 启动触发器
   *
   * @return 返回结果
   */
  @Bean
  @ConditionalOnMissingBean
  public IMongoRepository repositoryStartTrigger() {
    return new MongoRepository();
  }

  /**
   * MongoNumberUtils 启动触发器
   *
   * @return 返回结果
   */
  @Bean(initMethod = "start")
  @ConditionalOnMissingBean
  public MongoNumberUtils numberStartTrigger() {
    return new MongoNumberUtils(repositoryStartTrigger());
  }
}
