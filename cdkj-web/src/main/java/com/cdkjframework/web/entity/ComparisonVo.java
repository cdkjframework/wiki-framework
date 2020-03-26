package com.cdkjframework.web.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.entity
 * @ClassName: ComparisonVo
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class ComparisonVo {

    /**
     * 字段名称
     */
    private String field = "13213132";

    /**
     * 原始值
     */
    private String originalValue = "13213132";

    /**
     * 当前值
     */
    private String currentValue = "13213132";
}
