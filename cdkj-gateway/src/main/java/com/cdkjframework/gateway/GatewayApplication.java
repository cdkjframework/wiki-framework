package com.cdkjframework.gateway;

import com.cdkjframework.util.tool.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.HashMap;
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
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setOrder(0);
        routeDefinition.setUri(URI.create("lb://com.lesmarthome.bms"));
        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");
        Map<String, String> map = new HashMap<>();
        map.put("Path", "/bms/**");
        predicate.setArgs(map);
        routeDefinition.getPredicates().add(predicate);
        System.out.println(JsonUtils.objectToJsonString(routeDefinition));
    }
}
