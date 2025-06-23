package com.cdkjframework.config;

import com.cdkjframework.constant.IntegerConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.config
 * @ClassName: CustomConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Component
@Configuration
@RefreshScope
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.custom")
public class CustomConfig {

  /**
   * 时间格式
   */
  private String dateFormat = "yyyy-MM-dd HH:mm:ss";

  /**
   * 时区
   */
  private String timeZone = "GMT+8";

  /**
   * jwt Key
   */
  private String jwtKey = "wiki-framework-jwt";

  /**
   * 模块
   */
  private String modular = "[]";

  /**
   * 是否加密
   */
  private boolean encryption = false;

  /**
   * 是否 JSON 格式
   */
  private boolean json = false;

  /**
   * 过虑接口
   */
  private List<String> filters;

  /**
   * webSocket服务
   */
  private String webSocketClassName;

  /**
   * 调用方法
   */
  private String webSocketMethodName;

  /**
   * 控制器 aop
   */
  private String aopController;

  /**
   * 映射器 aop
   */
  private String aopMapper;

  /**
   * Redis 渠道订阅
   */
  private List<String> channel;

  /**
   * Redis 模式订阅
   */
  private List<String> pattern;

  /**
   * 匹配器
   */
  private List<String> ignoreUrls;

  /**
   * 开放接口
   */
  private List<String> openUrls;

  /**
   * 唯一登录地址
   */
  private List<String> uniqueLoginUrls;

  /**
   * AOP 过滤地址
   */
  private List<String> ignoreAopUrls;

  /**
   * 许可
   */
  private String permission;

  /**
   * 密钥（16进制，和前台保持一致，或者是作为参数直接传过来也可以）
   */
  private String defaultKey = "cn.framewiki.com";

  /**
   * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
   */
  private String defaultIv = "hk.framewiki.com";

  /**
   * 算法 PKCS5 Padding
   */
  private String aesCbcNoPadding = "AES/CBC/NoPadding";

  /**
   * 算法 PKCS5 Padding
   */
  private String aesEcbNoPadding = "AES/ECB/NoPadding";

  /**
   * 加密类型 (AES加密类型 0：CBC，1：ECB)
   */
  private Integer aesType = IntegerConsts.ZERO;

  /**
   * 密码类型
   */
  private String passwordType = "AES";

  /**
   * 编码类型
   */
  private String charsetName = "utf-8";

  /**
   * Security 登录地址
   */
  private String loginUrl = "/security/user/login";

  /**
   * Security 登录成功后跳转地址
   */
  private String loginSuccess = "/callback/redirect";

  /**
   * Security 登录页面设置
   */
  private String loginPage = "/login.html";

  /**
   * Security 退出地址
   */
  private String logoutUrl = "/security/user/logout";

  /**
   * 默认账号
   */
  private String username = "framewiki";

  /**
   * 默认账号密码
   */
  private String password = "framewiki";

  /**
   * Security 资源过滤地址
   */
  private List<String> patternsUrls = Arrays.asList("/security/**", "/configure/**", "/login.html");

  /**
   * 状态码
   */
  private Integer statusCode;

  /**
   * 错误状态码
   */
  private Integer errorCode;

  /**
   * 基础数据（0或空：mysql、1：postgreSql、2：msSQL）
   */
  private Integer dataBase;
  /**
   * 脱敏
   */
  private boolean desensitization;
  /**
   * redis 日志主题
   */
  private String redisLogTopic = "CHANNEL-BodyMessage-Log";

  /**
   * 二维码LOGO（URI地址）
   */
  private String qrlogo;

  /**
   * 程序模板
   */
  private String classTemplate = "/com/cdkjframework/templates/";

	/**
	 * 目录模板
	 */
	private String templatePath;

	/**
	 * 文件大小（KB为单位）
	 */
	private Long maxFileSize;

	/**
	 * 数据加密
	 */
	private boolean data;

	/**
	 * 数据字段
	 */
	private List<String> fields;

	/**
	 * 静态资源
	 */
	private Resource resource;

	@Data
	public static class Resource {

		/**
		 * 静态资源路径
		 */
		private String[] locations;

		/**
		 * 路径模式
		 */
		private String[] pathPatterns;

		/**
		 * 缓存 默认为 false
		 */
		private boolean cache = false;
	}
}
