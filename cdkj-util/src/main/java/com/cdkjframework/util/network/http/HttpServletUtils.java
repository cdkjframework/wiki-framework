package com.cdkjframework.util.network.http;

import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    private static final String HEADER_IP = "X-Real-IP";

    /**
     * HttpServletRequest
     *
     * @return 返回结果
     */
    public static HttpServletRequest getContext() {
        try {
            ServletRequestAttributes attributes = getRequestAttributes();
            if (attributes == null) {
                return null;
            } else {
                return attributes.getRequest();
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * HttpServletRequest
     *
     * @return 返回结果
     */
    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = getRequestAttributes();
            if (attributes == null) {
                return null;
            } else {
                return attributes.getRequest();
            }
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * HttpServletResponse
     *
     * @return 返回结果
     */
    public static jakarta.servlet.http.HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = getRequestAttributes();
        if (attributes == null) {
            return null;
        } else {
            return attributes.getResponse();
        }
    }

    /**
     * 请求属性
     *
     * @return 返回结果
     */
    private static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes() == null ?
                RequestContextHolder.currentRequestAttributes() : RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) attributes;
        try {
            //设置子线程共享
            RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
        } catch (Exception e) {

        }
        return servletRequestAttributes;
    }

    /**
     * 获取客户端IP地址
     *
     * @return 返回IP地址
     */
    public static String getRemoteAddr() {
        //
        String localAddr = getRequest().getHeader(HEADER_IP);
        if (StringUtils.isNullAndSpaceOrEmpty(localAddr)) {
            localAddr = getRequest().getRemoteAddr();
        }
        // 返回结果
        return localAddr;
    }
}
