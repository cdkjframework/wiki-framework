package com.cdkjframework.login.controller;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.make.VerifyCodeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.login.controller
 * @ClassName: LoginController
 * @Description: 用户登录接口
 * @Author: xiaLin
 * @Date: 2022/10/6 10:56
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/security")
public class LoginController {

    /**
     * 编码字符
     */
    private final String CODE = "code";

    /**
     * 获取验证码
     */
    @ResponseBody
    @RequestMapping("/verify/code")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        // 验证码
        String code = GeneratedValueUtils.getRandomCharacter(IntegerConsts.FOUR);
        HttpSession session = request.getSession();
        session.setAttribute(CODE, code);

        // 响应数据
        try {
            OutputStream outputStream = response.getOutputStream();
            VerifyCodeUtils.outputImage(IntegerConsts.ONE_HUNDRED, IntegerConsts.THIRTY, outputStream, code);
        } catch (IOException e) {
            new GlobalRuntimeException("生成验证码异常！");
        }
    }

    public void login(){

    }
}
