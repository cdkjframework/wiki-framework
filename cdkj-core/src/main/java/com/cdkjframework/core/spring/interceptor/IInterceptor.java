package com.cdkjframework.core.spring.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.spring.interceptor
 * @ClassName: Interceptor
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface IInterceptor {

    /**
     * 验证用户是否登录
     *
     * @param token              密钥
     * @param verified           验证
     * @param httpServletRequest 请求信息
     * @throws GlobalException 异常信息
     */
    void authenticateUserLogin(String token, boolean verified, HttpServletRequest httpServletRequest) throws GlobalException, GlobalException;

    /**
     * 修改参数
     *
     * @param httpServletRequest 参数
     * @param userEntity         用户实体
     */
    void modifyParameters(HttpServletRequest httpServletRequest, UserEntity userEntity);

    /**
     * 将请求体转换为 JSON 对象
     *
     * @param body       请求体
     * @param builder    转换结果
     * @param userEntity 用户信息
     * @return 返回是否做过转换
     */
    boolean bodyChangeJson(String body, StringBuilder builder, UserEntity userEntity);

    /**
     * 日志记录
     *
     * @param httpServletRequest http request
     * @param userEntity         用户实体
     * @param inString           传入参数
     */
    void logRecord(HttpServletRequest httpServletRequest, UserEntity userEntity, String inString);

    /**
     * 添加 JSON 值
     *
     * @param jsonObject JSON 对象
     * @param userEntity 用户实体
     */
    void addJsonObject(JSONObject jsonObject, UserEntity userEntity);

    /**
     * 请求是否需要验证
     *
     * @param servletPath 请求路径
     * @return 返回结果（布尔值）
     */
    boolean requestNeedsValidation(String servletPath);
}
