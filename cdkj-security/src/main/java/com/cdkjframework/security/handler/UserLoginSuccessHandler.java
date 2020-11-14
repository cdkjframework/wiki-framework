package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.HttpHeaderConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.network.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.cloud.handler
 * @ClassName: UserLoginSuccessHandler
 * @Description: 用户登录成功
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 自定义配置
     */
    private final CustomConfig customConfig;

    /**
     * 构造函数
     *
     * @param customConfig 自定义配置
     */
    public UserLoginSuccessHandler(CustomConfig customConfig) {
        this.customConfig = customConfig;
    }

    /**
     * 权限认证成功
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 权限
     * @throws IOException      异常信息
     * @throws ServletException 异常信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUserEntity user = (SecurityUserEntity) authentication.getPrincipal();
        ResponseBuilder builder = ResponseBuilder.successBuilder();
        builder.setData(user);
        // 构建 token
        buildJwtToken(request, response, user);

        // 返回登录结果
        ResponseUtils.out(response, builder);
    }

    /**
     * 生成 jwt token
     *
     * @param user     用户实体
     * @param request  请求
     * @param response 响应
     */
    private void buildJwtToken(HttpServletRequest request, HttpServletResponse response, SecurityUserEntity user) {
        // 生成 JWT token
        Map<String, Object> map = new HashMap<>(IntegerConsts.FOUR);
        map.put(BusinessConsts.LOGIN_NAME, user.getUsername());
        long effective = IntegerConsts.TWENTY_FOUR * IntegerConsts.SIXTY * IntegerConsts.SIXTY;
        long time = System.currentTimeMillis() / 1000;
        map.put(BusinessConsts.TIME, time);
        String userAgent = request.getHeader(HttpHeaderConsts.USER_AGENT);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("loginName=%s&effective=%s&time=%s&userAgent=%s",
                user.getUsername(), effective, time, userAgent));
        String token = Md5Utils.getMd5(builder.toString());
        map.put(BusinessConsts.HEADER_TOKEN, token);
        String jwtToken = JwtUtils.createJwt(map, customConfig.getJwtKey());
        response.setHeader(BusinessConsts.HEADER_TOKEN, jwtToken);
    }
}
