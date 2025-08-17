package com.cdkjframework.ai.model.doubao;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.AiProvider;
import com.cdkjframework.ai.enums.ModelsName;
import com.cdkjframework.ai.model.doubao.impl.DouBaoServiceImpl;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.deepseek
 * @ClassName: DeepSeekProvider
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class DouBaoProvider implements AiProvider {

  /**
   * 获取服务名称
   *
   * @return 返回服务名称
   */
  @Override
  public String getServiceName() {
    return ModelsName.DOUBAO.getValue();
  }

  /**
   * 创建服务
   *
   * @param config 配置信息
   * @return 返回服务
   */
  @Override
  public DouBaoService create(AiConfig config) {
    return new DouBaoServiceImpl(config);
  }
}
