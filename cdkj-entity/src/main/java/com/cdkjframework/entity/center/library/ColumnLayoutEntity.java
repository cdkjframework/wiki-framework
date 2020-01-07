package com.cdkjframework.entity.center.library;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.center.library
 * @ClassName: ColumnLayoutEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class ColumnLayoutEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

    /**
     * 是否为唯一
     */
    private Boolean unique;

    /**
     * 是否为空
     */
    private Boolean nullable;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 小数位数
     */
    private Integer scale;

    /**
     * 数据类型
     */
    private String dataType;
}
