package com.cdkjframework.core.business.mapper;

import com.cdkjframework.entity.center.library.TableLayoutEntity;
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
     * @param tableList 表集合
     */
    void createTableColumn(List<TableLayoutEntity> tableList);

    /**
     * 删了表字段
     *
     * @param tableList 表集合
     */
    void deleteTableField(List<TableLayoutEntity> tableList);

    /**
     * 删除表
     *
     * @param tableList 表集合
     */
    void deleteTable(List<TableLayoutEntity> tableList);

    /**
     * 创建唯一索引
     *
     * @param tableList 表集合
     */
    void createTableUniqueIndex(List<TableLayoutEntity> tableList);
}
