package com.cdkjframework.cloud.common;

import com.cdkjframework.cloud.ribbon.DefaultRibbonConfig;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.cloud.ribbon.self
 * @ClassName: RibbonZonePreferenceServerListFilter
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
public class RibbonConfigurationBasedServerList {

    public static class BazServiceList extends ConfigurationBasedServerList {

        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }

    }
}
