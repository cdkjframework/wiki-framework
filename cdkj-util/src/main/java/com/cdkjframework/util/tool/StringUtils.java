package com.cdkjframework.util.tool;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.constant.StringConstant;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: StringUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class StringUtils implements Serializable, StringConstant {

  /**
   * 字符串去除空白内容
   *
   * <ul> <li>'"<>&*+=#-; sql注入黑名单</li> <li>\n 回车</li> <li>\t 水平制表符</li> <li>\s 空格</li> <li>\r 换行</li> </ul>
   */
  private static final Pattern REPLACE_BLANK = Pattern.compile("'|\"|\\<|\\>|&|\\*|\\+|=|#|-|;|\\s*|\t|\r|\n");

  /**
   * 空值变量
   */
  public final static String Empty = "";

  /**
   * 空格
   */
  public final static String BLANK_SPACE = " ";

  /**
   * 加号
   */
  public final static String PLUS = "+";

  /**
   * 横线变量
   */
  public final static String HORIZONTAL = "-";

  /**
   * 连接符
   */
  public final static String CONNECTOR = "&";

  /**
   * 下划线
   */
  public final static String UNDERLINE = "_";

  /**
   * 等于
   */
  public final static String EQUAL = "=";

  /**
   * 问号
   */
  public final static String QUESTION_MARK = "?";

  /**
   * 冒号
   */
  public final static String COLON = ":";

  /**
   * 斜线
   */
  public final static String DIAGONAL_LINE = "\\";

  /**
   * 反斜线
   */
  public final static String BACKSLASH = "/";

  /**
   * 空对像
   */
  public final static String NullObject = null;

  /**
   * 默认值
   */
  public final static String DEFAULT_VALUE = "cdkj_";

  /**
   * 0 值变量
   */
  public final static String ZERO = "0";
  /**
   * -1 值变量
   */
  public final static String NEGATIVE_ONE = "-1";
  /**
   * 逗号 值变量
   */
  public final static String COMMA = ",";
  /**
   * 分号 值变量
   */
  public final static String SEMICOLON = ";";
  /**
   * 点号 值变量
   */
  public final static String POINT = ".";
  /**
   * <p>The maximum size to which the padding constant(s) can expand.</p>
   */
  private static final int PAD_LIMIT = 8192;
  /**
   * 日志
   */
  private static LogUtils logUtil = LogUtils.getLogger(StringUtils.class);

  /**
   * 补0
   *
   * @param length 长度
   * @param value  值
   * @return 返回结果
   */
  public static String format(int length, long value) {
    final String FORMAT = "%" + ZERO + length + "d";
    return String.format(FORMAT, value);
  }

  /**
   * 验证是否为空
   *
   * @param obj 数据
   * @return 返回布尔值
   */
  public static boolean isNullAndSpaceOrEmpty(Object obj) {
    //验证是否为 null
    if (obj == null) {
      return true;
    }
    //验证是否为空
    return Empty.equals(obj.toString().trim());
    //返回结果
  }

  /**
   * 是否不为空
   *
   * @param obj 值
   * @return 返回结果
   */
  public static boolean isNotNullAndEmpty(Object obj) {
    return !isNullAndSpaceOrEmpty(obj);
  }

  /**
   * 包含指定
   *
   * @param str        原数据
   * @param searchChar 搜索字符
   * @return 返回结果
   */
  public static boolean contains(Object str, char searchChar) {
    if (isNullAndSpaceOrEmpty(str)) {
      return false;
    }
    return str.toString().indexOf(searchChar) >= IntegerConsts.ZERO;
  }

  /**
   * 包含指定
   *
   * @param str       原数据
   * @param searchStr 搜索字符
   * @return 返回结果
   */
  public static boolean contains(Object str, String searchStr) {
    if (isNullAndSpaceOrEmpty(str) || isNullAndSpaceOrEmpty(searchStr)) {
      return false;
    }
    return str.toString().indexOf(searchStr) >= IntegerConsts.ZERO;
  }

  /**
   * 将值转换为整数
   *
   * @param obj 值
   * @return 返回结果
   */
  public static int convertInt(Object obj) {
    int value = IntegerConsts.ZERO;
    try {
      if (!isNullAndSpaceOrEmpty(obj)) {
        value = Integer.valueOf(obj.toString());
      }
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }
    //返回结果
    return value;
  }

  /**
   * 转换为长整形
   *
   * @param obj 数据
   * @return 返回结果
   */
  public static Long convertLong(Object obj) {
    Long value = 0L;
    try {
      if (!isNullAndSpaceOrEmpty(obj)) {
        value = Long.valueOf(obj.toString());
      }
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }
    //返回结果
    return value;
  }

  /**
   * 将值转换为布尔
   *
   * @param obj 值
   * @return 返回结果
   */
  public static Boolean convertBoolean(Object obj) {
    Boolean value = false;
    try {
      int intValue = convertInt(obj);
      if (intValue > IntegerConsts.ZERO) {
        value = true;
      } else if (!isNullAndSpaceOrEmpty(obj)) {
        value = Boolean.valueOf(obj.toString());
      }
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }
    //返回结果
    return value;
  }

  /**
   * 类名格式
   *
   * @param clazzName 类型名
   * @return 返回结果
   */
  public static String classFormat(String clazzName) {
    //返回结果
    return classFormat(clazzName, UNDERLINE);
  }

  /**
   * 属性名
   *
   * @param clazzName 属性名称
   * @param splitter  分割符
   * @return 返回结果
   */
  public static String classFormat(String clazzName, String splitter) {
    //分割字符
    String[] clazzList = clazzName.split(splitter);

    StringBuilder builder = new StringBuilder();
    for (String name :
        clazzList) {
      builder.append(Character.toUpperCase(name.charAt(IntegerConsts.ZERO))).append(name.substring(IntegerConsts.ONE));
    }

    //返回结果
    return builder.toString();
  }

  /**
   * 属性名
   *
   * @param attributeName 属性名称
   * @return 返回结果
   */
  public static String attributeNameFormat(String attributeName) {
    return attributeNameFormat(attributeName, UNDERLINE);
  }

  /**
   * 属性名
   *
   * @param attributeName 属性名称
   * @param splitter      分割符
   * @return 返回结果
   */
  public static String attributeNameFormat(String attributeName, String splitter) {
    //分割字符
    String[] clazzList = attributeName.split(splitter);

    StringBuilder builder = new StringBuilder();
    for (int i = IntegerConsts.ZERO; i < clazzList.length; i++) {
      String name = clazzList[i];
      if (IntegerConsts.ZERO.equals(i)) {
        builder.append(Character.toLowerCase(name.charAt(IntegerConsts.ZERO))).append(name.substring(IntegerConsts.ONE));
        continue;
      }
      builder.append(Character.toUpperCase(name.charAt(IntegerConsts.ZERO))).append(name.substring(IntegerConsts.ONE));
    }

    //返回结果
    return builder.toString();
  }

  /**
   * 左边2
   *
   * @param str
   * @param size
   * @param padChar
   * @return 返回字符
   */
  public static String leftPad(String str, int size, char padChar) {
    if (str == null) {
      return null;
    }
    int pads = size - str.length();
    if (pads <= IntegerConsts.ZERO) {
      return str;
    }
    if (pads > PAD_LIMIT) {
      return leftPad(str, size, String.valueOf(padChar));
    }
    return padding(pads, padChar).concat(str);
  }

  /**
   * 内
   *
   * @param repeat
   * @param padChar
   * @return 返回字符
   * @throws IndexOutOfBoundsException 异常信息
   */
  private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
    if (repeat < IntegerConsts.ZERO) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
    }
    final char[] buf = new char[repeat];
    for (int i = IntegerConsts.ZERO; i < buf.length; i++) {
      buf[i] = padChar;
    }
    return new String(buf);
  }

  /**
   * 左边
   *
   * @param str    字符
   * @param size   大小
   * @param padStr 衬垫窜
   * @return 返回结果
   */
  public static String leftPad(String str, int size, String padStr) {
    if (str == null) {
      return null;
    }
    if (isNullAndSpaceOrEmpty(padStr)) {
      padStr = " ";
    }
    int padLen = padStr.length();
    int strLen = str.length();
    int pads = size - strLen;
    if (pads <= IntegerConsts.ZERO) {
      return str;
    }
    if (padLen == 1 && pads <= PAD_LIMIT) {
      return leftPad(str, size, padStr.charAt(IntegerConsts.ZERO));
    }

    if (pads == padLen) {
      return padStr.concat(str);
    } else if (pads < padLen) {
      return padStr.substring(IntegerConsts.ZERO, pads).concat(str);
    } else {
      char[] padding = new char[pads];
      char[] padChars = padStr.toCharArray();
      for (int i = IntegerConsts.ZERO; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return new String(padding).concat(str);
    }
  }

  /**
   * 字符串去除空白内容：
   * <ul>
   *     <li>\n 回车</li>
   *     <li>\t 水平制表符</li>
   *     <li>\s 空格</li>
   *     <li>\r 换行</li>
   * </ul>
   *
   * @param str 字符串
   */
  public static String replaceAllBlank(String str) {
    Matcher matcher = REPLACE_BLANK.matcher(str);
    return matcher.replaceAll("");
  }

  /**
   * 字符串驼峰转下划线格式
   *
   * @param param 需要转换的字符串
   * @return 转换好的字符串
   */
  public static String camelToUnderline(String param) {
    if (isNullAndSpaceOrEmpty(param)) {
      return StringConstant.EMPTY;
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c) && i > 0) {
        sb.append(UNDERLINE);
      }
      sb.append(Character.toLowerCase(c));
    }
    return sb.toString();
  }

  /**
   * 是否为CharSequence类型
   *
   * @param clazz class
   * @return true 为是 CharSequence 类型
   */
  public static boolean isCharSequence(Class<?> clazz) {
    return clazz != null && CharSequence.class.isAssignableFrom(clazz);
  }

  /**
   * 判断对象是否不为空
   *
   * @param object ignore
   * @return ignore
   */
  public static boolean checkValNotNull(Object object) {
    if (object instanceof CharSequence) {
      return isNotNullAndEmpty((CharSequence) object);
    }
    return object != null;
  }

  /**
   * 判断对象是否为空
   *
   * @param object ignore
   * @return ignore
   */
  public static boolean checkValNull(Object object) {
    return !checkValNotNull(object);
  }

  /**
   * 对象转为字符串去除左右空格
   *
   * @param o 带转换对象
   * @return
   */
  public static String toStringTrim(Object o) {
    return String.valueOf(o).trim();
  }

  /**
   * 首字母转换小写
   *
   * @param param 需要转换的字符串
   * @return 转换好的字符串
   */
  public static String firstToLowerCase(String param) {
    if (isNullAndSpaceOrEmpty(param)) {
      return EMPTY;
    }
    return param.substring(0, 1).toLowerCase() + param.substring(1);
  }
}
