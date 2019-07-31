package com.cdkj.framework.core.spring;

import com.cdkj.framework.consts.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkj.framework.core.spring
 * @ClassName: CdkjApplication
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CdkjApplication {

    /**
     * 程序启动
     *
     * @param args 参数
     * @return 返回结果
     */
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(primarySource, args);
        Application.applicationContext = context;
        return context;
    }
}
