package com.cdkjframework.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.wechat
 * @ClassName: EncryptionEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@XStreamAlias("xml")
public class EncryptionEntity {

    /**
     * 第三方平台 appid
     */
    @XStreamAlias("AppId")
    private String appId;

    /**
     * 加密内容
     */
    @XStreamAlias("Encrypt")
    private String encrypt;
}
