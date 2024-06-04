package com.cdkjframework.security.authorization;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.security.AuthenticationEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(ValidateCodeFilter.class);

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
    if (uri.contains(BusinessConsts.LOGIN) && !validateCode(request, response)) {
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
    HttpSession httpSession = request.getSession();
    String validateValue = ConvertUtils.convertString(httpSession.getAttribute(BusinessConsts.IMAGE_CODE));
    //这里需要验证前端传过来的验证码是否和session里面存的一致，并且要判断是否过期
    if (StringUtils.isNullAndSpaceOrEmpty(validateValue)) {
      return true;
    }
    // 时间效验
    long time = ConvertUtils.convertLong(httpSession.getAttribute(BusinessConsts.TIME));
    final long EXPIRATION_TIME = IntegerConsts.FIVE * IntegerConsts.SIXTY * IntegerConsts.ONE_THOUSAND;
    if (EXPIRATION_TIME < (System.currentTimeMillis() - time)) {
      ResponseUtils.out(response, ResponseBuilder.failBuilder("验证码过期！"));
      return false;
    }
    validateValue = validateValue.toLowerCase();

    // 获取数据
    String[] values = request.getParameterValues(BusinessConsts.IMAGE_CODE);
    if (values == null || values.length == IntegerConsts.ZERO) {
      ResponseUtils.out(response, ResponseBuilder.failBuilder("验证码错误！"));
    }
    String verifyCode = ConvertUtils.convertString(values[IntegerConsts.ZERO]).toLowerCase();

    if (StringUtils.isNullAndSpaceOrEmpty(verifyCode) ||
        !validateValue.equals(verifyCode)) {
      ResponseUtils.out(response, ResponseBuilder.failBuilder("验证码错误！"));
      return false;
    }
    return true;
  }
}
