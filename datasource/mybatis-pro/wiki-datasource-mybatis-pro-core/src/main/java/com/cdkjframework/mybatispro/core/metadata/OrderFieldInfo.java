package com.cdkjframework.mybatispro.core.metadata;

import com.cdkjframework.constant.Constants;
import lombok.Data;

/**
 * @author nieqiurong
 * @since 3.5.4
 */
@Data
public class OrderFieldInfo {

    /**
     * 字段
     */
    private String column;

    /**
     * 排序类型
     */
    private String type;

    /**
     * 排序顺序
     */
    private short sort;


    public OrderFieldInfo(String column, boolean asc, short orderBySort) {
        this.column = column;
        this.type = asc ? Constants.ASC : Constants.DESC;
        this.sort = orderBySort;
    }

}
