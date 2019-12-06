package com.cdkjframework.entity.pay.webchat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信异步回调结果通知
 */
@XStreamAlias("xml")
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

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
