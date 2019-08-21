package com.cdkjframework.entity.generate.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity.generate
 * @ClassName: DatabaseEntity
 * @Description: 数据库实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class DatabaseEntity {

    /**
     * 当前登录数据库名称
     */
    private String tableSchema;

    /**
     * 子级信息
     */
    private List<DatabaseEntity> children = new ArrayList<>();
}
