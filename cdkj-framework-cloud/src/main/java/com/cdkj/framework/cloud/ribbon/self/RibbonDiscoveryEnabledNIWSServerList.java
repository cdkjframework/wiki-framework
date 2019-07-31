package com.cdkj.framework.cloud.ribbon.self;

import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.List;

/**
 * @author xialin
 * RibbonDiscoveryEnabledNIWSServerList
 */
public class RibbonDiscoveryEnabledNIWSServerList extends DiscoveryEnabledNIWSServerList {

    @Override
    public List<DiscoveryEnabledServer> getInitialListOfServers() {
        System.out.println("RibbonDiscoveryEnabledNIWSServerList getInitialListOfServers  ... ");
        return super.getInitialListOfServers();
    }

    @Override
    public List<DiscoveryEnabledServer> getUpdatedListOfServers(){
        System.out.println("RibbonDiscoveryEnabledNIWSServerList getUpdatedListOfServers  ... ");
        return super.getUpdatedListOfServers();
    }
}
