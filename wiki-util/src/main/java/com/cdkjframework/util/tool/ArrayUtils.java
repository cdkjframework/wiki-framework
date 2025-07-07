package com.cdkjframework.util.tool;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ArrayUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ArrayUtils {

  /**
   * 判断数据是否为空
   *
   * @param array 长度
   * @return 数组对象为null或者长度为 0 时，返回 false
   */
  public static boolean isEmpty(Object[] array) {
    return array == null || array.length == 0;
  }

  /**
   * 判断数组是否不为空
   *
   * @param array 数组
   * @return 数组对象内含有任意对象时返回 true
   * @see ArrayUtils#isEmpty(Object[])
   */
  public static boolean isNotEmpty(Object[] array) {
    return !isEmpty(array);
  }


  /**
   * 判断是否为数组
   *
   * @param obj 对象
   * @return 是否为数组
   */
  public static boolean isArray(Object obj) {
    return obj != null && obj.getClass().isArray();
  }
}
