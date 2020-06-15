package com.cdkjframework.util.network.http;

import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.util.http
 * @ClassName: HttpServletUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class HttpServletUtils {

    /**
     * IP头部变量
     */
    private final String HEADER_IP = "X-Real-IP";

    /**
     * HttpServletRequest
     *
     * @return 返回结果
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * HttpServletResponse
     *
     * @return 返回结果
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 获取客户端IP地址
     *
     * @return 返回IP地址
     */
    public static String getLocalAddr() {
        //
        String localAddr = getRequest().getHeader("HEADER_IP");
        if (StringUtils.isNullAndSpaceOrEmpty(localAddr)) {
            localAddr = getRequest().getLocalAddr();
        }
        // 返回结果
        return localAddr;
    }
}
