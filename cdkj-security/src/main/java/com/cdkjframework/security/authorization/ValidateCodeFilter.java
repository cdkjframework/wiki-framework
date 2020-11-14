package com.cdkjframework.security.authorization;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.authorization
 * @ClassName: ValidateCodefilter
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    /**
     * 过虑权限验证
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        // 如果为get请求并且请求uri为/login（也就是我们登录表单的form的action地址）
        if (BusinessConsts.LOGIN.contains(uri) && !validateCode(request, response)) {
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 验证用户输入的验证码和session中存的是否一致
     *
     * @param request  请求
     * @param response 响应
     */
    private boolean validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String validateValue = ConvertUtils.convertString(request.getSession().getAttribute(BusinessConsts.IMAGE_CODE));
        //这里需要验证前端传过来的验证码是否和session里面存的一致，并且要判断是否过期
        logger.info(validateValue);
        String code = StringUtils.Empty;
        InputStream inputStream = request.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        if (bytes.length > 0) {
            inputStream.read(bytes);
            Map<String, Object> map = JsonUtils.jsonStringToMap(new String(bytes));
            code = ConvertUtils.convertString(map.get(BusinessConsts.IMAGE_CODE));
        }

        ResponseBuilder builder;
        if (StringUtils.isNullAndSpaceOrEmpty(code)) {
            builder = ResponseBuilder.failBuilder("验证码错误！");
            ResponseUtils.out(response, builder);
            return false;
        }
        if (StringUtils.isNullAndSpaceOrEmpty(validateValue) || !validateValue.equals(code)) {
            builder = ResponseBuilder.failBuilder("验证码错误！");
            ResponseUtils.out(response, builder);
            return false;
        }
        return true;
    }
}
