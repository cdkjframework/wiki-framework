package com.cdkjframework.ai.model.doubao;

import com.cdkjframework.ai.core.impl.BaseAiConfig;
import com.cdkjframework.ai.enums.DeepSeek;
import com.cdkjframework.ai.enums.DouBao;
import com.cdkjframework.ai.enums.ModelsName;

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
public class DouBaoConfig extends BaseAiConfig {

  /**
   * 构造函数
   */
  public DouBaoConfig() {
    // API接口地址
    super.setApiUrl("https://ark.cn-beijing.volces.com/api/v3");
    super.setModel(DouBao.DOUBAO_1_5_LITE_32K.getModel());
  }

  /**
   * 构造函数
   *
   * @param apiKey API密钥
   */
  public DouBaoConfig(String apiKey) {
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
    return ModelsName.DOUBAO.getValue();
  }

}
