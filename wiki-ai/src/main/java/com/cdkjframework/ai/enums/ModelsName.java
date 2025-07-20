package com.cdkjframework.ai.enums;

import lombok.Getter;

/**
 * 模型名称枚举
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.enums
 * @ClassName: ModelsName
 * @Description: 模型名称枚举
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
public enum ModelsName {

  /**
   * deepSeek
   */
  DEEPSEEK("deepSeek"),
  /**
   * openai
   */
  OPENAI("openai"),
  /**
   * doubao
   */
  DOUBAO("doubao"),
  /**
   * grok
   */
  GROK("grok"),
  /**
   * qwen
   */
  QWEN("qwen");

  /**
   * 值
   */
  private final String value;

  /**
   * 构造方法
   *
   * @param value 模型名称
   */
  ModelsName(String value) {
    this.value = value;
  }
}
