package com.cdkjframework.web.controller;

import com.cdkjframework.redis.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web
 * @ClassName: TestController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试")

public class TestController {

    @RequestMapping("/getInteger")
    @ApiOperation("获取值")
    @ResponseBody
    public Integer getInteger(@RequestBody String id) {
        RedisUtils.publish("test", id);
        return Integer.valueOf(id);
    }
}
