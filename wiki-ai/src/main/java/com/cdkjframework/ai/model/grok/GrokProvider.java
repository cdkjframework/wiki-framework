package com.cdkjframework.ai.model.grok;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.AiProvider;
import com.cdkjframework.ai.enums.ModelsName;
import com.cdkjframework.ai.model.grok.impl.GrokServiceImpl;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.grok
 * @ClassName: GrokProvider
 * @Description: Grok供应商
 * @Author: xiaLin
 * @Version: 1.0
 */
public class GrokProvider implements AiProvider {
  /**
   * 获取服务名称
   *
   * @return 返回服务名称
   */
  @Override
  public String getServiceName() {
    return ModelsName.GROK.getValue();
  }

  /**
   * 创建服务
   *
   * @param config 配置信息
   * @return 返回服务
   */
  @Override
  public GrokService create(AiConfig config) {
    return new GrokServiceImpl(config);
  }
}
