package com.cdkjframework.center.controller;

import com.cdkjframework.core.controller.WebUiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller
 * @ClassName: VersionController
 * @Description: 版本控制器
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
public class VersionController extends WebUiController {

    /**
     * 获取程序版本信息
     *
     * @param request HttpServletRequest
     */
    @Override
    @GetMapping("/")
    public void version(HttpServletRequest request) {
        super.version(request);
    }
}
