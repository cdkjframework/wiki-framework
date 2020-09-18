package com.cdkjframework.core.spring.cors;

import com.cdkjframework.core.member.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.core.spring.cors
 * @ClassName: CustomeizedRequest
 * @Description: 继承HttpServletRequestWrapper, 重写数据
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CustomeizedRequest extends HttpServletRequestWrapper {

    /**
     * 构造函数
     *
     * @param request 请求
     */
    public CustomeizedRequest(HttpServletRequest request) {
        super(request);
    }

    /**
     * 获取头部信息
     *
     * @param name 名称
     * @return 返回结果
     */
    @Override
    public String getHeader(String name) {
        if (!CurrentUser.USER_LOGIN_TOKEN_KEY.equals(name)) {
            return super.getHeader(name);
        }
        return super.getHeader(name);
    }
}
