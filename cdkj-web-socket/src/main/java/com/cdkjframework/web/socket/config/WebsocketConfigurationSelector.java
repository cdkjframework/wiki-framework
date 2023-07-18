package com.cdkjframework.web.socket.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebsocketConfigurationSelector
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 8:59
 * @Version: 1.0
 */
public class WebsocketConfigurationSelector implements ImportSelector {
    /**
     * 选择导入
     *
     * @param importingClassMetadata 参数
     * @return 类名，如果没有则为空数组
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                WebsocketAutoConfiguration.class.getName(),
                WebSocketConfig.class.getName()
        };
    }
}
