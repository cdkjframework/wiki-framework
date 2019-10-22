package com.cdkjframework.cloud.ribbon.self;

import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.cloud.ribbon.self
 * @ClassName: RibbonDiscoveryEnabledNIWSServerList
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class RibbonDiscoveryEnabledNiwsServerList extends DiscoveryEnabledNIWSServerList {

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
