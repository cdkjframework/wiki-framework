package com.cdkjframework.entity.generate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.entity
 * @ClassName: OrderNumberEntity
 * @Description: 订单生成实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class OrderNumberEntity implements Serializable {

  /**
   * 构造函数 2
   *
   * @param prefix   前缀
   * @param length   长度
   * @param serialNo 序号
   */
  public OrderNumberEntity(String prefix, int length, int serialNo) {
    this.prefix = prefix;
    this.length = length;
    this.serialNo = serialNo;
  }

  /**
   * 构造函数 3
   *
   * @param serialNo 序号
   * @param noName   订单编号名称
   * @param ruleNo   规则
   * @param length   长度
   * @param prefix   前缀
   */
  public OrderNumberEntity(long serialNo, String noName, long ruleNo, int length, String prefix) {
    this.serialNo = serialNo;
    this.noName = noName;
    this.ruleNo = ruleNo;
    this.length = length;
    this.prefix = prefix;
  }

  /**
   * 获取下一个单号
   *
   * @return 返回结果
   */
  public synchronized String nextNo() {
    serialNo++;
    return leftPad(String.valueOf(serialNo), length, "0");
  }

  /**
   * 创建单号
   *
   * @return 返回结果
   */
  public String createNextNo() {
    return leftPad(String.valueOf(serialNo + 1), length, "0");
  }

  private static final long serialVersionUID = 5333907085312643274L;

  /**
   * 序号
   */
  private long serialNo;

  /**
   *
   */
  private String noName;

  /**
   * 规则号
   */
  private long ruleNo;

  /**
   * 长度
   */
  private int length;

  /**
   * 前缀
   */
  private String prefix;

  /**
   * <p>The maximum size to which the padding constant(s) can expand.</p>
   */
  private static final int PAD_LIMIT = 8192;


  /**
   * 左边
   *
   * @param character 字符
   * @param size      大小
   * @param padStr    衬垫窜
   * @return 返回结果
   */
  public static String leftPad(String character, int size, String padStr) {
    if (character == null) {
      return null;
    }
    if (padStr == null) {
      padStr = " ";
    }
    int padLength = padStr.length();
    int sizeLength = size - character.length();
    if (sizeLength <= 0) {
      return character;
    }
    if (padLength == 1 && sizeLength <= PAD_LIMIT) {
      if (character == null) {
        character = "";
      }
      return leftPad(character, size, padStr.charAt(0));
    }

    if (sizeLength < padLength) {
      return padStr.substring(0, sizeLength).concat(character);
    } else if (sizeLength == padLength) {
      return padStr.concat(character);
    } else {
      char[] padding = new char[sizeLength];
      char[] padChars = padStr.toCharArray();
      for (int i = 0; i < sizeLength; i++) {
        padding[i] = padChars[i % padLength];
      }
      return new String(padding).concat(character);
    }
  }

  /**
   * 左边2
   *
   * @param character 字符
   * @param size      大小
   * @param padChar   字节
   * @return 返回字符
   */
  public static String leftPad(String character, int size, char padChar) {
    int pads = size - character.length();
    if (pads <= 0) {
      return character;
    }
    if (pads > PAD_LIMIT) {
      return leftPad(character, size, String.valueOf(padChar));
    }
    return padding(pads, padChar).concat(character);
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
    if (repeat < 0) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
    }
    final char[] buf = new char[repeat];
    for (int i = 0; i < buf.length; i++) {
      buf[i] = padChar;
    }
    return new String(buf);
  }
}
