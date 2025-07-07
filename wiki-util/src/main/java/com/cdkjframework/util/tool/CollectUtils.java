package com.cdkjframework.util.tool;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: CollectUtils
 * @Author: xiaLin
 * @Description: 集合工具
 * @Date: 2024/6/4 20:44
 * @Version: 1.0
 */
public class CollectUtils extends CollectionUtils {

  /**
   * 最大功率为2
   */
  private static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

  /**
   * JDK8
   */
  private static boolean isJdk8;

  static {
    // Java 8
    // Java 9+: 9,11,17
    try {
      isJdk8 = System.getProperty("java.version").startsWith("1.8.");
    } catch (Exception ignore) {
      isJdk8 = true;
    }
  }

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

  /**
   * 构建 List
   */
  @SafeVarargs
  public static <T> List<T> toList(T... t) {
    if (t != null) {
      return Arrays.asList(t);
    }
    return Collections.emptyList();
  }

  /**
   * 根据预期大小创建HashMap.
   *
   * @param expectedSize 预期大小
   * @param <K>          K
   * @param <V>          V
   * @return HashMap
   * @see com.google.common.collect.Maps#newHashMapWithExpectedSize
   */
  public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
    return new HashMap<>(capacity(expectedSize));
  }


  /**
   * 计算容量
   *
   * @param expectedSize 预期大小
   * @return 计算容量
   */
  private static int capacity(int expectedSize) {
    if (expectedSize < 3) {
      if (expectedSize < 0) {
        throw new IllegalArgumentException("expectedSize cannot be negative but was: " + expectedSize);
      }
      return expectedSize + 1;
    }
    if (expectedSize < MAX_POWER_OF_TWO) {
      // This is the calculation used in JDK8 to resize when a putAll
      // happens; it seems to be the most conservative calculation we
      // can make.  0.75 is the default load factor.
      return (int) ((float) expectedSize / 0.75F + 1.0F);
    }
    // any large value
    return Integer.MAX_VALUE;
  }

  /**
   * 用来过渡下Jdk1.8下ConcurrentHashMap的性能bug
   *
   * <p>
   * A temporary workaround for Java 8 ConcurrentHashMap#computeIfAbsent specific performance issue: JDK-8161372.</br>
   *
   * @param concurrentHashMap ConcurrentHashMap 没限制类型了，非ConcurrentHashMap就别调用这方法了
   * @param key               key
   * @param mappingFunction   function
   * @param <K>               k
   * @param <V>               v
   * @return V
   */
  public static <K, V> V computeIfAbsent(Map<K, V> concurrentHashMap, K key, Function<? super K, ? extends V> mappingFunction) {
    Objects.requireNonNull(mappingFunction);
    if (isJdk8) {
      V v = concurrentHashMap.get(key);
      if (null == v) {
        // issue#11986 lock bug
        // v = map.computeIfAbsent(key, func);

        // this bug fix methods maybe cause `func.apply` multiple calls.
        v = mappingFunction.apply(key);
        if (null == v) {
          return null;
        }
        final V res = concurrentHashMap.putIfAbsent(key, v);
        if (null != res) {
          // if pre value present, means other thread put value already, and putIfAbsent not effect
          // return exist value
          return res;
        }
        // if pre value is null, means putIfAbsent effected, return current value
      }
      return v;
    } else {
      return concurrentHashMap.computeIfAbsent(key, mappingFunction);
    }
  }

  /**
   * 切割集合为多个集合
   *
   * @param entityList 数据集合
   * @param batchSize  每批集合的大小
   * @param <T>        数据类型
   * @return 切割后的多个集合
   */
  public static <T> List<List<T>> split(Collection<T> entityList, int batchSize) {
    if (isEmpty(entityList)) {
      return Collections.emptyList();
    }
    AssertUtils.isFalse(batchSize < 1, "batchSize must not be less than one");
    final Iterator<T> iterator = entityList.iterator();
    final List<List<T>> results = new ArrayList<>(entityList.size() / batchSize);
    while (iterator.hasNext()) {
      final List<T> list = IntStream.range(0, batchSize).filter(x -> iterator.hasNext())
          .mapToObj(i -> iterator.next()).collect(Collectors.toList());
      if (!list.isEmpty()) {
        results.add(list);
      }
    }
    return results;
  }
}
