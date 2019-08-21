package com.cdkjframework.core.spring;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.consts.CacheConstant;
import com.cdkjframework.core.spring.interceptor.AbstractInterceptor;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.enums.ResponseBuilderEnum;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.cache.JedisPoolUtil;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.user
 * @ClassName: AuthenticationInterceptor
 * @Description: 身份验证拦截器
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class AuthenticationInterceptor extends AbstractInterceptor implements HandlerInterceptor {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(AuthenticationInterceptor.class);

    /**
     * 自定义配置信息
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 预处理
     *
     * @param httpServletRequest  http request 请求
     * @param httpServletResponse http response
     * @param o                   参数信息
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logUtil.info("请求拦截 开始");
        //获取路径
        String servletPath = httpServletRequest.getServletPath();
        //验证请求地址是否需要权限验证
        boolean validation = requestNeedsValidation(servletPath);
        //结果
        boolean result = !validation;
        //获取并验证 token
        String token = httpServletRequest.getHeader("token");
        ResponseBuilder builder = null;
        if (validation) {
            try {
                if (StringUtils.isNullAndSpaceOrEmpty(token)) {
                    throw new GlobalException("无效 token");
                }

                authenticateUserLogin(token, true, httpServletRequest);
                result = true;
            } catch (GlobalException ex) {
                builder = new ResponseBuilder();
                builder.setCode(ResponseBuilderEnum.LogonFailure.getValue());
                builder.setMessage(ex.getMessage());
            }
        } else {
            try {
                if (!StringUtils.isNullAndSpaceOrEmpty(token)) {
                    authenticateUserLogin(token, false, httpServletRequest);
                }
            } catch (GlobalException ex) {
                logUtil.error(ex);
            }
        }

        if (builder != null) {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("text/html;charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JsonUtils.beanToJsonObject(builder).toString());
            writer.close();
        }

        logUtil.info("请求拦截 结束" + result);
        return result;
    }

    /**
     * post 请求处理
     *
     * @param httpServletRequest  http request 请求
     * @param httpServletResponse http response
     * @param o                   参数信息
     * @param modelAndView        数据信息
     * @throws Exception 异常信息
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("afterCompletion");
    }

    /**
     * 请求完成后
     *
     * @param httpServletRequest  http request 请求
     * @param httpServletResponse http response
     * @param o                   参数信息
     * @param e                   异常数据
     * @throws Exception 异常信息
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion");
    }

    /**
     * 验证用户是否登录
     *
     * @param token    密钥
     * @param verified 验证
     * @return 返回结果
     */
    @Override
    public void authenticateUserLogin(String token, boolean verified, HttpServletRequest httpServletRequest) throws GlobalException {
        // jwt 解密
        Claims claims = JwtUtils.parseJWT(token, customConfig.getJwtKey());
        String key = CacheConstant.userLogin + claims.get("token").toString();

        //获取用户信息
        UserEntity userEntity = JedisPoolUtil.getEntity(key, UserEntity.class);
        if (userEntity == null && verified) {
            throw new GlobalException(ResponseBuilderEnum.LogonFailure.getName());
        }

        if (userEntity == null) {
            userEntity = new UserEntity();
        }

        //修改参数
        modifyParameters(httpServletRequest, userEntity);
    }
}