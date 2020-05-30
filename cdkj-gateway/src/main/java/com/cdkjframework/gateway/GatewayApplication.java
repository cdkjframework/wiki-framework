package com.cdkjframework.gateway;

import com.cdkjframework.core.spring.CdkjApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway
 * @ClassName: GatewayApplication
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
})
public class GatewayApplication {

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args){
        CdkjApplication.run(GatewayApplication.class, args);
    }
}
