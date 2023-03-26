package com.cdkjframework.mp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.config
 * @ClassName: MpConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/6 19:44
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.custom.wechat.mp")
public class MpConfig {

  /**
   * APPID
   */
  private String appId = "wxea4e6cdbd36b70fc";

  /**
   * 密钥
   */
  private String appSecret = "916805b393431a459294bee19d041ace";
}
