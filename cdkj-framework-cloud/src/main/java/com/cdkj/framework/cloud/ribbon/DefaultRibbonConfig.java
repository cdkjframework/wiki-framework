package com.cdkj.framework.cloud.ribbon;

import com.cdkj.framework.cloud.ribbon.self.RibbonPingUrl;
import com.cdkj.framework.cloud.ribbon.self.RibbonRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.cloud.netflix.ribbon.ZonePreferenceServerListFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xialin
 * DefaultRibbonConfig
 */
@Configuration
public class DefaultRibbonConfig {

    /**
     * 区域首选项服务器列表筛选器
     *
     * @return 返回结果
     */
    @Bean
    public ZonePreferenceServerListFilter serverListFilter() {
        ZonePreferenceServerListFilter filter = new ZonePreferenceServerListFilter();
        filter.setZone("cdkj.RibbonZone");
        return filter;
    }

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
}
