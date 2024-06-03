package com.cdkjframework.cloud.ribbon;

import com.cdkjframework.cloud.common.RibbonConfigurationBasedServerList;
import com.cdkjframework.cloud.ribbon.self.RibbonPingUrl;
import com.cdkjframework.cloud.ribbon.self.RibbonRule;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.ribbon
 * @ClassName: CommonRibbonConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class CommonRibbonConfig {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(CommonRibbonConfig.class);

    /**
     * PING
     *
     * @return 返回结果
     */
    @Bean
    public IPing ribbonPing() {
        return new RibbonPingUrl(new NIWSDiscoveryPing());
    }

    /**
     * 规则
     *
     * @return 返回结果
     */
    @Bean
    public IRule ribbonRule() {
        return new RibbonRule();
    }

    /**
     * 功能区服务器列表
     *
     * @param config 配置
     * @return 服务列表
     */
    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config) {
        RibbonConfigurationBasedServerList.BazServiceList bazServiceList = new RibbonConfigurationBasedServerList.BazServiceList(config);
        logUtil.info(JsonUtils.objectToJsonString(bazServiceList));

        return bazServiceList;
    }

    /**
     * 服务列表过虑
     *
     * @return 返回过虑结果
     */
    @Bean
    public ServerListSubsetFilter serverListFilter() {
        ServerListSubsetFilter filter = new ServerListSubsetFilter();
        logUtil.info(JsonUtils.objectToJsonString(filter));
        return filter;
    }
}
