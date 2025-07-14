package com.cdkjframework.ai.core;

/**
 * AI服务接口，创建的AI服务都要实现此接口
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core
 * @ClassName: AiProvider
 * @Description: AI服务接口，创建的AI服务都要实现此接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface AiProvider {
  /**
   * 获取服务名称
   *
   * @return 返回服务名称
   */
  String getServiceName();

  /**
   * 创建服务
   *
   * @param config 配置信息
   */
  <T extends AiService> T create(final AiConfig config);
}
