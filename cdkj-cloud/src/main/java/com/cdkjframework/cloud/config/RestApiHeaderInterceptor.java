package com.cdkjframework.cloud.config;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.cloud.config
 * @ClassName: RestApiHeaderInterceptor
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class RestApiHeaderInterceptor implements ClientHttpRequestInterceptor {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RestApiHeaderInterceptor.class);

    /**
     * 头部内容
     */
    private List<String> headerNameList = Arrays.asList("User-Agent", "token", "X-Real-IP");

    /**
     * 处理restTemplate的请求拦截
     *
     * @param request   请求
     * @param body      请求内容
     * @param execution 请求异常信息
     * @return 响应
     * @throws IOException IO异常
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long startTime = System.currentTimeMillis();
        try {
            HttpHeaders headers = request.getHeaders();
            // 远程调用请求增加头部信息处理(简写代码如下)
            ClientHttpResponse response = execution.execute(request, body);

            if (response != null) {
                for (String key :
                        headerNameList) {
                    Object header = headers.get(key);
                    if (StringUtils.isNullAndSpaceOrEmpty(header)) {
                        continue;
                    }
                    response.getHeaders().add(key, header.toString());
                }
            }

            // 返回响应
            return response;
        } finally {
            logUtils.info("执行时间：" + (System.currentTimeMillis() - startTime));
        }
    }
}
