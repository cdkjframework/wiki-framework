package com.cdkjframework.enums;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.enums
 * @ClassName: AnnotationEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum AnnotationEnums {
  /**
   * 字符串
   */
  String {
    @Override
    public int getValue() {
      return 0;
    }

    @Override
    public String getName() {
      return "字符串";
    }
  },
  /**
   * 数字
   */
  Number {
    @Override
    public int getValue() {
      return 1;
    }

    @Override
    public String getName() {
      return "数据字";
    }
  },
  /**
   * 布尔
   */
  Boolean {
    @Override
    public int getValue() {
      return 3;
    }

    @Override
    public String getName() {
      return "布尔值";
    }
  },
  /**
   * 数组
   */
  ArrayList {
    @Override
    public int getValue() {
      return 4;
    }

    @Override
    public String getName() {
      return "布尔值";
    }
  };

  /**
   * 获取枚举值
   *
   * @return 返回结果
   */
  public abstract int getValue();

  /**
   * 获取枚举名称
   *
   * @return 返回结果
   */
  public abstract String getName();

}
