package com.cdkjframework.ai.enums;

import lombok.Getter;

/**
 * Doubao 模型
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.enums
 * @ClassName: DouBao
 * @Description: Doubao 模型
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
public enum DouBao {
  /**
   * 模型名称 doubao-1.5-pro-32k-250115
   */
  DOUBAO_1_5_PRO_32K("doubao-1.5-pro-32k-250115"),

  /**
   * 模型名称 doubao-1.5-pro-256k-250115
   */
  DOUBAO_1_5_PRO_256K("doubao-1.5-pro-256k-250115"),
  /**
   * 模型名称 doubao-1.5-lite-32k-250115
   */
  DOUBAO_1_5_LITE_32K("doubao-1.5-lite-32k-250115"),
  /**
   * 模型名称 doubao-1.5-lite-256k-250115
   */
  DEEPSEEK_R1("deepseek-r1-250120"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-32b-250120
   */
  DEEPSEEK_R1_DISTILL_QWEN_32B("deepseek-r1-distill-qwen-32b-250120"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-250120
   */
  DEEPSEEK_R1_DISTILL_QWEN_7B("deepseek-r1-distill-qwen-7b-250120"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DEEPSEEK_V3("deepseek-v3-241226"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_4K_240515("doubao-pro-4k-240515"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_4K_CHARACTER_240728("doubao-pro-4k-character-240728"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_4K_FUNCTIONCALL_240615("doubao-pro-4k-functioncall-240615"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_4K_BROWSING_240524("doubao-pro-4k-browsing-240524"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_32K_241215("doubao-pro-32k-241215"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_32K_FUNCTIONCALL_241028("doubao-pro-32k-functioncall-241028"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_32K_BROWSING_241115("doubao-pro-32k-browsing-241115"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_32K_CHARACTER_241215("doubao-pro-32k-character-241215"),

  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_128K_240628("doubao-pro-128k-240628"),

  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_PRO_256K_240828("doubao-pro-256k-240828"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_LITE_4K_240328("doubao-lite-4k-240328"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_LITE_4K_PRETRAIN_CHARACTER_240516("doubao-lite-4k-pretrain-character-240516"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_LITE_32K_240828("doubao-lite-32k-240828"),
  /**
   * 模型名称 doubao-1.5-distill-qwen-7b-240615
   */
  DOUBAO_LITE_32K_CHARACTER_241015("doubao-lite-32k-character-241015"),
  /**
   * 模型名称 240828
   */
  DOUBAO_LITE_128K_240828("240828"),
  /**
   * 模型名称 moonshot-v1-8k
   */
  MOONSHOT_V1_8K("moonshot-v1-8k"),
  /**
   * 模型名称 moonshot-v1-32k
   */
  MOONSHOT_V1_32K("moonshot-v1-32k"),
  /**
   * 模型名称 moonshot-v1-128k
   */
  MOONSHOT_V1_128K("moonshot-v1-128k"),
  /**
   * 模型名称 moonshot-v1-256k
   */
  CHATGLM3_130B_FC("chatglm3-130b-fc-v1.0"),
  /**
   * 模型名称 chatglm3-130b-fc-v1.0
   */
  CHATGLM3_130_FIN("chatglm3-130-fin-v1.0-update"),
  /**
   * 模型名称 mistral-7b-instruct-v0.2
   */
  MISTRAL_7B("mistral-7b-instruct-v0.2"),
  /**
   * 模型名称 mistral-7b-instruct-v0.2
   */
  DOUBAO_1_5_VISION_PRO_32K("doubao-1.5-vision-pro-32k-250115"),
  /**
   * 模型名称 doubao-vision-pro-32k-241008
   */
  DOUBAO_VISION_PRO_32K("doubao-vision-pro-32k-241008"),
  /**
   * 模型名称 doubao-vision-lite-32k-241015
   */
  DOUBAO_VISION_LITE_32K("doubao-vision-lite-32k-241015"),
  /**
   * 模型名称 doubao-embedding-large-text-240915
   */
  DOUBAO_EMBEDDING_LARGE("doubao-embedding-large-text-240915"),
  /**
   * 模型名称 doubao-embedding-text-240715
   */
  DOUBAO_EMBEDDING_TEXT_240715("doubao-embedding-text-240715"),

  /**
   * 模型名称 doubao-embedding-vision-241215
   */
  DOUBAO_EMBEDDING_VISION("doubao-embedding-vision-241215"),
  /**
   * 模型名称 doubao-seedream-3-0-t2i-250415
   */
  DOUBAO_SEEDREAM_3_0_T2I("doubao-seedream-3-0-t2i-250415"),
  /**
   * 模型名称 doubao-seedream-3-0-i2t-250415
   */
  DOUBAO_SEEDANCE_1_0_LITE_T2V("doubao-seedance-1-0-lite-t2v-250428"),
  /**
   * 模型名称 doubao-seedream-3-0-i2t-250415
   */
  DOUBAO_SEEDANCE_1_0_LITE_I2V("doubao-seedance-1-0-lite-i2v-250428"),
  /**
   * 模型名称 doubao-seedream-3-0-i2t-250415
   */
  WAN2_1_14B_T2V("wan2-1-14b-t2v-250225"),
  /**
   * 模型名称 doubao-seedream-3-0-i2t-250415
   */
  WAN2_1_14B_I2V("wan2-1-14b-i2v-250225");

  /**
   * 模型名称
   */
  private final String model;

  /**
   * 构造函数
   *
   * @param model 模型名称
   */
  DouBao(String model) {
    this.model = model;
  }
}
