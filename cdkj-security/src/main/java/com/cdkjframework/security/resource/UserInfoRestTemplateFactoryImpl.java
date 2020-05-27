package com.cdkjframework.security.resource;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.resource
 * @ClassName: UserInfoRestTemplateFactoryImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserInfoRestTemplateFactoryImpl implements UserInfoRestTemplateFactory {

    @Override
    public OAuth2RestTemplate getUserInfoRestTemplate() {
        return null;
    }
}
