package com.cdkjframework.web.socket.configure;

import com.cdkjframework.constant.IntegerConsts;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebAppRootListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebAppRootContext
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@Component
@EnableAutoConfiguration
public class WebAppRootContext implements ServletContextInitializer {

    /**
     * 启动时设置参数
     *
     * @param servletContext servlet上下文
     * @throws ServletException servlet异常
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // 初始变量
        final String textBufferSize = "org.apache.tomcat.websocket.textBufferSize";
        long value = IntegerConsts.BYTE_LENGTH * IntegerConsts.BYTE_LENGTH;

        // 设置事件
        servletContext.addListener(WebAppRootListener.class);
        servletContext.setInitParameter(textBufferSize, String.valueOf(value));
    }

}