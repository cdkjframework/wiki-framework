package com.cdkjframework.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.entity
 * @ClassName: PageEntity
 * @Description: 分页实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
@ApiModel("分页数据")
public class PageEntity<T> implements Serializable {

    /**
     * 序列版本UID
     */
    private static final long serialVersionUID = -766231940524932922L;

    /**
     * 构造实体
     *
     * @return 返回实体
     */
    public static <T> PageEntity<T> build() {
        return new PageEntity<>();
    }

    /**
     * 带参数构造函数
     *
     * @param index 当面页码
     * @param total 总条数
     * @param data  数据集 list
     */
    public static <T> PageEntity build(long index, long total, List<T> data) {
        return new PageEntity<>(index, total, data);
    }

    /**
     * 构造函数
     */
    public PageEntity() {
    }

    /**
     * 带参数构造函数
     *
     * @param index 当面页码
     * @param total 总条数
     * @param data  数据集 list
     */
    public PageEntity(long index, long total, List<T> data) {
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
