package com.cdkjframework.gateway.controller;

import com.cdkjframework.gateway.service.GatewayDynamicRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway.controller
 * @ClassName: GatewayDynamicRouteController
 * @Description: 网关动态路由控制器
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@RequestMapping("/gateway")
@Api("路由配置信息")
public class GatewayDynamicRouteController {


    /**
     * 网关动态路由服务
     */
    @Resource
    private GatewayDynamicRouteService gatewayDynamicRouteService;

    /**
     * 添加数据
     *
     * @param entity 路由实体
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增路由配置信息")
    public String create(@RequestBody RouteDefinition entity) {
        int result = gatewayDynamicRouteService.add(entity);
        return String.valueOf(result);
    }

    /**
     * 修改数据
     *
     * @param entity 路由实体
     * @return 返回结果
     */
    @PostMapping("/update")
    @ApiOperation("修改路由配置信息")
    public String update(@RequestBody RouteDefinition entity) {
        int result = gatewayDynamicRouteService.update(entity);
        return String.valueOf(result);
    }

    /**
     * 删除数据
     *
     * @param id ID
     * @return 返回执行结果
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除路由配置信息")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        return gatewayDynamicRouteService.delete(id);
    }
}
