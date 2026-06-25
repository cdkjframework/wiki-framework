package com.cdkjframework.configure;

import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.configure
 * @ClassName: ConfigureRefreshController
 * @Description: Spring Cloud Config 客户端配置读取及刷新接口
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/configure")
public class ConfigureRefreshController {

  /**
   * 上下文刷新器
   */
  private ContextRefresher contextRefresher;

  /**
   * 构造函数
   *
   * @param contextRefresher 刷新终结点
   */
  public ConfigureRefreshController(ContextRefresher contextRefresher) {
    this.contextRefresher = contextRefresher;
  }

  /**
   * 刷新
   */
  @PostMapping(value = "/refresh")
  public void refresh() {
    if (contextRefresher != null) {
      contextRefresher.refresh();
    }
  }
}
