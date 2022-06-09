package com.cdkjframework.entity.number;

import com.cdkjframework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.datasource.mongodb.entity
 * @ClassName: NumberEntity
 * @Description: mongo生成单号实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NumberEntity extends BaseEntity {

    /**
     * 关键测试
     */
    private String key;

    /**
     * 时间格式
     */
    private int dateType;

    /**
     * 时间值
     */
    private String dateValue;

    /**
     * 单号序号
     */
    private Long value;
}
