package com.cdkjframework.enums;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.enums
 * @ClassName: FileTypeEnums
 * @Description: 文件类型 魔术数字 枚举
 * @Author: xiaLin
 * @Date: 2024/4/22 10:19
 * @Version: 1.0
 */
public enum FileTypeEnums implements InterfaceEnum {
  /**
   * 未知道设备
   */
  UNKNOWN("unknown", "unknown", "unknown"),

  /**
   * 音频
   */
  GZ("GZ", "GZ", "GZ"),
  /**
   * 图片
   */
  PGP_4("PGP_4", "PGP_4", "PGP_4"),
  /**
   * 图片 BMP
   */
  BMP("BMP", "BMP", "BMP"),
  /**
   * 图片
   */
  ITC("ITC", "ITC", "ITC"),
  /**
   * 图片
   */
  XPM("XPM", "XPM", "XPM"),
  /**
   * 图片
   */
  GKS("GKS", "GKS", "GKS"),
  /**
   * 图片
   */
  NIF("NIF", "NIF", "NIF"),
  /**
   * 图片
   */
  PM("PM", "PM", "PM"),
  /**
   * 图片
   */
  PS("PS", "PS", "PS"),
  /**
   * 图片
   */
  FITS("FITS", "FITS", "FITS"),
  /**
   * 图片
   */
  RGB("RGB", "RGB", "RGB"),
  /**
   * 图片
   */
  FIG("FIG", "FIG", "FIG"),
  /**
   * 图片
   */
  TAR("TAR", "TAR", "TAR"),
  /**
   * 图片
   */
  Z("Z", "Z", "Z"),
  /**
   * 图片
   */
  BZ("BZ", "BZ", "BZ"),
  /**
   * 图片
   */
  PGP_3("PGP_3", "PGP_3", "PGP_3"),
  /**
   * PNG
   */
  PNG("PNG", "PNG", "PNG"),
  /**
   * PGP_2
   */
  PGP_2("PGP_2", "PGP_2", "PGP_2"),
  /**
   * PGP
   */
  PGP("PGP", "PGP", "PGP"),
  /**
   * PGP_1
   */
  PGP_1("PGP_1", "PGP_1", "PGP_1"),
  /**
   * ZIP
   */
  ZIP("ZIP", "ZIP", "ZIP"),
  /**
   * PGP_2
   */
  ELF("ELF", "ELF", "ELF"),
  /**
   * TIF_2
   */
  TIF_2("TIF_2", "TIF_2", "TIF_2"),
  /**
   * TIF
   */
  TIF("TIF", "TIF", "TIF"),
  /**
   * RAS
   */
  RAS("RAS", "RAS", "RAS"),
  /**
   * JPG
   */
  JPG("JPG", "JPG", "JPG"),
  /**
   * GIF
   */
  GIF("GIF", "GIF", "GIF"),
  /**
   * XCF
   */
  XCF("XCF", "XCF", "XCF"),
  /**
   * MZ
   */
  MZ("MZ", "MZ", "MZ"),
  /**
   * TIF_1
   */
  TIF_1("TIF_1", "TIF_1", "TIF_1"),
  /**
   * PGP_2
   */
  GZIP("GZIP", "GZIP", "GZIP"),
  /**
   * PGP_0
   */
  PGP_0("PGP_0", "PGP_0", "PGP_0"),
  /**
   * ELF_1
   */
  ELF_1("ELF_1", "ELF_1", "ELF_1");

  /**
   * 值
   */
  private final String value;
  /**
   * 说明
   */
  private final String text;

  /**
   * 节点值
   */
  private final String node;

  /**
   * 构造函数
   */
  FileTypeEnums(String value, String text, String node) {
    this.value = value;
    this.text = text;
    this.node = node;
  }

  /**
   * 获取值
   *
   * @return 返回值
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * 获取描述
   *
   * @return 返描述
   */
  @Override
  public String getText() {
    return text;
  }

  /**
   * 获取下节点值
   *
   * @return 返下节点值
   */
  @Override
  public String getNode() {
    return node;
  }

  /**
   * 通过值获取文件类型
   *
   * @param value 值
   * @return 返回类型
   */
  public static FileTypeEnums formByValue(String value) {
    for (FileTypeEnums fileType : FileTypeEnums.values()) {
      if (fileType.getValue().equals(value)) {
        return fileType;
      }
    }
    return FileTypeEnums.UNKNOWN;
  }
}
