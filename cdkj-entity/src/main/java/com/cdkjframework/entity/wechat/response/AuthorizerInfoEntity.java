package com.cdkjframework.entity.wechat.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.wechat.response
 * @ClassName: AuthorizerInfoEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class AuthorizerInfoEntity {

    /**
     * 昵称
     */
    @JSONField(name = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @JSONField(name = "head_img")
    private String headImg;

    /**
     * 原始 ID
     */
    @JSONField(name = "user_name")
    private String userName;

    /**
     * 主体名称
     */
    @JSONField(name = "principal_name")
    private String principalName;

    /**
     * 公众号所设置的微信号，可能为空
     */
    @JSONField(name = "alias")
    private String alias;

    /**
     * 二维码地址
     */
    @JSONField(name = "qrcode_url")
    private String qrCodeUrl;

    /**
     * 公众号类型
     */
    @JSONField(name = "service_type_info")
    private Map<String, Object> serviceTypeInfo;

    /**
     * 公众号认证类型
     */
    @JSONField(name = "verify_type_info")
    private Map<String, Object> verifyTypeInfo;

    /**
     * 用以了解功能的开通状况（0代表未开通，1代表已开通）
     */
    @JSONField(name = "business_info")
    private BusinessInfo businessInfo;

    @Getter
    @Setter
    @ToString
    public class BusinessInfo {

        /**
         * 是否开通微信门店功能
         */
        @JSONField(name = "open_store")
        private Integer openStore;

        /**
         * 是否开通微信扫商品功能
         */
        @JSONField(name = "open_scan")
        private Integer openScan;

        /**
         * 是否开通微信支付功能
         */
        @JSONField(name = "open_pay")
        private Integer openPay;

        /**
         * 是否开通微信卡券功能
         */
        @JSONField(name = "open_card")
        private Integer openCard;

        /**
         * 是否开通微信摇一摇功能
         */
        @JSONField(name = "open_shake")
        private Integer openShake;
    }
}
