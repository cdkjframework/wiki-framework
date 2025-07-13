package com.cdkjframework.license.entity;

import de.schlichtherle.license.LicenseContent;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

/**
 * <p>License证书验证结果对象</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
/**
 * License证书验证结果对象
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.entity
 * @ClassName: LicenseResultEntity
 * @Description: License证书验证结果对象
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Schema(name = "License证书验证结果对象")
public class LicenseResultEntity {

  /**
   * 检验结果
   */
  @SchemaProperty(name = "检验结果")
  private Boolean result;
  /**
   * 附加信息
   */
  @SchemaProperty(name = "附加信息")
  private String message;
  /**
   * 证书内容
   */
  @SchemaProperty(name = "证书内容")
  private LicenseContent content;
  /**
   * 检验失败错误
   */
  @SchemaProperty(name = "检验失败错误")
  private Exception exception;

  /**
   * 构建函数
   */
  public LicenseResultEntity(String message, LicenseContent content) {
    this.result = true;
    this.message = message;
    this.content = content;
  }

  /**
   * 构建函数
   */
  public LicenseResultEntity(boolean result, String message, Exception exception) {
    this.result = result;
    this.message = message;
    this.exception = exception;
  }
}
