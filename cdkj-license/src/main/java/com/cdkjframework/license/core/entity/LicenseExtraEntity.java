package com.cdkjframework.license.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>自定义需要校验的License参数</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
@Data
@ApiModel(value = "自定义需要校验的License参数")
public class LicenseExtraEntity implements Serializable {

    private static final long serialVersionUID = 8600137500316662317L;

    /**
     * 是否认证ip
     */
    @ApiModelProperty(value = "是否认证ip")
    private boolean isIpCheck;

    /**
     * 可被允许的IP地址
     */
    @ApiModelProperty(value = "可被允许的IP地址")
    private List<String> ipAddress;

    /**
     * 是否认证mac
     */
    @ApiModelProperty(value = "是否认证mac")
    private boolean isMacCheck;

    /**
     * 可被允许的mac地址
     */
    @ApiModelProperty(value = "可被允许的mac地址")
    private List<String> macAddress;

    /**
     * 是否认证cpu序列号
     */
    @ApiModelProperty(value = "是否认证cpu序列号")
    private boolean isCpuCheck;

    /**
     * 可被允许的CPU序列号
     */
    @ApiModelProperty(value = "可被允许的CPU序列号")
    private String cpuSerial;

    /**
     * 是否认证主板号
     */
    @ApiModelProperty(value = "是否认证主板号")
    private boolean isBoardCheck;

    /**
     * 可被允许的主板序列号
     */
    @ApiModelProperty(value = "可被允许的主板序列号")
    private String mainBoardSerial;

    /**
     * 是否限制注册人数
     */
    @ApiModelProperty(value = "是否限制注册人数")
    private boolean isRegisterCheck;

    /**
     * 限制系统中可注册的人数
     */
    @ApiModelProperty(value = "限制系统中可注册的人数")
    private Long registerAmount;
}
