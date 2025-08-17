package com.cdkjframework.ai.enums;

import lombok.Getter;

/**
 * DeepSeek 模型
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.enums
 * @ClassName: DeepSeek
 * @Description: DeepSeek 模型
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
public enum DeepSeek {
  /**
   * 模型名称 deepseek-chat
   */
  DEEPSEEK_CHAT("deepseek-chat"),

  /**
   * 模型名称 deepseek-reasoner
   */
  DEEPSEEK_REASONER("deepseek-reasoner");

  /**
   * 模型名称
   */
  private final String model;

  /**
   * 构造函数
   *
   * @param model 模型名称
   */
  DeepSeek(String model) {
    this.model = model;
  }
}
