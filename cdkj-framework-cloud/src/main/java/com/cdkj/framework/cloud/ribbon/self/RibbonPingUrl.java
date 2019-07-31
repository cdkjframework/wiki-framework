package com.cdkj.framework.cloud.ribbon.self;

import com.cdkj.framework.util.log.LogUtil;
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
    private LogUtil logUtil = LogUtil.getLogger(RibbonPingUrl.class);

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
