package com.cdkjframework.security.server.jwt;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.security.UserDetailsEntity;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.server.jwt
 * @ClassName: LoginFilter
 * @Description: 登录过虑
 * @Author: xiaLin
 * @Version: 1.0
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 身份验证管理器
     */
    private AuthenticationManager authenticationManager;

    /**
     * 构造函数
     *
     * @param authenticationManager 身份验证管理器
     */
    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    /**
     * 接收并解析用户凭证，出現错误时，返回json数据前端
     *
     * @param request  请求
     * @param response 响应
     * @return 返回权限验证结果
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDetailsEntity user = new ObjectMapper().readValue(request.getInputStream(), UserDetailsEntity.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (Exception e) {
            try {
                //未登錄出現賬號或密碼錯誤時，使用json進行提示
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                ResponseBuilder builder = ResponseBuilder.successBuilder();
                builder.setCode(HttpServletResponse.SC_UNAUTHORIZED);
                builder.setMessage("账号或密码错误！");
                out.write(JsonUtils.objectToJsonString(builder));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户登录成功后，生成 token,并且返回 json 数据给前端
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤链
     * @param auth     身份验证
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        // 用户信息
        UserDetailsEntity userDetails = (UserDetailsEntity) auth.getPrincipal();

        // 生成 JWT token
        Map<String, Object> map = new HashMap<>(IntegerConsts.FOUR);
        map.put("username", userDetails.getUsername());
        long effective = 24 * 60 * 60;
        map.put("effective", effective);
        long time = System.currentTimeMillis();
        map.put("time", time / 1000);
        String userAgent = HttpServletUtils.getRequest().getHeader(com.cdkjframework.consts.HttpHeaderConsts.USER_AGENT);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("username=%s&effective=%s&time=%s&userAgent=%s", userDetails.getUsername(), map.get("effective"), time, userAgent));
        String token = Md5Utils.getMd5(builder.toString());
        map.put("token", token);

        //json web token构建
        token = JwtUtils.createJwt(map, customConfig.getJwtKey(), time + effective * 1000);

        //返回token
        response.addHeader("token", token);

        try {
            //登录成功時，返回json格式进行提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            ResponseBuilder responseBuilder = ResponseBuilder.successBuilder();
            responseBuilder.setCode(HttpServletResponse.SC_OK);
            responseBuilder.setMessage("登陆成功！");
            out.write(JsonUtils.objectToJsonString(responseBuilder));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
