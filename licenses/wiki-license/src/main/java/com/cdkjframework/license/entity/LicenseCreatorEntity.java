package com.cdkjframework.license.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * License创建（生成）需要的参数
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.entity
 * @ClassName: LicenseCreatorEntity
 * @Description: License创建（生成）需要的参数
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Schema(name = "License创建（生成）需要的参数")
public class LicenseCreatorEntity implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;

    /**
     * 证书主题
     */
    @SchemaProperty(name = "证书主题")
    private String subject;

    /**
     * 私钥别名
     */
    @SchemaProperty(name = "私钥别名")
    private String privateAlias = "publicKey";

    /**
     * 私钥密码（需要妥善保管，不能让使用者知道）
     */
    @SchemaProperty(name = "私钥密码（需要妥善保管，不能让使用者知道）")
    private String keyPass;

    /**
     * 私钥库存储路径
     */
    @SchemaProperty(name = "私钥库存储路径")
    private String privateKeysStorePath = "/privateKey.store";

    /**
     * 访问私钥库的密码
     */
    @SchemaProperty(name = "访问私钥库的密码")
    private String storePass;

    /**
     * 证书生成路径
     */
    @SchemaProperty(name = "证书生成路径")
    private String licensePath;

    /**
     * 证书生效时间
     */
    @SchemaProperty(name = "证书生效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     */
    @SchemaProperty(name = "证书失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**
     * 有效期年限
     */
    @SchemaProperty(name = "有效期年限（默认为 0 年）")
    private Integer year;
    /**
     * 有效期限（月）
     */
    @SchemaProperty(name = "有效期年限（默认为 0 月）")
    private Integer month;

    /**
     * 用户类型
     */
    @SchemaProperty(name = "用户类型")
    private String consumerType = "user";

    /**
     * 用户数量
     */
    @SchemaProperty(name = "用户数量")
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    @SchemaProperty(name = "描述信息")
    private String description;

    /**
     * 额外的服务器硬件校验信息
     */
    @SchemaProperty(name = "额外的服务器硬件校验信息")
    private LicenseExtraEntity licenseCheck;

    /**
     * 证书下载地址 == 一旦证书create成功，这个值就会填充上
     */
    @SchemaProperty(name = "证书下载地址 == 一旦证书create成功，这个值就会填充上")
    private String licUrl;
}
