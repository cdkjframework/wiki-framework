package com.cdkjframework.cloud.config;

import com.cdkjframework.cloud.client.FeignClient;
import com.cdkjframework.cloud.service.FeignService;
import com.cdkjframework.constant.IntegerConsts;
import feign.Request;
import feign.Retryer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.cloud.config
 * @ClassName: FeignConfig
 * @Description: Feign 配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@RequiredArgsConstructor
public class FeignConfig extends FeignApiInterceptor {

  /**
   * 默认值：70s
   */
  private final int CONNECT_TIMEOUT_MILLIS = IntegerConsts.ONE_THOUSAND * IntegerConsts.SEVENTY,
      READ_TIMEOUT_MILLIS = CONNECT_TIMEOUT_MILLIS;

  /**
   * Feign服务接口
   */
  private final FeignService feignServiceImpl;

  /**
   * feign Retryer
   *
   * @return 返回 Retryer
   */
  @Bean
  public Retryer feignRetryer() {
    return new Retryer.Default(IntegerConsts.ONE_HUNDRED,
        TimeUnit.SECONDS.toMillis(IntegerConsts.TEN),
        IntegerConsts.THREE);
  }

  /**
   * feignOption
   *
   * @return 返回 Request.Options
   */
  @Bean
  public Request.Options feignOption() {
    return new Request.Options(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS, READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS, Boolean.TRUE);
  }

  /**
   * 默认不注入，如果yml配置里有 com.cdkjframework.cloud.client.FeignClient 才注入
   *
   * @return 返回结果
   * @throws NoSuchAlgorithmException 异常信息
   * @throws KeyManagementException   异常信息
   */
  @Bean
  @ConditionalOnProperty("com.cdkjframework.cloud.client.FeignClient")
  public FeignClient getClient() throws NoSuchAlgorithmException, KeyManagementException {
    // 忽略SSL校验
    /**
     * SLL Context
     */
    String sslContext = "SSL";
    SSLContext ctx = SSLContext.getInstance(sslContext);
    X509TrustManager tm = new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    };
    ctx.init(null, new TrustManager[]{tm}, null);
    return new FeignClient(ctx.getSocketFactory(), (hostname, sslSession) -> Boolean.TRUE, feignServiceImpl);
  }
}
