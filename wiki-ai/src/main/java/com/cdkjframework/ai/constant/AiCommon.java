package com.cdkjframework.ai.constant;

import lombok.Getter;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.constant
 * @ClassName: AiCommon
 * @Description: 公共类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AiCommon {

  /**
   * DouBao 上下文缓存参数
   */
  @Getter
  public enum DouBaoContext {

    /**
     * session
     */
    SESSION("session"),

    /**
     * 公共前缀
     */
    COMMON_PREFIX("common_prefix");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 构造函数
     *
     * @param mode 模式
     */
    DouBaoContext(String mode) {
      this.mode = mode;
    }
  }

  /**
   * DouBao 视觉参数
   */
  @Getter
  public enum DouBaoVision {

    /**
     * 自动
     */
    AUTO("auto"),
    /**
     * 低质量
     */
    LOW("low"),

    /**
     * 高质量
     */
    HIGH("high");

    /**
     * 详情
     */
    private final String value;

    /**
     * 构造函数
     *
     * @param detail 详情
     */
    DouBaoVision(String detail) {
      this.value = detail;
    }
  }

  /**
   * DouBao 视频生成参数
   */
  @Getter
  public enum DouBaoVideo {

    /**
     * 宽高比例 16:9
     * [1280, 720]
     */
    RATIO_16_9("--rt", "16:9"),
    /**
     * 宽高比例 4:3
     * [960, 720]
     */
    RATIO_4_3("--rt", "4:3"),
    /**
     * 宽高比例 1:1
     * [720, 720]
     */
    RATIO_1_1("--rt", "1:1"),
    /**
     * 宽高比例 3:4
     * [960, 1280]
     */
    RATIO_3_4("--rt", "3:4"),
    /**
     * 宽高比例 9:16
     * [1280, 720]
     */
    RATIO_9_16("--rt", "9:16"),
    /**
     * 宽高比例 21:9
     * [1280, 544]
     */
    RATIO_21_9("--rt", "21:9"),

    /**
     * 生成视频时长 5秒
     * 文生视频，图生视频
     */
    DURATION_5("--dur", 5),
    /**
     * 生成视频时长 10秒
     * 文生视频
     */
    DURATION_10("--dur", 10),

    //帧率，即一秒时间内视频画面数量
    FPS_5("--fps", 24),

    //视频分辨率
    RESOLUTION_5("--rs", "720p"),

    /**
     * 生成视频是否包含水印
     * 是
     */
    WATERMARK_TRUE("--wm", true),

    /**
     * 生成视频是否包含水印
     * 否
     */
    WATERMARK_FALSE("--wm", false);

    /**
     * 参数类型
     */
    private final String type;
    /**
     * 参数值
     */
    private final Object value;

    /**
     * 构造函数
     *
     * @param type  参数类型
     * @param value 参数值
     */
    DouBaoVideo(String type, Object value) {
      this.type = type;
      this.value = value;
    }
  }


  /**
   * grok视觉参数
   */
  @Getter
  public enum GrokVision {

    /**
     * 自动
     */
    AUTO("auto"),
    /**
     * 低
     */
    LOW("low"),
    /**
     * 高
     */
    HIGH("high");

    /**
     * 参数值
     */
    private final String value;

    /**
     * 参数构造
     *
     * @param value 参数值
     */
    GrokVision(String value) {
      this.value = value;
    }
  }


  /**
   * openai推理参数
   */
  @Getter
  public enum OpenaiReasoning {

    /**
     * 低
     */
    LOW("low"),

    /**
     * 中
     */
    MEDIUM("medium"),

    /**
     * 高
     */
    HIGH("high");

    /**
     * 推理难度
     */
    private final String effort;

    /**
     * 推理参数构造
     *
     * @param effort 推理难度
     */
    OpenaiReasoning(String effort) {
      this.effort = effort;
    }
  }

  /**
   * openai视觉参数
   */
  @Getter
  public enum OpenaiVision {

    /**
     * 自适应
     */
    AUTO("auto"),

    /**
     * 低
     */
    LOW("low"),

    /**
     * 高
     */
    HIGH("high");

    /**
     * 值
     */
    private final String value;

    /**
     * 构建函数
     *
     * @param value 值
     */
    OpenaiVision(String value) {
      this.value = value;
    }

  }

  /**
   * openai音频参数
   */
  @Getter
  public enum OpenaiSpeech {

    /**
     * 默认
     */
    ALLOY("alloy"),
    /**
     * 轻
     */
    ASH("ash"),

    /**
     *  中
     */
    CORAL("coral"),

    /**
     * 重
     */
    ECHO("echo"),
    /**
     * 粉
     */
    FABLE("fable"),
    /**
     * 晶
     */
    ONYX("onyx"),
    /**
     * 新
     */
    NOVA("nova"),
    /**
     * 圣人
     */
    SAGE("sage"),
    /**
     * 微光
     */
    SHIMMER("shimmer");

    /**
     * 语音
     */
    private final String voice;

    /**
     * 构造
     * @param voice 语音
     */
    OpenaiSpeech(String voice) {
      this.voice = voice;
    }
  }
}
