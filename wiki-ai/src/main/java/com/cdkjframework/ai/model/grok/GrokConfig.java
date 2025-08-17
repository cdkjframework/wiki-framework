package com.cdkjframework.ai.model.grok;

import com.cdkjframework.ai.core.impl.BaseAiConfig;
import com.cdkjframework.ai.enums.DeepSeek;
import com.cdkjframework.ai.enums.DouBao;
import com.cdkjframework.ai.enums.Grok;
import com.cdkjframework.ai.enums.ModelsName;
import com.cdkjframework.util.tool.StringUtils;

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
public class GrokConfig extends BaseAiConfig {

  /**
   * 构造函数
   */
  public GrokConfig() {
    // API接口地址
    super.setApiUrl("https://api.x.ai/v1");
    if (StringUtils.isNullAndSpaceOrEmpty(super.getModel())) {
      super.setModel(Grok.GROK_2_1212.getModel());
    }
  }

  /**
   * 构造函数
   *
   * @param apiKey API密钥
   */
  public GrokConfig(String apiKey) {
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
    return ModelsName.GROK.getValue();
  }

}
