package com.cdkjframework.util.tool;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;

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

  /**
   * 判断两个数组是否相等，判断依据包括数组长度和每个元素都相等。
   *
   * @param array1 数组1
   * @param array2 数组2
   * @return 是否相等
   */
  public static boolean equals(Object array1, Object array2) {
    if (array1 == array2) {
      return true;
    }
    if (hasNull(array1, array2)) {
      return false;
    }

    AssertUtils.isTrue(isArray(array1), "First is not a Array !");
    AssertUtils.isTrue(isArray(array2), "Second is not a Array !");

    if (array1 instanceof long[]) {
      return Arrays.equals((long[]) array1, (long[]) array2);
    } else if (array1 instanceof int[]) {
      return Arrays.equals((int[]) array1, (int[]) array2);
    } else if (array1 instanceof short[]) {
      return Arrays.equals((short[]) array1, (short[]) array2);
    } else if (array1 instanceof char[]) {
      return Arrays.equals((char[]) array1, (char[]) array2);
    } else if (array1 instanceof byte[]) {
      return Arrays.equals((byte[]) array1, (byte[]) array2);
    } else if (array1 instanceof double[]) {
      return Arrays.equals((double[]) array1, (double[]) array2);
    } else if (array1 instanceof float[]) {
      return Arrays.equals((float[]) array1, (float[]) array2);
    } else if (array1 instanceof boolean[]) {
      return Arrays.equals((boolean[]) array1, (boolean[]) array2);
    } else {
      // Not an array of primitives
      return Arrays.deepEquals((Object[]) array1, (Object[]) array2);
    }
  }

  /**
   * 是否包含{@code null}元素
   *
   * @param <T>   数组元素类型
   * @param array 被检查的数组
   * @return 是否包含{@code null}元素
   */
  @SuppressWarnings("unchecked")
  public static <T> boolean hasNull(T... array) {
    if (isNotEmpty(array)) {
      for (T element : array) {
        if (ObjectUtils.isEmpty(element)) {
          return true;
        }
      }
    }
    return array == null;
  }
}
