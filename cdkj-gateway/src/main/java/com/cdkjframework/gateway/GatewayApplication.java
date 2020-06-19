package com.cdkjframework.gateway;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway
 * @ClassName: GatewayApplication
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.cdkjframework",
        "com.cdkjframework.core",
        "com.cdkjframework.redis"
}, exclude = {
        DataSourceAutoConfiguration.class,
})
@Configuration
@EnableDiscoveryClient
@EnableCircuitBreaker
public class GatewayApplication {

    /**
     * 启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
//        RouteDefinition routeDefinition = new RouteDefinition();
//        routeDefinition.setOrder(0);
//        routeDefinition.setUri(URI.create("lb://com.lesmarthome.bms"));
//        // 访问路径
//        List<PredicateDefinition> predicates = new ArrayList<>();
//        PredicateDefinition predicate = new PredicateDefinition();
//        predicate.setName("Path");
//        Map<String, String> map = new HashMap<>(IntegerConsts.THREE);
//        map.put("bms", "/bms/**");
//        map.put("rms", "/rms/**");
//        map.put("security", "/security/**");
//        predicate.setArgs(map);
//        predicates.add(predicate);
//        routeDefinition.setPredicates(predicates);
//        // 过滤
//        List<FilterDefinition> filters = new ArrayList<>();
//        FilterDefinition definition = new FilterDefinition();
//        definition.setName("Hystrix");
//        map = new HashMap<>(IntegerConsts.TWO);
//        map.put("name", "default");
//        map.put("fallbackUri", "forward:/gateway/fallback");
//        definition.setArgs(map);
//        filters.add(definition);
//        routeDefinition.setFilters(filters);
//        System.out.println(JsonUtils.objectToJsonString(routeDefinition));
    }
}
