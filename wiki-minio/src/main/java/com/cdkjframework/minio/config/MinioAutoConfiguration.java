package com.cdkjframework.minio.config;

import com.cdkjframework.minio.connectivity.MinioConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.config
 * @ClassName: MinioAutoConfiguration
 * @Description: Minio自动配置
 * @Author: xiaLin
 * @Date: 2024/9/2 11:29
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
		MinioProperties.class
})
@ImportAutoConfiguration(value = {
		MinioProperties.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(MinioMarkerConfiguration.Marker.class)
public class MinioAutoConfiguration {

	/**
	 * 配置信息
	 */
	private final MinioProperties minioProperties;

	/**
	 * minio配置
	 *
	 * @return 返回配置信息
	 */
	@Bean(initMethod = "start")
	public MinioConfiguration minioConfiguration() {
		return new MinioConfiguration(minioProperties);
	}
}
