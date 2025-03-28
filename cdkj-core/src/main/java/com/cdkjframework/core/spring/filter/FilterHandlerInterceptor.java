package com.cdkjframework.core.spring.filter;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.redis.lock.impl.RedisLettuceLock;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.core.spring.filter
 * @ClassName: FilterHandlerInterceptor
 * @Description: 拦截过滤
 * @Author: xiaLin
 * @Date: 2022/6/22 13:36
 * @Version: 1.0
 */
public class FilterHandlerInterceptor implements HandlerInterceptor {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(FilterHandlerInterceptor.class);

  /**
   * redis锁
   */
  private final RedisLettuceLock redisLettuceLock;

  /**
   * IP头部变量(可能通过Nginx代理后)
   */
  private static final String HEADER_IP = "X-Real-IP";

  /**
   * 锁IP请求URL地址KEY
   */
  private static final String LOCK_IP_URL_KEY = "lock_ip_";

  /**
   * IP请求URL地址时间
   */
  private static final String IP_URL_REQ_TIME = "ip_url_times_";

  /**
   * 极限时间
   */
  private static final long LIMIT_TIMES = 5;

  /**
   * IP锁定时间 秒
   */
  private static final int IP_LOCK_TIME = 60;

  /**
   * 构建函数
   */
  public FilterHandlerInterceptor(RedisLettuceLock redisLettuceLock) {
    this.redisLettuceLock = redisLettuceLock;
  }

  /**
   * 预处理
   *
   * @param request  请求
   * @param response 响应
   * @param o        参数
   * @return 返回结果
   * @throws Exception 异常信息
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
    String ip = request.getHeader(HEADER_IP);
    if (StringUtils.isNullAndSpaceOrEmpty(ip)) {
      ip = request.getRemoteAddr();
    }
    logUtils.info("request 请求地址 Uri={}，ip={}", request.getRequestURI(), ip);
    if (ipIsLock(ip)) {
      logUtils.info("ip访问被禁止={}", ip);
      ResponseBuilder builder = ResponseBuilder.failBuilder("ip访问被禁止");
      returnJson(response, builder);
      return false;
    }
    if (!addRequest(ip, request.getRequestURI())) {
      ResponseBuilder builder = ResponseBuilder.failBuilder("ip访问被禁止");
      returnJson(response, builder);
      return false;
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }

  /**
   * IP 是否已锁
   *
   * @param ip IP 地址
   * @return 返回是否成功
   */
  private Boolean ipIsLock(String ip) {
    if (redisLettuceLock.lock(LOCK_IP_URL_KEY + ip)) {
      return true;
    }
    return false;
  }

  /**
   * 添加请求信息
   *
   * @param ip  IP 地址
   * @param uri 请求路径
   * @return 返回是否成功
   */
  private Boolean addRequest(String ip, String uri) {
    String key = IP_URL_REQ_TIME + ip + uri;
    if (RedisUtils.syncExists(key)) {
      long time = RedisUtils.syncIncr(key, IntegerConsts.ONE);
      if (time >= LIMIT_TIMES) {
        redisLettuceLock.lock(LOCK_IP_URL_KEY + ip, IP_LOCK_TIME, ip);
        return false;
      }
    } else {
      redisLettuceLock.lock(key, (long) IntegerConsts.ONE, IntegerConsts.ONE);
    }
    return true;
  }

  /**
   * 返回结果
   *
   * @param response 响应
   * @param builder  返回结果
   * @throws Exception 异常信息
   */
  private void returnJson(HttpServletResponse response, ResponseBuilder builder) throws Exception {
    ResponseUtils.out(response, builder);
  }
}
