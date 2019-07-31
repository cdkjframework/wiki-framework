package com.cdkj.framework.entity;

import java.io.Serializable;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.entity
 * @ClassName: PageEntity
 * @Description: 分页实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class PageEntity implements Serializable {

    private static final long serialVersionUID = -766231940524932922L;

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
    public PageEntity(long index, long total, Object data) {
        this.pageIndex = index;
        this.total = total;
        this.data = data;
    }

    /**
     * 编码
     */
    private int code;

    /**
     * 当前页码
     */
    private long pageIndex;

    /**
     * 总条数
     */
    private long total;

    /**
     * 分页数据集
     */
    private Object data;

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
