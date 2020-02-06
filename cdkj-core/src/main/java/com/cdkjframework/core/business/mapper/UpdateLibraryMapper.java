package com.cdkjframework.core.business.mapper;

import com.cdkjframework.entity.center.library.ColumnLayoutEntity;
import com.cdkjframework.entity.center.library.TableLayoutEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.business.mapper
 * @ClassName: UpdateLibraryMapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public interface UpdateLibraryMapper {

    /**
     * 创建表
     *
     * @param table 表集合
     */
    void createTable(TableLayoutEntity table);

    /**
     * 创建字段
     *
     * @param table  表集合
     * @param column 字段
     */
    void createTableColumn(@Param("item") TableLayoutEntity table, @Param("column") ColumnLayoutEntity column);

    /**
     * 删了表字段
     *
     * @param table  表集合
     * @param column 字段
     */
    void deleteTableField(@Param("item") TableLayoutEntity table, @Param("column") ColumnLayoutEntity column);

    /**
     * 删除表
     *
     * @param tableList 表集合
     */
    void deleteTable(List<TableLayoutEntity> tableList);

    /**
     * 创建唯一索引
     *
     * @param table  表集合
     * @param column 字段
     */
    void createTableUniqueIndex(@Param("item") TableLayoutEntity table, @Param("column") ColumnLayoutEntity column);
}
