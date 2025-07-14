package com.cdkjframework.ai.model.openai;

import com.cdkjframework.ai.core.impl.BaseAiConfig;
import com.cdkjframework.ai.enums.ModelsName;
import com.cdkjframework.ai.enums.Openai;

/**
 * openai配置类，初始化API接口地址，设置默认的模型
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.openai
 * @ClassName: OpenaiConfig
 * @Description: openai配置类，初始化API接口地址，设置默认的模型
 * @Author: xiaLin
 * @Version: 1.0
 */
public class OpenaiConfig extends BaseAiConfig {

  /**
   * 构造函数
   */
  public OpenaiConfig() {
    // API接口地址
    super.setApiUrl("https://api.openai.com/v1");
    super.setModel(Openai.GPT_4O.getModel());
  }

  /**
   * 构造函数
   *
   * @param apiKey API密钥
   */
  public OpenaiConfig(String apiKey) {
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
    return ModelsName.OPENAI.getValue();
  }

}
