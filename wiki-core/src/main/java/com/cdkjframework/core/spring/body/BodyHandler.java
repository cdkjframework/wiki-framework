package com.cdkjframework.core.spring.body;

import com.cdkjframework.config.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.core.spring.body
 * @ClassName: BodyHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class BodyHandler {

    /**
     * 请求特殊参数
     */
    protected final String X_REQUESTED_TOKEN = "X-Requested-token";

    /**
     * 请求特殊参数值
     */
    protected final String X_REQUESTED_TOKEN_VALUE = "token";

    /**
     * 自定义配置
     */
    @Autowired
    protected CustomConfig customConfig;

    /**
     * 支持
     *
     * @param filter 过滤信息
     * @param name   名称
     * @return 返回结果
     */
    protected boolean supportsFilter(List<String> filter, String name) {
        if (CollectionUtils.isEmpty(filter)) {
            return true;
        }
        List<String> list = filter.stream()
                .filter(name::contains)
                .toList();
        return CollectionUtils.isEmpty(list);
    }

    /**
     * header验证
     *
     * @param httpHeaders http头部
     * @return 返回结果
     */
    protected boolean header(HttpHeaders httpHeaders) {
        if (!httpHeaders.containsKey(X_REQUESTED_TOKEN)) {
            return false;
        }
        return Objects.requireNonNull(httpHeaders.get(X_REQUESTED_TOKEN)).contains(X_REQUESTED_TOKEN_VALUE);
    }
}
