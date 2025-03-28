package com.cdkjframework.minio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.config
 * @ClassName: MinioMarkerConfiguration
 * @Description: Minio标记配置
 * @Author: xiaLin
 * @Date: 2024/9/2 11:28
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class MinioMarkerConfiguration {
	@Bean
	public Marker mybatisMarker() {
		return new Marker();
	}

	public static class Marker {

	}
}
