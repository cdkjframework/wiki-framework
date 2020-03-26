package com.cdkjframework.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.entity
 * @ClassName: PageVo
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class PageVo<T> {


    private static final long serialVersionUID = -766231940524932922L;

    /**
     * 构造函数
     */
    public PageVo() {
    }

    /**
     * 带参数构造函数
     *
     * @param index 当面页码
     * @param total 总条数
     * @param data  数据集 list
     */
    public PageVo(long index, long total, List<T> data) {
        this.pageIndex = index;
        this.total = total;
        this.data = data;
    }

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    private int code;

    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    private long pageIndex;

    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private long total;

    /**
     * 分页数据集
     */
    @ApiModelProperty("分页数据集")
    private List<T> data;
}
