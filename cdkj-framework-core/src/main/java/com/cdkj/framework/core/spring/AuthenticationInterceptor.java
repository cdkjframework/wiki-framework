package com.cdkj.framework.core.spring;

import com.cdkj.framework.config.CustomConfig;
import com.cdkj.framework.consts.CacheConstant;
import com.cdkj.framework.core.builder.ResponseBuilder;
import com.cdkj.framework.core.spring.interceptor.AbstractInterceptor;
import com.cdkj.framework.entity.user.UserEntity;
import com.cdkj.framework.enums.ResponseBuilderEnum;
import com.cdkj.framework.exceptions.GlobalException;
import com.cdkj.framework.util.cache.JedisPoolUtil;
import com.cdkj.framework.util.jwt.JwtUtil;
import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.JsonUtil;
import com.cdkj.framework.util.tool.StringUtil;
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
 * @Package: com.cdkj.framework.core.user
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
    private LogUtil logUtil = LogUtil.getLogger(AuthenticationInterceptor.class);

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
                if (StringUtil.isNullAndSpaceOrEmpty(token)) {
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
                if (!StringUtil.isNullAndSpaceOrEmpty(token)) {
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
            writer.write(JsonUtil.beanToJsonObject(builder).toString());
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
        Claims claims = JwtUtil.parseJWT(token, customConfig.getJwtKey());
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