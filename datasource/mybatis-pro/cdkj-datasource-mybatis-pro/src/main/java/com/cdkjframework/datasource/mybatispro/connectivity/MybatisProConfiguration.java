package com.cdkjframework.datasource.mybatispro.connectivity;

import com.cdkjframework.datasource.mybatispro.config.MybatisProConfig;
import com.cdkjframework.util.log.LogUtils;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: GenerateController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisProConfiguration {
	/**
	 * 日志
	 */
	private LogUtils logUtil = LogUtils.getLogger(MybatisProConfiguration.class);

	/**
	 * MybatisPro 配置
	 */
	private final MybatisProConfig mybatisProConfig;

	/**
	 * 构造方法
	 *
	 * @param mybatisProConfig MybatisPro 配置
	 */
	public MybatisProConfiguration(MybatisProConfig mybatisProConfig) {
		this.mybatisProConfig = mybatisProConfig;
	}
}
