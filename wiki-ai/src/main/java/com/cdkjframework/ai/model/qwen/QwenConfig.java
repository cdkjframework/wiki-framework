package com.cdkjframework.ai.model.qwen;

import com.cdkjframework.ai.core.impl.BaseAiConfig;
import com.cdkjframework.ai.enums.ModelsName;
import com.cdkjframework.ai.enums.Openai;
import com.cdkjframework.ai.enums.Qwen;
import com.cdkjframework.util.tool.StringUtils;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.qwen
 * @ClassName: QwenConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/8/3 9:07
 * @Version: 1.0
 */
public class QwenConfig extends BaseAiConfig {
  /**
   * 构造函数
   */
  public QwenConfig() {
    // API接口地址
    super.setApiUrl("https://dashscope.aliyuncs.com/compatible-mode/v1");
    if (StringUtils.isNullAndSpaceOrEmpty(super.getModel())) {
      super.setModel(Qwen.QWEN_PLUS.getModel());
    }
  }

  /**
   * 构造函数
   *
   * @param apiKey API密钥
   */
  public QwenConfig(String apiKey) {
    this();
    super.setApiKey(apiKey);
  }

  /**
   * 获取模型（厂商）名称
   *
   * @return 返回模型（厂商）名称
   */
  @Override
  public String getModelName() {
    return ModelsName.QWEN.getValue();
  }
}
