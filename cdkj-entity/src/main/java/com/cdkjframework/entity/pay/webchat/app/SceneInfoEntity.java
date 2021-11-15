package com.cdkjframework.entity.pay.webchat.app;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.pay.webchat.app
 * @ClassName: SceneInfoEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SceneInfoEntity {

    /**
     * 门店id
     */
    private String id;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 门店行政区划码
     */
    @XStreamAlias("area_code")
    private String areaCode;
    /**
     * 门店详细地址
     */
    private String address;

    /**
     * 用户终端IP
     */
    @JSONField(name = "payer_client_ip")
    private String payerClientIp;

    /**
     * 用户终端IP
     */
    @JSONField(name = "device_id")
    private String deviceId;
}
