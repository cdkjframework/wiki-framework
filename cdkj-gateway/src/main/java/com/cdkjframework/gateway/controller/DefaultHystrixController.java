package com.cdkjframework.gateway.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway
 * @ClassName: FallbackController
 * @Description: 后备控制器
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@RequestMapping("/gateway")
public class DefaultHystrixController {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(DefaultHystrixController.class);

    /**
     * 熔断返回结果
     *
     * @return 返回结果
     */
    @RequestMapping("/fallback")
    public ResponseBuilder fallback() {
        logUtils.error("触发熔断......");
        return ResponseBuilder.failBuilder();
    }
}
