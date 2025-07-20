package com.cdkjframework.ai.enums;

import lombok.Getter;

/**
 * Grok 模型
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.enums
 * @ClassName: Grok
 * @Description: Grok 模型
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
public enum Grok {
  /**
   * 模型名称 grok-3-beta
   */
  GROK_3_BETA_LATEST("grok-3-beta"),
  /**
   * 模型名称 grok-3-beta
   */
  GROK_3_BETA("grok-3-beta"),
  /**
   * 模型名称 grok-3
   */
  GROK_3("grok-3"),
  /**
   * 模型名称 grok-3-mini-fast
   */
  GROK_3_MINI_FAST_LATEST("grok-3-mini-fast-beta"),
  /**
   * 模型名称 grok-3-mini-fast-beta
   */
  GROK_3_MINI_FAST_BETA("grok-3-mini-fast-beta"),
  /**
   * 模型名称 grok-3-mini-fast
   */
  GROK_3_MINI_FAST("grok-3-mini-fast-beta"),
  /**
   * 模型名称 grok-3-fast-beta
   */
  GROK_3_FAST_LATEST("grok-3-fast-beta"),
  /**
   * 模型名称 grok-3-fast-beta
   */
  GROK_3_FAST_BETA("grok-3-fast-beta"),
  /**
   * 模型名称 grok-3-fast
   */
  GROK_3_FAST("grok-3-fast-beta"),
  /**
   * 模型名称 grok-3-mini-beta
   */
  GROK_3_MINI_LATEST("grok-3-mini-beta"),
  /**
   * 模型名称 grok-3-mini-beta
   */
  GROK_3_MINI_BETA("grok-3-mini-beta"),
  /**
   * 模型名称 grok-3-mini
   */
  GROK_3_MINI("grok-3-mini-beta"),
  /**
   * 模型名称 grok-2-image-1212
   */
  GROK_2_IMAGE_LATEST("grok-2-image-1212"),
  /**
   * 模型名称 grok-2-image-1212
   */
  GROK_2_IMAGE("grok-2-image-1212"),
  /**
   * 模型名称 grok-2-image-1212
   */
  GROK_2_IMAGE_1212("grok-2-image-1212"),
  /**
   * 模型名称 grok-2-latest
   */
  grok_2_latest("grok-2-1212"),
  /**
   * 模型名称 grok-2
   */
  GROK_2("grok-2-1212"),
  /**
   * 模型名称 grok-2-1212
   */
  GROK_2_1212("grok-2-1212"),
  /**
   * 模型名称 grok-2-vision-1212
   */
  GROK_2_VISION_1212("grok-2-vision-1212"),
  /**
   * 模型名称 grok-beta
   */
  GROK_BETA("grok-beta"),
  /**
   * 模型名称 grok-vision-beta
   */
  GROK_VISION_BETA("grok-vision-beta");

  /**
   * 模型名称
   */
  private final String model;

  /**
   * 构造函数
   *
   * @param model 模型名称
   */
  Grok(String model) {
    this.model = model;
  }
}
