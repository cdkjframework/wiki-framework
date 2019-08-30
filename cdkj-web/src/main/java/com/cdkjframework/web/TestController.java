package com.cdkjframework.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web
 * @ClassName: TestController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@RequestMapping("/test")
@Api(tags = "测试")
@RestController
public class TestController {

    @RequestMapping("/getInteger")
    @ApiOperation("获取值")
    public Integer getInteger() {
        return 0;
    }
}
