package com.cdkjframework.security.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.security.configure
 * @ClassName: AuthorizationServerConfigurer
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2022/12/14 21:48
 * @Version: 1.0
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

  /**
   * 配置信息
   */
  private final SecurityConfig securityConfig;

  /**
   * 令牌服务
   *
   * @return 返回结果
   */
  public DefaultTokenServices tokenService() {
    DefaultTokenServices tokenService = new DefaultTokenServices();
    return tokenService;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//    endpoints.tokenServices()
    super.configure(endpoints);
  }
}
