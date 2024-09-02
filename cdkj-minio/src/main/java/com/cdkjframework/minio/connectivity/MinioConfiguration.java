package com.cdkjframework.minio.connectivity;

import com.cdkjframework.minio.MinioUtils;
import com.cdkjframework.minio.config.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.connectivity
 * @ClassName: MinioConfiguration
 * @Description: minio配置
 * @Author: xiaLin
 * @Date: 2024/9/2 11:30
 * @Version: 1.0
 */
public class MinioConfiguration {

	/**
	 * 配置信息
	 */
	private final MinioProperties minioProperties;

	/**
	 * 构建函数
	 */
	public MinioConfiguration(MinioProperties minioProperties) {
		this.minioProperties = minioProperties;
	}

	/**
	 * 启动
	 */
	@Bean(name = "start")
	public void start() {
		MinioClient client = MinioClient.builder()
				// 服务端IP+端口
				.endpoint(minioProperties.getEndpoint())
				// 服务端用户名 、 服务端密码
				.credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
				.build();

		// 实例化工具类
		new MinioUtils(client);
	}
}
