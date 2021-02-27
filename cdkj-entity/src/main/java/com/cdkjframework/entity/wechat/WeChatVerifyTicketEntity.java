package com.cdkjframework.entity.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.wechat
 * @ClassName: WeChatVerifyTicketEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@XStreamAlias("xml")
public class WeChatVerifyTicketEntity {

    /**
     * 第三方平台 appid
     */
    @XStreamAlias("AppId")
    private String appId;

    /**
     * 创建时间 时间戳，单位：s
     */
    @XStreamAlias("CreateTime")
    private long createTime;

    /**
     * 固定为：component_verify_ticket"
     */
    @XStreamAlias("InfoType")
    private String infoType;

    /**
     * Ticket 内容
     */
    @XStreamAlias("ComponentVerifyTicket")
    private String verifyTicket;

    /**
     * 主键
     */
    private String id;
}
