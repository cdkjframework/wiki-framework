package com.cdkjframework.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.config
 * @ClassName: MinioProperties
 * @Description: mini配置
 * @Author: xiaLin
 * @Date: 2024/9/2 11:28
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.minio")
public class MinioProperties {

	/**
	 * 存储端点
	 */
	private String endpoint;

	/**
	 * 访问密钥
	 */
	private String accessKey;

	/**
	 * 密钥
	 */
	private String secretKey;

	/**
	 * 存储桶名称
	 */
	private String bucketName;

	/**
	 * 分片对象过期时间 单位（天）
	 */
	private Integer expiry;

	/**
	 * 断点续传有效时间，在redis存储任务的时间 单位（天）
	 */
	private Integer breakpointTime;
}
