package com.cdkjframework.cloud.ribbon.self;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList;

import java.util.List;

/**
 * @author xialin
 * RibbonDomainExtractingServerList
 */
public class RibbonDomainExtractingServerList extends DomainExtractingServerList {
    public RibbonDomainExtractingServerList(ServerList<DiscoveryEnabledServer> list, IClientConfig clientConfig, boolean approximateZoneFromHostname) {
        super(list, clientConfig, approximateZoneFromHostname);
    }

    @Override
    public List<DiscoveryEnabledServer> getInitialListOfServers() {
        System.out.println("RibbonDomainExtractingServerList getInitialListOfServers  ... ");
        return super.getInitialListOfServers();
    }

    @Override
    public List<DiscoveryEnabledServer> getUpdatedListOfServers() {
        System.out.println("RibbonDomainExtractingServerList getUpdatedListOfServers  ... ");
        return super.getUpdatedListOfServers();
    }
}
