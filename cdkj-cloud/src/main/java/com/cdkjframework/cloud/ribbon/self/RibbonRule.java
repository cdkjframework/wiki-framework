package com.cdkjframework.cloud.ribbon.self;

import com.cdkjframework.util.log.LogUtils;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

/**
 * @ProjectName: com.cdkjframework.blog
 * @Package: com.cdkjframework.cloud.ribbon.self
 * @ClassName: RibbonRule
 * @Description: 权限过滤 逐个考察 Server，如果 Server 断路器打开，则忽略，再选择其中并发连接最低的 Server
 * RandomRule	                随机策略    	    随机选择 Server
 * RoundRobinRule	            轮训策略	        按顺序循环选择 Server
 * RetryRule	                重试策略	        在一个配置时问段内当选择 Server 不成功，则一直尝试选择一个可用的 Server
 * BestAvailableRule	        最低并发策略	    逐个考察 Server，如果 Server 断路器打开，则忽略，再选择其中并发连接最低的 Server
 * AvailabilityFilteringRule    可用过滤策略	    过滤掉一直连接失败并被标记为 circuit tripped 的 Server，过滤掉那些高并发连接的 Server（active connections 超过配置的网值）
 * ResponseTimeWeightedRule     响应时间加权策略	根据 Server 的响应时间分配权重。响应时间越长，权重越低，被选择到的概率就越低；响应时间越短，权重越高，被选择到的概率就越高。这个策略很贴切，综合了各种因素，如：网络、磁盘、IO等，这些因素直接影响着响应时间
 * ZoneAvoidanceRule	        区域权衡策略	    综合判断 Server 所在区域的性能和 Server 的可用性轮询选择 Server，并且判定一个 AWS Zone 的运行性能是否可用，剔除不可用的 Zone 中的所有 Server
 * ---------------------
 * 作者：大漠知秋
 * 来源：CSDN
 * 原文：https://blog.csdn.net/wo18237095579/article/details/83384134
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 * @Author: xiaLin
 * @Version: 1.0
 */
public class RibbonRule extends ZoneAvoidanceRule {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(RibbonRule.class);

    /**
     * 选择服务
     *
     * @param key
     * @return 返回结果
     */
    @Override
    public Server choose(Object key) {
        logUtil.info("RibbonRule choose " + key + " ... ");
        return super.choose(key);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        logUtil.info("RibbonRule - setLoadBalancer  ... ");
        super.setLoadBalancer(lb);
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        logUtil.info("RibbonRule - getLoadBalancer  ... ");
        return super.getLoadBalancer();
    }
}
