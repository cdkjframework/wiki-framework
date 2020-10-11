package com.cdkjframework.configure;

import org.springframework.cloud.context.refresh.ContextRefresher;
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
@RequestMapping(value = "/configure")
public class ConfigureRefreshController {

    /**
     * 上下文刷新器
     */
    private final ContextRefresher contextRefresher;

    /**
     * 构造函数
     *
     * @param contextRefresher 上下文刷新器
     */
    public ConfigureRefreshController(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    /**
     * 刷新
     */
    @PostMapping(value = "/refresh")
    public void refresh() {
        contextRefresher.refreshEnvironment();
    }

}
