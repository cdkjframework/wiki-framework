package com.cdkjframework.security.authorization;

import com.cdkjframework.constant.BusinessConsts;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.authorization
 * @ClassName: AuthenticationFilter
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 请求类型
     */
    private List<String> CONTENT_TYPE_LIST = Arrays.asList(MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE);

    /**
     * 尝试身份验证
     *
     * @param request  请求
     * @param response 响应
     * @return 返回结果
     * @throws AuthenticationException 权限异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //attempt Authentication when Content-Type is json
        if (CONTENT_TYPE_LIST.contains(request.getContentType())) {
            Object userName = request.getAttribute(BusinessConsts.USER_NAME);
            Object password = request.getAttribute(BusinessConsts.PASSWORD);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
