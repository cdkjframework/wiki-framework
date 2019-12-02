package com.cdkjframework.util.network.https;

import com.cdkjframework.config.TlsConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
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
                ctx = SSLContext.getInstance("TLSv1.2");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logUtils.error(e.getCause(),e.getMessage());
        }
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx,
                new String[]{"TLSv1", "TLSv2", "TLSv3"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        // Create a registry of custom CONNECTION socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", socketFactory)
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the CONNECTION manager.
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(10);
        // 个性化设置某个url的连接
        connManager.setMaxPerRoute(new HttpRoute(new HttpHost("www.chengdukeji.com",
                80)), 20);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
                .build();

        //返回结果
        return httpclient;
    }
}
