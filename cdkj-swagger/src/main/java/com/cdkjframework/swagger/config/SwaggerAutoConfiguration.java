package com.cdkjframework.swagger.config;

import com.cdkjframework.swagger.SwaggerStartTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

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
  private final TypeResolver resolver;

	@Bean(initMethod = "openAPI")
	public SwaggerStartTrigger swaggerOpenApi() {
		return new SwaggerStartTrigger(swaggerConfig, resolver);
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

	/**
	 * springfox处理程序提供程序Bean后处理器
	 *
	 * @return 返回结果
	 */
	@Bean
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
		return new BeanPostProcessor() {

			/**
			 * 初始化后的后处理
			 * @param bean the new bean instance
			 * @param beanName the name of the bean
			 * @return
			 * @throws BeansException
			 */
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			/**
			 * 自定义Spring fox处理程序映射
			 * @param mappings 映射
			 * @param <T> 类型
			 */
			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
				List<T> copy = mappings.stream()
						.filter(mapping -> mapping.getPatternParser() == null)
						.collect(Collectors.toList());
				mappings.clear();
				mappings.addAll(copy);
			}

			/**
			 * 获取处理程序映射
			 * @param bean 创建bean
			 * @return 返回结果
			 */
			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
				try {
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}
}
