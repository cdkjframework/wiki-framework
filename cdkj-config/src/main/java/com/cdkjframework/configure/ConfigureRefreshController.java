package com.cdkjframework.configure;

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
//@RefreshScope
@RestController
@RequestMapping(value = "/configure")
public class ConfigureRefreshController {

    /**
     * 上下文刷新器 ZHONGYIDONG
     */
    private RefreshEndpoint refreshEndpoint;

    /**
     * 构造函数
     *
     * @param refreshEndpoint 刷新终结点
     */
//    public ConfigureRefreshController(RefreshEndpoint refreshEndpoint) {
//        this.refreshEndpoint = refreshEndpoint;
//    }

    /**
     * 刷新
     */
    @PostMapping(value = "/refresh")
    public void refresh() {
        if (refreshEndpoint != null) {
            refreshEndpoint.refresh();
        }
    }
}
