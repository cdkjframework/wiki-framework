package com.cdkjframework.cloud.ribbon.self;

import com.cdkjframework.util.log.LogUtils;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

/**
 * @author xialin
 * RibbonPingUrl
 */
public class RibbonPingUrl implements IPing {
    private IPing pingUrl;

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(RibbonPingUrl.class);

    public RibbonPingUrl(IPing ping) {
        this.pingUrl = ping;
    }

    /**
     * 是否可用
     *
     * @param server
     * @return
     */
    @Override
    public boolean isAlive(Server server) {
        boolean isAlive = pingUrl.isAlive(server);
        logUtil.info("RibbonPingUrl  " + server.getHostPort() + " isAlive = " + isAlive + "; info=" + server.toString());
        return isAlive;
    }
}
