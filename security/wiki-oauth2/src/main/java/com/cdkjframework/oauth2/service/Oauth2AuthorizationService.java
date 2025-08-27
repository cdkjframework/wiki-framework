package com.cdkjframework.oauth2.service;

import com.cdkjframework.builder.ResponseBuilder;

/**
 * OAuth2授权服务接口
 * 
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.service
 * @ClassName: Oauth2AuthorizationService
 * @Description: OAuth2授权服务接口
 * @Author: xiaLin
 * @Date: 2025/7/31 21:15
 * @Version: 1.0
 */
public interface Oauth2AuthorizationService {

    /**
     * 授权端点
     *
     * @param clientId     客户端ID
     * @param responseType 响应类型
     * @param scope        授权范围
     * @return 授权页面或信息
     */
    ResponseBuilder authorizationCode(String clientId, String responseType, String scope);

    /**
     * 获取访问令牌
     *
     * @param code      授权码
     * @param timestamp 时间戳
     * @param signature 签名
     * @param clientId  客户端ID
     * @param grantType 授权类型
     * @return 访问令牌
     */
    ResponseBuilder accessToken(String code, String timestamp, String signature, String clientId,
            String grantType);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 刷新后的访问令牌
     */
    ResponseBuilder refreshToken(String refreshToken);
}
