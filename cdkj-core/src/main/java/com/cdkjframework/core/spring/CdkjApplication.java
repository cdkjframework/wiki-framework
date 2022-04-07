package com.cdkjframework.core.spring;

import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.spring
 * @ClassName: CdkjApplication
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CdkjApplication {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(CdkjApplication.class);

    /**
     * 接口服务
     */
    public static ApplicationService service;

    /**
     * 程序启动
     *
     * @param args 参数
     * @return 返回结果
     */
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        // 在Spring Boot应用中通过监听信号量和注册关闭钩子来实现在进程退出之前执行收尾工作
        // 监听信号量
        Signal sg = new Signal("TERM");
        Signal.handle(sg, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                logUtils.info("do signal handle: {}", signal.getName());
                System.exit(IntegerConsts.ZERO);
            }
        });

        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // 执行收尾工作
                logUtils.info("do something on shutdown hook");
                if (service != null) {
                    service.programEnd();
                }
            }
        });

        // 启动程序
        ConfigurableApplicationContext context = SpringApplication.run(primarySource, args);
        Application.applicationContext = context;
        return context;
    }
}
