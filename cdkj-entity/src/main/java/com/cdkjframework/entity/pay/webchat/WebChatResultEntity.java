package com.cdkjframework.entity.pay.webchat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信异步回调结果通知
 * @author frank
 */
@XStreamAlias("xml")
@Getter
@Setter
@ToString
public class WebChatResultEntity {

    /**
     * SUCCESS/FAIL
     * SUCCESS表示商户接收通知成功并校验成功
     */
    @XStreamAlias("return_code")
    private String returnCode = "SUCCESS";

    /**
     * 返回信息
     */
    @XStreamAlias("return_msg")
    private String returnMsg = "OK";
}
