package com.cdkjframework.util.network.https;

import com.cdkjframework.config.TlsConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.util.https
 * @ClassName: SslClient
 * @Description: 用于进行Https请求的HttpClient
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@Order(10)
public class TlsPool extends HttpClientBuilder implements ApplicationRunner {

  /**
   * SSLContext 配置
   */
  @Autowired
  private TlsConfig config;

  /**
   * SSLContext 配置
   */
  private static TlsConfig tlsConfig;

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(TlsPool.class);

  /**
   * 初始化
   *
   * @param args 运行参数
   * @throws Exception 异常信息
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    tlsConfig = this.config;
  }

  /**
   * 构造函数
   */
  public static CloseableHttpClient createSslContext() {
    SSLContext ctx = null;
    try {
      if (StringUtils.isNotNullAndEmpty(tlsConfig.getFile()) &&
          StringUtils.isNotNullAndEmpty(tlsConfig.getPassword())) {
        ctx = SSLContexts
            .custom()
            .loadTrustMaterial(
                new File(tlsConfig.getFile()),
                tlsConfig.getPassword().toCharArray(),
                new TrustSelfSignedStrategy())
            .build();
      } else {
        ctx = new SSLContextBuilder()
            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
            .build();
      }
    } catch (Exception e) {
      logUtils.error(e.getCause(), e.getMessage());
    }
    assert ctx != null;
    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx);
    HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
        .setSSLSocketFactory(socketFactory)
        .build();

    // 返回构建结果
    return HttpClients.custom()
        .setConnectionManager(connectionManager)
        .build();
  }
}
