package com.cdkjframework.configure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.configure
 * @ClassName: ConfigureRefreshController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
//@ConditionalOnClass(RefreshEndpoint.class)
//@AutoConfigureAfter(RefreshAutoConfiguration.class)
@RequestMapping(value = "/configure")
public class ConfigureRefreshController {

    /**
     * 上下文刷新器
     */
    private final RefreshEndpoint refreshEndpoint;

    /**
     * 构造函数
     *
     * @param refreshEndpoint 上下文刷新器
     */
    public ConfigureRefreshController(RefreshEndpoint refreshEndpoint) {
        this.refreshEndpoint = refreshEndpoint;
    }

    /**
     * 刷新
     */
    @PostMapping(value = "/refresh")
    public void refresh() {
        refreshEndpoint.refresh();
    }
}
