package com.cdkjframework.swagger.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.swagger.handler
 * @ClassName: SpringfoxHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/8/14 14:59
 * @Version: 1.0
 */
@Configuration
public class SpringfoxHandler {

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
