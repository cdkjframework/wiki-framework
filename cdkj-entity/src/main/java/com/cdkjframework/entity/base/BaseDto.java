package com.cdkjframework.entity.base;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.base
 * @ClassName: BaseDto
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class BaseDto extends BaseEntity {

    /**
     * 计算后的页码大小
     */
    private int pageSize = 10;

    /**
     * 当前页索引
     */
    private int pageIndex = 1;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序类型
     */
    private String sortType = "descending";
}
