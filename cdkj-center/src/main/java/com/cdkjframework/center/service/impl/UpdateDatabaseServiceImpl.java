package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.GenerateService;
import com.cdkjframework.center.service.UpdateDatabaseService;
import com.cdkjframework.constant.Application;
import com.cdkjframework.core.business.mapper.UpdateLibraryMapper;
import com.cdkjframework.core.processor.AbstractCdkjProcessor;
import com.cdkjframework.entity.center.CenterTableLayoutEntity;
import com.cdkjframework.entity.center.library.ColumnLayoutEntity;
import com.cdkjframework.entity.center.library.TableLayoutEntity;
import com.cdkjframework.entity.generate.template.TableColumnEntity;
import com.cdkjframework.entity.generate.template.TableEntity;
import com.cdkjframework.entity.generate.template.TreeEntity;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.center.service.impl
 * @ClassName: UpdateDatabaseServiceImpl
 * @Description: 更新数据服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class UpdateDatabaseServiceImpl implements UpdateDatabaseService {

    /**
     * 个性数据 mapper
     */
    @Autowired
    private UpdateLibraryMapper updateLibraryMapper;

    /**
     * 生成服务
     */
    @Autowired
    private GenerateService generateServiceImpl;

    /**
     * 更新实体表
     *
     * @param layoutEntityList 元数据注释
     */
    @Override
    public void updateEntityTable(List<TableLayoutEntity> layoutEntityList) {
        TableLayoutEntity layoutEntity = layoutEntityList.get(0);
        TableEntity entity = new TableEntity();
        entity.setTableSchema(layoutEntity.getSchema());

        // 获取全部表
        List<TableEntity> treeEntities = generateServiceImpl
                .findTableList(entity);
        List<String> tableList = treeEntities.stream()
                .map(TableEntity::getTableName)
                .collect(Collectors.toList());
        // 删除的表
        List<TableLayoutEntity> deleteTableList = new ArrayList<>();
        for (String table :
                tableList) {
            List listTable = layoutEntityList.stream()
                    .filter(f -> table.equals(f.getName()))
                    .collect(Collectors.toList());
            if (!listTable.isEmpty()) {
                continue;
            }
            TableLayoutEntity tableLayoutEntity = new TableLayoutEntity();
            tableLayoutEntity.setName(table);
            deleteTableList.add(tableLayoutEntity);
        }
        if (!deleteTableList.isEmpty()) {
            updateLibraryMapper.deleteTable(deleteTableList);
        }

        // 筛选修改表
        List<TableLayoutEntity> updateTableList = layoutEntityList.stream()
                .filter(f -> tableList.contains(f.getName()))
                .collect(Collectors.toList());
        updateTable(updateTableList);

        // 筛选添加表
        List<TableLayoutEntity> insertTableList = layoutEntityList.stream()
                .filter(f -> !tableList.contains(f.getName()))
                .collect(Collectors.toList());

        if (!insertTableList.isEmpty()) {
            updateLibraryMapper.createTable(insertTableList);
        }
    }

    /**
     * 修改表信息
     *
     * @param updateTableList 修改表
     */
    private void updateTable(List<TableLayoutEntity> updateTableList) {
        List<TableLayoutEntity> layoutEntities = new ArrayList<>();
        for (TableLayoutEntity layoutEntity :
                updateTableList) {
            ValidationTableFields(layoutEntity);
            if (layoutEntity.getLayoutEntities() == null || layoutEntity.getLayoutEntities().isEmpty()) {
                continue;
            }
            layoutEntities.add(layoutEntity);
        }

        if (layoutEntities.isEmpty()) {
            return;
        }

        // 修改数据
        updateLibraryMapper.createTableColumn(layoutEntities);
    }

    /**
     * 效验表字段
     *
     * @param tableLayoutEntity 表信息
     */
    private void ValidationTableFields(TableLayoutEntity tableLayoutEntity) {
        // 设置查询条件
        TableColumnEntity columnEntity = new TableColumnEntity();
        columnEntity.setTableName(tableLayoutEntity.getName());
        columnEntity.setTableSchema(tableLayoutEntity.getSchema());
        // 查询当前表的全部列
        List<TableColumnEntity> columnEntities = generateServiceImpl
                .findTableColumnList(columnEntity);

        // 列
        List<ColumnLayoutEntity> layoutEntityList = tableLayoutEntity.getLayoutEntities();
        if (layoutEntityList == null || layoutEntityList.isEmpty()) {
            return;
        }
        List<String> columnList = columnEntities.stream()
                .map(TableColumnEntity::getColumnName)
                .collect(Collectors.toList());
        List<ColumnLayoutEntity> layoutEntities = layoutEntityList.stream()
                .filter(f -> !columnList.contains(f.getName()))
                .collect(Collectors.toList());
        tableLayoutEntity.setLayoutEntities(layoutEntities);
    }
}
