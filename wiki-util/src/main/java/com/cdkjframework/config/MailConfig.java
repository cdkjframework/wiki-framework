package com.cdkjframework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.config
 * @ClassName: MailConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

	/**
	 * 主机
	 */
	private String host;

	/**
	 * 端口
	 */
	private Integer port;

	/**
	 * 账号
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 发邮件地址
	 */
	private String fromMail;

	/**
	 * 收件地址
	 */
	private List<String> toMail;

	/**
	 * 协议
	 */
	private String protocol;

	/**
	 * 发件人名称
	 */
	private String personal;

	/**
	 * 延迟时间
	 */
	private Integer mailTimeout = 0;

	/**
	 * 编码
	 */
	private String encoding = "UTF-8";

	/**
	 * SSL加密
	 */
	private boolean ssl = true;

	/**
	 * 权限验证
	 */
	private boolean auth = true;
}
