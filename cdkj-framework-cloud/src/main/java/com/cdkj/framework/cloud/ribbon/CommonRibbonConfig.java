package com.cdkj.framework.cloud.ribbon;

import com.cdkj.framework.cloud.common.RibbonConfigurationBasedServerList;
import com.cdkj.framework.cloud.ribbon.self.RibbonPingUrl;
import com.cdkj.framework.cloud.ribbon.self.RibbonRule;
import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.JsonUtil;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.cloud.ribbon
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
    private LogUtil logUtil = LogUtil.getLogger(CommonRibbonConfig.class);

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
        logUtil.info(JsonUtil.objectToJsonString(bazServiceList));

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
        logUtil.info(JsonUtil.objectToJsonString(filter));
        return filter;
    }
}
