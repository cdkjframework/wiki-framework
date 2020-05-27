package com.cdkjframework.security.server.jwt;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.server.jwt
 * @ClassName: AuthenticationFilter
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class AuthenticationFilter extends BasicAuthenticationFilter {

    /**
     * 配置读取
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 构造函数
     *
     * @param authenticationManager 身份验证管理器
     */
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 對請求進行過濾
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            //请求体的头中是否包含Authorization
            String header = request.getHeader("token");
            //Authorization中是否包含Bearer，有一个不包含时直接返回
            if (StringUtils.isNotNullAndEmpty(header)) {
                chain.doFilter(request, response);
                responseJson(response);
                return;
            }
            //获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            //获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            responseJson(response);
            e.printStackTrace();
        }
    }

    /**
     * 未登錄時的提示
     *
     * @param response 响应
     */
    private void responseJson(HttpServletResponse response) {
        try {
            //未登錄時，使用json進行提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            ResponseBuilder builder = ResponseBuilder.failBuilder();
            builder.setCode(HttpServletResponse.SC_FORBIDDEN);
            builder.setMessage("请登录！");
            out.write(JsonUtils.objectToJsonString(builder));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 通过 token，获取用户信息
     *
     * @param request 请求
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isNotNullAndEmpty(token)) {
            //通过token解析出用户信息
            Claims claims = JwtUtils.parseJwt(token, customConfig.getJwtKey());
            String username = claims.get("username").toString();
            //不为null，返回
            if (StringUtils.isNotNullAndEmpty(username)) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
