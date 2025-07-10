package com.cdkjframework.minio.enums;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.StringUtils;
import lombok.Getter;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.enums
 * @ClassName: ContentTypeEnums
 * @Description: 内容类型枚举
 * @Author: xiaLin
 * @Date: 2024/9/2 13:30
 * @Version: 1.0
 */
@Getter
public enum ContentTypeEnums {
  /**
   * 默认类型
   */
  DEFAULT("default", "application/octet-stream"),

  /**
   * jpg类型
   */
  JPG("jpg", "image/jpeg"),
  /**
   * tiff类型
   */
  TIFF("tiff", "image/tiff"),
  /**
   * gif类型
   */
  GIF("gif", "image/gif"),
  /**
   * jfif类型
   */
  JFIF("jfif", "image/jpeg"),
  /**
   * png类型
   */
  PNG("png", "image/png"),
  /**
   * tif类型
   */
  TIF("tif", "image/tiff"),
  /**
   * ico类型
   */
  ICO("ico", "image/x-icon"),
  /**
   * jpeg类型
   */
  JPEG("jpeg", "image/jpeg"),
  /**
   * wbmp类型
   */
  WBMP("wbmp", "image/vnd.wap.wbmp"),
  /**
   * fax类型
   */
  FAX("fax", "image/fax"),
  /**
   * net类型
   */
  NET("net", "image/pnetvue"),
  /**
   * jpe类型
   */
  JPE("jpe", "image/jpeg"),
  /**
   * rp类型
   */
  RP("rp", "image/vnd.rn-realpix"),
  /**
   * mp4类型
   */
  MP4("mp4", "video/mp4");

  /**
   * 文件名后缀
   * -- GETTER --
   * 获取文件后缀
   */
  private final String suffix;

  /**
   * 返回前端请求头中，Content-Type具体的值
   * -- GETTER --
   * 获取文件类型
   */
  private final String value;

  ContentTypeEnums(String suffix, String value) {
    this.suffix = suffix;
    this.value = value;
  }

  /**
   * 根据文件后缀，获取Content-Type
   *
   * @param suffix 文件后缀
   * @return 返回结果
   */
  public static String formContentType(String suffix) {
    if (StringUtils.isNullAndSpaceOrEmpty(suffix)) {
      return DEFAULT.getValue();
    }
    int beginIndex = suffix.lastIndexOf(StringUtils.POINT) + IntegerConsts.ONE;
    suffix = suffix.substring(beginIndex);
    for (ContentTypeEnums value : ContentTypeEnums.values()) {
      if (suffix.equalsIgnoreCase(value.getSuffix())) {
        return value.getValue();
      }
    }
    return DEFAULT.getValue();
  }

}
