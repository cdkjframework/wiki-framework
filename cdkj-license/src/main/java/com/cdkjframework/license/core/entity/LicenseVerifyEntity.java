package com.cdkjframework.license.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>License校验类需要的参数</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
@Data
@ApiModel(value = "License校验类需要的参数")
public class LicenseVerifyEntity {

    /**
     * 证书主题
     */
    @ApiModelProperty(value = "证书主题")
    private String subject;

    /**
     * 公钥别名
     */
    @ApiModelProperty(value = "公钥别名")
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    @ApiModelProperty(value = "访问公钥库的密码")
    private String storePass;

    /**
     * 证书生成路径
     */
    @ApiModelProperty(value = "证书生成路径")
    private String licensePath;

    /**
     * 公钥库存储路径
     */
    @ApiModelProperty(value = "公钥库存储路径")
    private String publicKeysStorePath;
}
