package com.cdkjframework.ai.model.qwen;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.AiProvider;
import com.cdkjframework.ai.enums.ModelsName;
import com.cdkjframework.ai.model.qwen.impl.QwenServiceImpl;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.qwen
 * @ClassName: QwenProvider
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/8/3 9:08
 * @Version: 1.0
 */
public class QwenProvider implements AiProvider {
  /**
   * 获取服务名称
   *
   * @return 返回服务名称
   */
  @Override
  public String getServiceName() {
    return ModelsName.QWEN.getValue();
  }

  /**
   * 创建服务
   *
   * @param config 配置信息
   */
  @Override
  public QwenService create(AiConfig config) {
    return new QwenServiceImpl(config);
  }
}
