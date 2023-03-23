package com.cdkjframework.license.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>License创建（生成）需要的参数</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
@Data
@ApiModel(value = "License创建（生成）需要的参数")
public class LicenseCreatorEntity implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;

    /**
     * 证书主题
     */
    @ApiModelProperty(value = "证书主题")
    private String subject;

    /**
     * 私钥别名
     */
    @ApiModelProperty(value = "私钥别名")
    private String privateAlias = "publicKey";

    /**
     * 私钥密码（需要妥善保管，不能让使用者知道）
     */
    @ApiModelProperty(value = "私钥密码（需要妥善保管，不能让使用者知道）")
    private String keyPass;

    /**
     * 私钥库存储路径
     */
    @ApiModelProperty(value = "私钥库存储路径")
    private String privateKeysStorePath = "/privateKey.store";

    /**
     * 访问私钥库的密码
     */
    @ApiModelProperty(value = "访问私钥库的密码")
    private String storePass;

    /**
     * 证书生成路径
     */
    @ApiModelProperty(value = "证书生成路径")
    private String licensePath;

    /**
     * 证书生效时间
     */
    @ApiModelProperty(value = "证书生效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     */
    @ApiModelProperty(value = "证书失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**
     * 有效期年限
     */
    @ApiModelProperty(value = "有效期年限（默认为 1 年）")
    private Integer year = 1;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private String consumerType = "user";

    /**
     * 用户数量
     */
    @ApiModelProperty(value = "用户数量")
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    @ApiModelProperty(value = "描述信息")
    private String description;

    /**
     * 额外的服务器硬件校验信息
     */
    @ApiModelProperty(value = "额外的服务器硬件校验信息")
    private LicenseExtraEntity licenseCheck;

    /**
     * 证书下载地址 == 一旦证书create成功，这个值就会填充上
     */
    @ApiModelProperty(value = "证书下载地址 == 一旦证书create成功，这个值就会填充上")
    private String licUrl;
}
