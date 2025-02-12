package com.cdkjframework.datasource.mybatispro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.datasource.mybatispro.config
 * @ClassName: MybatisMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/6 17:14
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class MybatisMarkerConfiguration {
	@Bean
	public Marker mybatisMarker() {
		return new Marker();
	}

	public static class Marker {

	}
}
