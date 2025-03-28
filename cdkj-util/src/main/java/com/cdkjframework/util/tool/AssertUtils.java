package com.cdkjframework.util.tool;

import com.cdkjframework.exceptions.GlobalRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.util.tool
 * @ClassName: AssertUtils
 * @Description: 断言工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class AssertUtils {

  /**
   * 默认消息
   */
  private final static String IS_EMPTY_MESSAGE = "不能为空";

  /**
   * 对象断言
   *
   * @param t   实体
   * @param <T> 类型
   * @throws GlobalRuntimeException 异常信息
   */
  public static <T> void isEmptyMessage(T t) throws GlobalRuntimeException {
    isEmptyMessage(t, IS_EMPTY_MESSAGE);
  }

  /**
   * 对象断言
   *
   * @param key 验证字段
   * @throws GlobalRuntimeException 异常信息
   */
  public static void isEmptyMessage(String key) throws GlobalRuntimeException {
    isEmptyMessage(key, IS_EMPTY_MESSAGE);
  }

  /**
   * 对象断言
   *
   * @param key     验证字段
   * @param message 消息
   * @throws GlobalRuntimeException 异常信息
   */
  public static void isEmptyMessage(String key, String message) throws GlobalRuntimeException {
    if (StringUtils.isNullAndSpaceOrEmpty(message)) {
      message = IS_EMPTY_MESSAGE;
    }
    if (StringUtils.isNullAndSpaceOrEmpty(key)) {
      throw new GlobalRuntimeException(message);
    }
  }

  /**
   * 对象断言
   *
   * @param t   实体
   * @param <T> 类型
   * @throws GlobalRuntimeException 异常信息
   */
  public static <T> void isEmptyMessage(T t, String message) throws GlobalRuntimeException {
    if (StringUtils.isNullAndSpaceOrEmpty(message)) {
      message = IS_EMPTY_MESSAGE;
    }

    if (t == null) {
      throw new GlobalRuntimeException(message);
    }
  }


  /**
   * 断言这个 value 不为 empty
   * <p>为 empty 则抛异常</p>
   *
   * @param value   字符串
   * @param message 消息
   */
  public static void isNotEmptyMessage(String value, String message, Object... params) {
    isTrue(StringUtils.isNotNullAndEmpty(value), message, params);
  }


  /**
   * 断言这个 value 不为 empty
   * <p>为 empty 则抛异常</p>
   *
   * @param t       字符串
   * @param message 消息
   */
  public static <T> void isNotEmptyMessage(T t, String message, Object... params) {
    if (StringUtils.isNullAndSpaceOrEmpty(message)) {
      message = IS_EMPTY_MESSAGE;
    }

    if (t == null) {
      throw new GlobalRuntimeException(message);
    }
  }

  /**
   * 断言数据集是为空
   *
   * @param list 数据集
   * @throws GlobalRuntimeException 异常信息
   */
  public static void isListEmpty(List<?> list) throws GlobalRuntimeException {
    isListEmpty(list, IS_EMPTY_MESSAGE);
  }

  /**
   * 断言数据集是为空
   *
   * @param list 数据集
   * @throws GlobalRuntimeException 异常信息
   */
  public static void isListEmpty(List<?> list, String message) throws GlobalRuntimeException {
    if (StringUtils.isNullAndSpaceOrEmpty(message)) {
      message = IS_EMPTY_MESSAGE;
    }

    if (CollectionUtils.isEmpty(list)) {
      throw new GlobalRuntimeException(message);
    }
  }

  /**
   * 断言这个 boolean 为 true
   * <p>为 false 则抛出异常</p>
   *
   * @param expression boolean 值
   * @param message    消息
   */
  public static void isTrue(boolean expression, String message, Object... params) throws GlobalRuntimeException {
    if (!expression) {
      throw new GlobalRuntimeException(message);
    }
  }

  /**
   * 断言这个 boolean 为 false
   * <p>为 true 则抛出异常</p>
   *
   * @param expression boolean 值
   * @param message    消息
   */
  public static void isFalse(boolean expression, String message, Object... params) {
    isTrue(!expression, message, params);
  }
}
