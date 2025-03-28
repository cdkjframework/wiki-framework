package com.cdkjframework.mp.enums;

import com.cdkjframework.enums.InterfaceEnum;
import com.cdkjframework.util.tool.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.enums
 * @ClassName: MaterialEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/28 22:03
 * @Version: 1.0
 */
public enum MaterialEnum implements InterfaceEnum {
  /**
   * 未知
   */
  UNKNOWN("unknown", "未知"),
  /**
   * 图片
   */
  IMAGE("image", "图片"),
  /**
   * 视频
   */
  VIDEO("video", "视频"),
  /**
   * 语音
   */
  VOICE("voice", "语音"),
  /**
   * 图文
   */
  NEWS("news", "图文");
  /**
   * 值、内容
   */
  private final String value;
  private final String text;

  /**
   * 构建函数
   *
   * @param value 值
   * @param text  说明
   */
  MaterialEnum(String value, String text) {
    this.value = value;
    this.text = text;
  }

  /**
   * 获取值
   *
   * @return 返回值
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * 获取描述
   *
   * @return 返描述
   */
  @Override
  public String getText() {
    return text;
  }

  /**
   * 获取下节点值
   *
   * @return 返下节点值
   */
  @Override
  public String getNode() {
    return null;
  }

  /**
   * 根据值获取类型
   *
   * @param value 值
   * @return 返回结果
   */
  public static MaterialEnum formValue(String value) {
    if (StringUtils.isNullAndSpaceOrEmpty(value)) {
      return MaterialEnum.UNKNOWN;
    }
    Optional<MaterialEnum> optional = Arrays.stream(MaterialEnum.values())
        .filter(f -> f.getValue().equals(value))
        .findFirst();
    if (optional.isPresent()) {
      return optional.get();
    } else {
      return MaterialEnum.UNKNOWN;
    }
  }
}
