package com.cdkjframework.core.spring.format;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import jakarta.servlet.MultipartConfigElement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.core.spring.format
 * @ClassName: MultipartConfiguration
 * @Description: 文件上传配置
 * @Author: xiaLin
 * @Date: 2023/2/10 9:16
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class MultipartConfiguration {

	/**
	 * 自定义配置
	 */
	private final CustomConfig customConfig;

	/**
	 * 文件上传配置
	 * BYTES("B", DataSize.ofBytes(1L)),
	 * KILOBYTES("KB", DataSize.ofKilobytes(1L)),
	 * MEGABYTES("MB", DataSize.ofMegabytes(1L)),
	 * GIGABYTES("GB", DataSize.ofGigabytes(1L)),
	 * TERABYTES("TB", DataSize.ofTerabytes(1L));
	 *
	 * @return 多部分配置元素
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		long amount;
		if (customConfig.getMaxFileSize() == null) {
			amount = IntegerConsts.ONE_HUNDRED;
		} else {
			amount = customConfig.getMaxFileSize() / IntegerConsts.BYTE_LENGTH;
		}
		// 单个数据大小 // KB,MB
		factory.setMaxFileSize(DataSize.of(amount, DataUnit.MEGABYTES));
		// 总上传数据大小
		factory.setMaxRequestSize(DataSize.of(amount, DataUnit.MEGABYTES));
		return factory.createMultipartConfig();
	}
}
