package com.cdkjframework.socket.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.socket.config
 * @ClassName: SocketConfigurationSelector
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:24
 * @Version: 1.0
 */
public class SocketConfigurationSelector implements ImportSelector {
    /**
     * 选择导入
     *
     * @param importingClassMetadata 参数
     * @return 类名，如果没有则为空数组
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                SocketAutoConfiguration.class.getName(),
                SocketConfig.class.getName()
        };
    }
}
