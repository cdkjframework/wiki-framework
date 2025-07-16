package com.cdkjframework.license.util;

import java.util.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.service
 * @ClassName: CommonUtils
 * @Description: 常用判断和集合转换工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class CommonUtils {

  /**
   * 判断对象是否为空
   *
   * @param object ignore
   * @return 值
   */
  public static String validStringValue(Object object) {
    return object == null ? "" : object.toString();
  }

  /**
   * 获取int值
   *
   * @param object ignore
   * @return 返回int值
   */
  public static int validIntValue(Object object) {
    return object == null ? 0 : Integer.parseInt(object.toString());
  }

  /**
   * 获取long值
   *
   * @param object ignore
   * @return 返回long值
   */
  public static long validLongValue(Object object) {
    return object == null ? 0L : Long.parseLong(object.toString());
  }

  /**
   * 判断对象是否为空
   *
   * @param object ignore
   * @return 布尔值
   */
  public static boolean isNotEmpty(Object object) {
    if (object == null) {
      return false;
    } else if (object instanceof Integer) {
      return Integer.parseInt(object.toString()) > 0;
    } else if (object instanceof Long) {
      return Long.parseLong(object.toString()) > 0L;
    } else if (object instanceof String) {
      return !((String) object).trim().isEmpty();
    } else if (object instanceof StringBuffer) {
      return !((StringBuffer) object).toString().trim().isEmpty();
    } else if (object instanceof Boolean) {
      return Boolean.parseBoolean(object.toString());
    } else if (object instanceof List) {
      return !((List<?>) object).isEmpty();
    } else if (object instanceof Set) {
      return !((Set<?>) object).isEmpty();
    } else if (object instanceof Map) {
      return !((Map<?, ?>) object).isEmpty();
    } else if (object instanceof Iterator) {
      return ((Iterator<?>) object).hasNext();
    } else if (object.getClass().isArray()) {
      return true;
    } else {
      return true;
    }
  }

  /**
   * 判断对象是否为空
   *
   * @param object ignore
   * @return 返回布尔值
   */
  public static boolean isEmpty(Object object) {
    if (object == null) {
      return true;
    } else if (object instanceof Integer) {
      return Integer.valueOf(object.toString()) == 0;
    } else if (object instanceof Long) {
      return Long.valueOf(object.toString()) == 0L;
    } else if (object instanceof String) {
      return ((String) object).trim().length() == 0;
    } else if (object instanceof StringBuffer) {
      return ((StringBuffer) object).toString().trim().length() == 0;
    } else if (object instanceof Boolean) {
      return Boolean.valueOf(object.toString());
    } else if (object instanceof List) {
      return ((List) object).size() == 0;
    } else if (object instanceof Set) {
      return ((Set) object).size() == 0;
    } else if (object instanceof Map) {
      return ((Map) object).size() == 0;
    } else if (object instanceof Iterator) {
      return !((Iterator) object).hasNext();
    } else if (object.getClass().isArray()) {
      return Arrays.asList(object).size() == 0;
    } else {
      return false;
    }
  }

  public static List<Long> set2List(Set<Long> sets) {
    List<Long> list = new ArrayList();
    if (sets != null && sets.size() > 0) {
      Iterator var2 = sets.iterator();

      while (var2.hasNext()) {
        Object o = var2.next();
        Long id = Long.valueOf(o.toString());
        list.add(id);
      }

      return list;
    } else {
      return null;
    }
  }

  public static List<String> set2ListStr(Set<?> set) {
    ArrayList result = new ArrayList();

    try {
      if (set.size() > 0) {
        set.forEach((s) -> {
          result.add(s.toString());
        });
      }
    } catch (Exception var3) {
      System.err.println("HashSet转换失败");
    }

    return result;
  }

  /**
   * List转Set
   *
   * @param list 列表
   * @return Set
   */
  public static Set<Long> list2Set(List<String> list) {
    HashSet<Long> result = new HashSet<>();

    try {
      if (!list.isEmpty()) {
        list.forEach((s) -> {
          result.add(Long.parseLong(s));
        });
      }
    } catch (Exception var3) {
      System.err.println("List转换失败");
    }

    return result;
  }

  /**
   * Set转List
   *
   * @param sets 集合
   * @return List
   */
  public static Set<Long> hashSetToSet(HashSet<?> sets) {
    Set<Long> result = new HashSet<>();
    if (sets != null && !sets.isEmpty()) {

      for (Object o : sets) {
        Long id = Long.valueOf(o.toString());
        result.add(id);
      }

      return result;
    } else {
      return null;
    }
  }

  /**
   * Set转List
   *
   * @param set 集合
   * @return List
   */
  public static List<String> setToList(Set<?> set) {
    List<String> result = new ArrayList<>();
    try {
      if (!set.isEmpty()) {
        set.forEach((s) -> {
          result.add(s.toString());
        });
      }
    } catch (Exception var3) {
      System.err.println("HashSet转换失败");
    }
    return result;
  }

  /**
   * list 转List
   *
   * @param list 集合
   * @return List
   */
  public static List<String> listToList(List<?> list) {
    List<String> result = new ArrayList<>();
    try {
      if (!list.isEmpty()) {
        list.forEach((s) -> {
          result.add(s.toString());
        });
      }
    } catch (Exception var3) {
      System.err.println("List转换失败");
    }
    return result;
  }

  /**
   * List 转 String
   *
   * @param list 集合
   * @return String
   */
  public static String listToString(List<String> list) {
    if (list == null) {
      return null;
    } else {
      String result = "";

      try {
        if (list.size() <= 0) {
          return result;
        }

        String s;
        for (Iterator<String> var2 = list.iterator(); var2.hasNext(); result = result + s + ";") {
          s = var2.next();
        }

        result = result.substring(0, result.length() - 1);
      } catch (Exception var4) {
        System.err.println("List转换失败");
      }

      return result;
    }
  }

  /**
   * String转换成List
   *
   * @param str 字符串
   * @return List
   */
  public static List<String> stringTolist(String str) {
    if (str != null && !str.isEmpty()) {
      List<String> list = new ArrayList<>();

      try {
        String[] strs = str.substring(1, str.length() - 1).replaceAll("\"", "").split(",");
        int var4 = strs.length;

        list.addAll(Arrays.asList(strs).subList(0, var4));
      } catch (Exception var7) {
        System.err.println("List转换失败");
      }

      return list;
    } else {
      return null;
    }
  }
}
