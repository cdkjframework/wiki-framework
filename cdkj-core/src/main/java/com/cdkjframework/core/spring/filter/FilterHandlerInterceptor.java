package com.cdkjframework.core.spring.filter;

import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.redis.lock.impl.RedisLettuceLock;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    private static final String LOCK_IP_URL_KEY = "lock_ip_";

    private static final String IP_URL_REQ_TIME = "ip_url_times_";

    private static final long LIMIT_TIMES = 5;

    private static final int IP_LOCK_TIME = 60;

    public FilterHandlerInterceptor(RedisLettuceLock redisLettuceLock) {
        this.redisLettuceLock = redisLettuceLock;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        log.info("request请求地址uri={},ip={}", httpServletRequest.getRequestURI(), IpAdrressUtil.getIpAdrress(httpServletRequest));
//        if (ipIsLock(IpAdrressUtil.getIpAdrress(httpServletRequest))){
//            log.info("ip访问被禁止={}",IpAdrressUtil.getIpAdrress(httpServletRequest));
//            ApiResult result = new ApiResult(ResultEnum.LOCK_IP);
//            returnJson(httpServletResponse, JSON.toJSONString(result));
//            return false;
//        }
//        if(!addRequestTime(IpAdrressUtil.getIpAdrress(httpServletRequest),httpServletRequest.getRequestURI())){
//            ApiResult result = new ApiResult(ResultEnum.LOCK_IP);
//            returnJson(httpServletResponse, JSON.toJSONString(result));
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * @param ip
     * @return java.lang.Boolean
     * @Description: 判断ip是否被禁用
     * @author: shuyu.wang
     * @date: 2019-10-12 13:08
     */
    private Boolean ipIsLock(String ip) {
        if (redisLettuceLock.lock(LOCK_IP_URL_KEY + ip)) {
            return true;
        }
        return false;
    }

    /**
     * @param ip
     * @param uri
     * @return java.lang.Boolean
     * @Description: 记录请求次数
     * @author: shuyu.wang
     * @date: 2019-10-12 17:18
     */
    private Boolean addRequestTime(String ip, String uri) {
        String key = IP_URL_REQ_TIME + ip + uri;
        if (redisLettuceLock.lock(key)) {
            long time = RedisUtils.syncIncr(key, 1);
            if (time >= LIMIT_TIMES) {
//                redisLettuceLock.lock(LOCK_IP_URL_KEY + ip, ip, IP_LOCK_TIME);
                return false;
            }
        } else {
            redisLettuceLock.lock(key, (long) 1, 1);
        }
        return true;
    }

    /**
     * 返回结果
     *
     * @param response 响应
     * @param json     JSON对象
     * @throws Exception 异常信息
     */
    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logUtils.error("LoginInterceptor response error ---> {}", e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
