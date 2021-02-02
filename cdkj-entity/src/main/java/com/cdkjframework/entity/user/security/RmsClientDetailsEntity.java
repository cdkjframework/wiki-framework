package com.cdkjframework.entity.user.security;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: com.lesmarthome.wms
 * @Package: com.lesmarthome.wms
 * @ClassName: RmsClientDetails
 * @Description:
 * @Author: DESKTOP-U0VVSVK
 * @Version: 1.0
 * @Entity
 */

@Getter
@Setter
@ToString
public class RmsClientDetailsEntity extends BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 客户ID
     */
    private String clientId;
    /**
     * 资源ID
     */
    private String resourceIds;
    /**
     * 客户密钥
     */
    private String clientSecret;
    /**
     * 范围
     */
    private String scope;
    /**
     * 授权的代理类型
     */
    private String authorizedGrantTypes;
    /**
     * 重定向Uri
     */
    private String redirectUri;
    /**
     * 权限
     */
    private String authorities;
    /**
     * 访问令牌有效性
     */
    private Integer accessTokenValidity;
    /**
     * 刷新令牌有效性
     */
    private Integer refreshTokenValidity;
    /**
     * 附加信息
     */
    private String additionalInformation;
    /**
     * 自动批准
     */
    private String autoApproveScopes;
}

