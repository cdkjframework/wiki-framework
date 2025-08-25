package com.cdkjframework.oauth2.service;

/**
 * OAuth2令牌服务接口
 * 
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: Oauth2TokenService
 * @Description: OAuth2令牌服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface Oauth2TokenService {

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @throws IllegalArgumentException 如果令牌无效
     */
    default void validateToken(String token) {
        // 默认实现：可以在这里添加通用的令牌验证逻辑
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
    }
}
