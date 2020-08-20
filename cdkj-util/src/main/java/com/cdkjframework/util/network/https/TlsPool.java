package com.cdkjframework.util.network.https;

import com.cdkjframework.config.TlsConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

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
     * @param args
     * @throws Exception
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
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx);

        // 返回构建结果
        return HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
    }
}
