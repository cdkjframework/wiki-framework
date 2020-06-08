package com.cdkjframework.gateway.service;

import com.cdkjframework.gateway.repository.RedisRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway.service
 * @ClassName: GatewayDynamicRouteService
 * @Description: 网关动态路由服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class GatewayDynamicRouteService implements ApplicationEventPublisherAware {

    /**
     * Redis路由定义库
     */
    @Resource
    private RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    /**
     * 应用程序事件发布服务器
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 增加路由
     *
     * @param routeDefinition
     * @return
     */
    public int add(RouteDefinition routeDefinition) {
        redisRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        return 1;
    }

    /**
     * 更新
     *
     * @param routeDefinition
     * @return
     */
    public int update(RouteDefinition routeDefinition) {
        redisRouteDefinitionRepository.delete(Mono.just(routeDefinition.getId()));
        redisRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        return 1;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public Mono<ResponseEntity<Object>> delete(String id) {
        return redisRouteDefinitionRepository.delete(Mono.just(id)).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
    }


    /**
     * 设置应用程序事件发布服务器
     *
     * @param applicationEventPublisher 应用程序事件发布服务器
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
