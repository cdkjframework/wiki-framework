package com.cdkjframework.ai.enums;

import lombok.Getter;

/**
 * Openai 模型
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.enums
 * @ClassName: Openai
 * @Description: Openai 模型
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
public enum Openai {
  /**
   * 模型名称 gpt-4.5-preview
   */
  GPT_4_5_PREVIEW("gpt-4.5-preview"),

  /**
   * 模型名称 gpt-4o
   */
  GPT_4O("gpt-4o"),

  /**
   * 模型名称 gpt-4o-latest
   */
  CHATGPT_4O_LATEST("chatgpt-4o-latest"),
  /**
   * 模型名称 gpt-4o-mini
   */
  GPT_4O_MINI("gpt-4o-mini"),
  /**
   * 模型名称 o1
   */
  O1("o1"),
  O1_MINI("o1-mini"),
  /**
   * 模型名称 o3-mini
   */
  O3_MINI("o3-mini"),
  /**
   * 模型名称 o1-preview
   */
  O1_PREVIEW("o1-preview"),
  /**
   * 模型名称 gpt-4o-realtime-preview
   */
  GPT_4O_REALTIME_PREVIEW("gpt-4o-realtime-preview"),
  /**
   * 模型名称 gpt-4o-mini-realtime-preview
   */
  GPT_4O_MINI_REALTIME_PREVIEW("gpt-4o-mini-realtime-preview"),
  /**
   * 模型名称 gpt-4o-audio-preview
   */
  GPT_4O_AUDIO_PREVIEW("gpt-4o-audio-preview"),
  /**
   * 模型名称 gpt-4o-mini-audio-preview
   */
  GPT_4O_MINI_AUDIO_PREVIEW("gpt-4o-mini-audio-preview"),
  /**
   * 模型名称 gpt-4-turbo
   */
  GPT_4_TURBO("gpt-4-turbo"),
  /**
   * 模型名称 gpt-4-turbo-preview
   */
  GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview"),
  /**
   * 模型名称 gpt-4
   */
  GPT_4("gpt-4"),
  /**
   * 模型名称 gpt-3.5-turbo-preview
   */
  GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125"),
  /**
   * 模型名称 gpt-3.5-turbo
   */
  GPT_3_5_TURBO("gpt-3.5-turbo"),
  /**
   * 模型名称 gpt-3.5-turbo-1106
   */
  GPT_3_5_TURBO_1106("gpt-3.5-turbo-1106"),
  /**
   * 模型名称 gpt-3.5-turbo-instruct
   */
  GPT_3_5_TURBO_INSTRUCT("gpt-3.5-turbo-instruct"),
  /**
   * 模型名称 dall-e-3
   */
  DALL_E_3("dall-e-3"),
  /**
   * 模型名称 dall-e-2
   */
  DALL_E_2("dall-e-2"),
  /**
   * 模型名称 tts-1
   */
  TTS_1("tts-1"),
  /**
   * 模型名称 tts-1-hd
   */
  TTS_1_HD("tts-1-hd"),
  /**
   * 模型名称 whisper-1
   */
  WHISPER_1("whisper-1"),
  /**
   * 模型名称 text-embedding-3-large
   */
  TEXT_EMBEDDING_3_LARGE("text-embedding-3-large"),
  /**
   * 模型名称 text-embedding-3-small
   */
  TEXT_EMBEDDING_3_SMALL("text-embedding-3-small"),
  /**
   * 模型名称 text-embedding-ada-002
   */
  TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
  /**
   * 模型名称 omni-moderation
   */
  OMNI_MODERATION_LATEST("omni-moderation-latest"),
  /**
   * 模型名称 omni-moderation-2024-09-26
   */
  OMNI_MODERATION_2024_09_26("omni-moderation-2024-09-26"),
  /**
   * 模型名称 text-moderation
   */
  TEXT_MODERATION_LATEST("text-moderation-latest"),
  /**
   * 模型名称 text-moderation-stable
   */
  TEXT_MODERATION_STABLE("text-moderation-stable"),
  /**
   * 模型名称 text-moderation-007
   */
  TEXT_MODERATION_007("text-moderation-007"),
  /**
   * 模型名称 babbage-002
   */
  BABBAGE_002("babbage-002"),
  /**
   * 模型名称 davinci-002
   */
  DAVINCI_002("davinci-002");

  /**
   * 模型名称
   */
  private final String model;

  /**
   * 构造函数
   *
   * @param model 模型名称
   */
  Openai(String model) {
    this.model = model;
  }
}
