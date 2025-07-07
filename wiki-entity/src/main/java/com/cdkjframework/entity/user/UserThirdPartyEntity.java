package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity.user
 * @ClassName: UserThirdPartyEntity
 * @Description: 用户三方登录信息
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserThirdPartyEntity extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录类型 (wechat,weibo,qq)
     */
    private String loginType;

    /**
     * 受权appId
     */
    private String appId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 原始 ID
     */
    private String userName;

    /**
     * 主体名称
     */
    private String principalName;

    /**
     * 公众号所设置的微信号，可能为空
     */
    private String alias;

    /**
     * 二维码地址
     */
    private String qrCodeUrl;

    /**
     * 公众号类型
     */
    private Map<String, Object> serviceTypeInfo;

    /**
     * 公众号认证类型
     */
    private Map<String, Object> verifyTypeInfo;

    /**
     * 用以了解功能的开通状况（0代表未开通，1代表已开通）
     */
    private BusinessInfo businessInfo;

    @Getter
    @Setter
    @ToString
    public class BusinessInfo {

        /**
         * 是否开通微信门店功能
         */
        private Integer openStore;

        /**
         * 是否开通微信扫商品功能
         */
        private Integer openScan;

        /**
         * 是否开通微信支付功能
         */
        private Integer openPay;

        /**
         * 是否开通微信卡券功能
         */
        private Integer openCard;

        /**
         * 是否开通微信摇一摇功能
         */
        private Integer openShake;
    }
}
