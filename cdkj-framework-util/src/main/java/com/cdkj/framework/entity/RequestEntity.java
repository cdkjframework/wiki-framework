package com.cdkj.framework.entity;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.entity
 * @ClassName: RequestEntity
 * @Description: 分页实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class RequestEntity extends BaseEntity {

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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
