package com.cdkjframework.entity.swagger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.swagger
 * @ClassName: SwaggerHeaderEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class SwaggerHeaderEntity {

    /**
     * 名称
     */
    private String headerName;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据类型
     */
    private String headerType;
}