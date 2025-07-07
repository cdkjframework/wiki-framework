package com.cdkjframework.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.config
 * @ClassName: ThumbnailConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/1/5 13:30
 * @Version: 1.0
 */
@Data
@Configuration
@RefreshScope
public class ThumbnailConfig {

  /**
   * 缩略图
   */
  @Value("spring.custom.thumbnail:null")
  private List<String> thumbnail;
}
