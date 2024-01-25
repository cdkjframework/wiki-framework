package com.cdkjframework.util.tool;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ByteUtils
 * @Description: 字节工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ByteUtils {

  /**
   * 单位
   */
  private static final int UNIT = 1024;

  /**
   * 将字节转换为整数
   *
   * @param bytes 字节
   * @return 返回整数
   */
  public static int toInt(byte[] bytes) {
    int result = 0;
    for (int i = 0; i < 4; i++) {
      result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
    }
    return result;
  }

  /**
   * 格式化字节大小
   *
   * @param byteSize 字节大小
   * @return {@link String}
   */
  public static String formatByteSize(long byteSize) {

    if (byteSize <= -1) {
      return String.valueOf(byteSize);
    }

    double size = 1.0 * byteSize;

    String type;
    if ((int) Math.floor(size / UNIT) <= 0) { // 不足1KB
      type = "B";
      return format(size, type);
    }

    size = size / UNIT;
    if ((int) Math.floor(size / UNIT) <= 0) { // 不足1MB
      type = "KB";
      return format(size, type);
    }

    size = size / UNIT;
    if ((int) Math.floor(size / UNIT) <= 0) { // 不足1GB
      type = "MB";
      return format(size, type);
    }

    size = size / UNIT;
    if ((int) Math.floor(size / UNIT) <= 0) { // 不足1TB
      type = "GB";
      return format(size, type);
    }

    size = size / UNIT;
    if ((int) Math.floor(size / UNIT) <= 0) { // 不足1PB
      type = "TB";
      return format(size, type);
    }

    size = size / UNIT;
    if ((int) Math.floor(size / UNIT) <= 0) {
      type = "PB";
      return format(size, type);
    }
    return ">PB";
  }

  /**
   * 格式化字节大小为指定单位
   *
   * @param size 字节大小
   * @param type 单位类型
   * @return {@link String}
   */
  private static String format(double size, String type) {
    int precision;
    if (size * 100 % 10 > 0) {
      precision = 2;
    } else if (size * 10 % 10 > 0) {
      precision = 1;
    } else {
      precision = 0;
    }

    String formatStr = "%." + precision + "f";

    if ("KB".equals(type)) {
      return String.format(formatStr, (size)) + "KB";
    } else if ("MB".equals(type)) {
      return String.format(formatStr, (size)) + "MB";
    } else if ("GB".equals(type)) {
      return String.format(formatStr, (size)) + "GB";
    } else if ("TB".equals(type)) {
      return String.format(formatStr, (size)) + "TB";
    } else if ("PB".equals(type)) {
      return String.format(formatStr, (size)) + "PB";
    }
    return String.format(formatStr, (size)) + "B";
  }
}
