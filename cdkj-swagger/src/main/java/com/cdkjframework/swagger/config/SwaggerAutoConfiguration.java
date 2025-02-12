package com.cdkjframework.swagger.config;

import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.swagger.SwaggerApiInfoEntity;
import com.cdkjframework.swagger.SwaggerStartTrigger;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
   * 注册分组
   *
   * @return 返回分组
   */
  @Bean
  public void groupedOpenApiBeans() {
    // 注意这里返回 void，Bean 方法用于注册 Bean 定义
    if (CollectUtils.isEmpty(swaggerConfig.getGroups())) {
      return;
    }
    List<SwaggerConfig.SwaggerGroupEntity> groups = swaggerConfig.getGroups();
    groups.forEach((apiInfo) -> {
      GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
      beanDefinition.setBeanClass(GroupedOpenApi.class);
      // Make it autowire candidate
      beanDefinition.setAutowireCandidate(true);
      // 使用 GroupedOpenApi.builder() 工厂方法
      beanDefinition.setFactoryMethodName("builder");
      beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
      // 分组名称
      beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(apiInfo.getGroupName());

      GroupedOpenApi.Builder builder = GroupedOpenApi.builder().group(apiInfo.getGroupName());
      // 配置分组规则 (packagesToScan, pathsToMatch, tagsToMatch 等)
      if (CollectUtils.isNotEmpty(apiInfo.getPackagesToScan())) {
        builder.packagesToScan(apiInfo.getPackagesToScan().toArray(new String[0]));
      }
      if (CollectUtils.isNotEmpty(apiInfo.getPathsToMatch())) {
        builder.pathsToMatch(apiInfo.getPathsToMatch().toArray(new String[0]));
      }
      if (CollectUtils.isNotEmpty(apiInfo.getTagsToMatch())) {
        builder.headersToMatch(apiInfo.getTagsToMatch().toArray(new String[0]));
      }

      beanDefinition.getPropertyValues().addPropertyValue("configuredOpenApi", builder.build());
      // 注册 Bean 定义
      BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) Application
          .applicationContext.getBeanFactory();
      beanFactory.registerBeanDefinition(apiInfo.getBeanName(), beanDefinition);
      Map<String, GroupedOpenApi> beans = Application.applicationContext.getBeansOfType(GroupedOpenApi.class);
      beans.forEach((beanName, bean) -> {
        System.out.println(beanName);
      });
    });
  }

  @ConditionalOnMissingBean
  @Bean(initMethod = "openApi")
  public SwaggerStartTrigger swaggerOpenApi() {
    return new SwaggerStartTrigger(swaggerConfig);
  }
}
