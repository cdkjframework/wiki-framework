package com.cdkjframework.license.entity;

import de.schlichtherle.license.LicenseContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>License证书验证结果对象</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
@Data
@ApiModel(value = "License证书验证结果对象")
public class LicenseResultEntity {

    /**
     * 检验结果
     */
    @ApiModelProperty(value = "检验结果")
    private Boolean result;
    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String message;
    /**
     * 证书内容
     */
    @ApiModelProperty(value = "证书内容")
    private LicenseContent content;
    /**
     * 检验失败错误
     */
    @ApiModelProperty(value = "检验失败错误")
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
