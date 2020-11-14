package com.cdkjframework.security.controller;

import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.make.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.controller
 * @ClassName: SecurityCodeController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/security")
@Api(tags = "验证生成接口")
public class SecurityCodeController {


    /**
     * 获取验证码
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException IO异常信息
     */
    @GetMapping(value = "/verify/code")
    @ApiOperation(value = "获取验证码")
    public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String code = VerifyCodeUtils.outputVerifyImage(IntegerConsts.ONE_HUNDRED, IntegerConsts.THIRTY,
                outputStream, IntegerConsts.FOUR);
        // 将图形验证码存入到session中
        request.getSession().setAttribute(BusinessConsts.IMAGE_CODE, code);
    }
}
