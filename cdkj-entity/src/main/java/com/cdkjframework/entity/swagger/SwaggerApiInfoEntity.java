package com.cdkjframework.entity.swagger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity
 * @ClassName: SwaggerApiInfoEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class SwaggerApiInfoEntity {

    /**
     * 注册到spring生成bean的名称
     */
    private String beanName;
    /**
     * swagger API分组名称
     */
    private String groupName;
    /**
     * api 扫描的包
     */
    private String basePackage;
}
