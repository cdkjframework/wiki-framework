package com.cdkjframework.enums.pay;

import com.cdkjframework.enums.InterfaceEnum;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.enums.pay
 * @ClassName: WeChatEnums
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum WeChatEnums implements InterfaceEnum {

    INVALID_REQUEST("invalid_request", "参数错误"),

    NOAUTH("noauth", "商户无此接口权限"),

    NOTENOUGH("notenough", "余额不足"),

    ORDERPAID("orderpaid", "商户订单已支付"),

    ORDERCLOSED("orderclosed", "订单已关闭"),

    SYSTEMERROR("systemerror", "系统错误"),

    APPID_NOT_EXIST("appid_not_exist", "APPID不存在"),

    MCHID_NOT_EXIST("mchid_not_exist", "MCHID不存在"),

    APPID_MCHID_NOT_MATCH("appid_mchid_not_match", "appid和mch_id不匹配"),

    LACK_PARAMS("lack_params", "缺少参数"),

    OUT_TRADE_NO_USED("out_trade_no_used", "商户订单号重复"),

    SIGNERROR("signerror", "签名错误"),

    XML_FORMAT_ERROR("xml_format_error", "XML格式错误"),

    REQUIRE_POST_METHOD("require_post_method", "请使用post方法"),

    POST_DATA_EMPTY("post_data_empty", "post数据为空"),

    NOT_UTF8("not_utf8", "编码格式错误");

    private final String value;
    private final String text;

    /**
     * 构造
     *
     * @param value 值
     * @param text  名称
     */
    WeChatEnums(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取值
     *
     * @return 返回值
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取描述
     *
     * @return 返描述
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
        return null;
    }
}
