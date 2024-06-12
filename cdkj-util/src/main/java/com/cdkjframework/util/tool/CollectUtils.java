package com.cdkjframework.util.tool;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: CollectUtils
 * @Author: xiaLin
 * @Description: 集合工具
 * @Date: 2024/6/4 20:44
 * @Version: 1.0
 */
public class CollectUtils extends CollectionUtils {

  /**
   * 是否为空
   *
   * @param array 集合
   * @return 返回是否
   */

  public static boolean isEmpty(Object[] array) {
    return array == null || array.length == 0;
  }

  /**
   * 是否非空
   *
   * @param array 集合
   * @return 返回是否
   */
  public static boolean isNotEmpty(Object[] array) {
    return !isEmpty(array);
  }

  /**
   * 是否非空
   *
   * @param collection 集合
   * @return 返回是否
   */
  public static boolean isNotEmpty(@Nullable Collection<?> collection) {
    return !isEmpty(collection);
  }

  /**
   * 是否非空
   *
   * @param map 集合
   * @return 返回是否
   */
  public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
    return !isEmpty(map);
  }
}
