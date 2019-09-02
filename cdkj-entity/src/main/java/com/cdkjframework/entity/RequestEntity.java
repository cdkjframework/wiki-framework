package com.cdkjframework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.entity
 * @ClassName: RequestEntity
 * @Description: 分页实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class RequestEntity extends BaseEntity {

    /**
     * 计算后的页码大小
     */
    @ApiModelProperty("类目编码")
    private int pageSize = 10;

    /**
     * 当前页索引
     */
    @ApiModelProperty("当前页索引")
    private int pageIndex = 1;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String sortField;

    /**
     * 排序类型
     */
    @ApiModelProperty("排序类型")
    private String sortType = "descending";
}
