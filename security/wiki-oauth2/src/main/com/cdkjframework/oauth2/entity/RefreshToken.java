package com.cdkjframework.oauth2.entity;

import lombok.Data;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: GenerateController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class RefreshToken {

    /**
     * 刷新令牌
     */
    private String refreshToken;
}
