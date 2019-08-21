package com.cdkjframework.entity.generate.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity.generate
 * @ClassName: TableEntity
 * @Description: 表实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class TableEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表说明
     */
    private String tableComment;

    /**
     * 表所在数据库
     */
    private String tableSchema;
}
