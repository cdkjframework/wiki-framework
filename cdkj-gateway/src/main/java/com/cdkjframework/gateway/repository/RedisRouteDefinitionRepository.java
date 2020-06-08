package com.cdkjframework.gateway.repository;

import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway.repository
 * @ClassName: RedisRouteDefinitionRepository
 * @Description: Redis路由定义库
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    /**
     * hash存储的key
     */
    public static final String GATEWAY_ROUTES = "gateway_dynamic_route";

    /**
     * 获取路由信息
     *
     * @return 返回路由信息
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = getRoute();
        return Flux.fromIterable(routeDefinitions);
    }

    /**
     * 保存路由
     *
     * @param route 路由
     * @return 返回结果
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        List<RouteDefinition> routeDefinitions = getRoute();
        Mono<Void> mono = route.flatMap(routeDefinition -> {
            routeDefinitions.add(routeDefinition);
            return Mono.empty();
        });

        String routes = JsonUtils.objectToJsonString(routeDefinitions);
        RedisUtils.syncSet(GATEWAY_ROUTES, routes);

        // 返回结果
        return mono;
    }

    /**
     * 删除路由
     *
     * @param routeId 路由ID
     * @return 返回结果
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        List<RouteDefinition> routeDefinitions = getRoute();
        List<RouteDefinition> routeList = new ArrayList<>();
        Mono<Void> mono = routeId.flatMap(id -> {
            Optional<RouteDefinition> optional = routeDefinitions.stream()
                    .filter(f -> f.getId().equals(id))
                    .findFirst();
            if (optional.isPresent()) {
                RouteDefinition routeDefinition = optional.get();
                routeList.add(routeDefinition);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("route definition is not found, routeId:" + routeId)));
        });
        // 删除指定数据
        routeDefinitions.removeAll(routeList);
        String routes = JsonUtils.objectToJsonString(routeDefinitions);
        RedisUtils.syncSet(GATEWAY_ROUTES, routes);
        // 返回结果
        return mono;
    }

    /**
     * 读取路由
     *
     * @return 返回结果
     */
    private List<RouteDefinition> getRoute() {
        String routes = RedisUtils.syncGet(GATEWAY_ROUTES);
        if (StringUtils.isNotNullAndEmpty(routes)) {
            return JsonUtils.jsonStringToList(routes, RouteDefinition.class);
        } else {
            return new ArrayList<>();
        }
    }
}
