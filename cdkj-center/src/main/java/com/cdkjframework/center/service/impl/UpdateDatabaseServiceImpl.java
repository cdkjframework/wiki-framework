package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.GenerateService;
import com.cdkjframework.center.service.UpdateDatabaseService;
import com.cdkjframework.core.business.mapper.UpdateLibraryMapper;
import com.cdkjframework.entity.center.library.ColumnLayoutEntity;
import com.cdkjframework.entity.center.library.TableLayoutEntity;
import com.cdkjframework.entity.generate.template.TableColumnEntity;
import com.cdkjframework.entity.generate.template.TableEntity;
import com.cdkjframework.util.tool.CopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLSyntaxErrorException;
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
@RequiredArgsConstructor
public class UpdateDatabaseServiceImpl implements UpdateDatabaseService {

    /**
     * 个性数据 mapper
     */
    private final UpdateLibraryMapper updateLibraryMapper;

    /**
     * 生成服务
     */
    private final GenerateService generateServiceImpl;

    /**
     * 更新实体表
     *
     * @param layoutEntityList 元数据注释
     */
    @Override
    @Transactional(rollbackOn = {Exception.class, SQLSyntaxErrorException.class})
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
            for (TableLayoutEntity table :
                    insertTableList) {
                updateLibraryMapper.createTable(table);
            }
        }

        // 创建唯一索引
        createTableUniqueIndex(layoutEntityList);
    }

    /**
     * 创建表唯一索引
     */
    private void createTableUniqueIndex(List<TableLayoutEntity> layoutEntityList) {
        List<TableLayoutEntity> tableLayoutEntities = new ArrayList<>();
        for (TableLayoutEntity entity :
                layoutEntityList) {
            List<ColumnLayoutEntity> columnLayoutEntities = entity.getLayoutEntities();
            if (columnLayoutEntities == null || columnLayoutEntities.isEmpty()) {
                continue;
            }

            // 验证是否有唯一索引字段
            columnLayoutEntities = columnLayoutEntities.stream()
                    .filter(f -> f.getUnique())
                    .collect(Collectors.toList());
            if (columnLayoutEntities.isEmpty()) {
                continue;
            }
            entity.setLayoutEntities(columnLayoutEntities);
            tableLayoutEntities.add(entity);
        }

        if (!tableLayoutEntities.isEmpty()) {
            for (TableLayoutEntity table :
                    tableLayoutEntities) {
                for (ColumnLayoutEntity entity :
                        table.getLayoutEntities()) {
                    updateLibraryMapper.createTableUniqueIndex(table, entity);
                }
            }
        }
    }

    /**
     * 修改表信息
     *
     * @param updateTableList 修改表
     */
    private void updateTable(List<TableLayoutEntity> updateTableList) {
        List<TableLayoutEntity> layoutEntities = new ArrayList<>();
        List<TableLayoutEntity> deleteEntities = new ArrayList<>();
        for (TableLayoutEntity layoutEntity :
                updateTableList) {
            TableLayoutEntity entity = validationTableFields(layoutEntity);
            if (entity != null && !entity.getLayoutEntities().isEmpty()) {
                deleteEntities.add(entity);
            }
            if (layoutEntity.getLayoutEntities() == null || layoutEntity.getLayoutEntities().isEmpty()) {
                continue;
            }
            layoutEntities.add(layoutEntity);
        }

        // 修改数据
        if (!layoutEntities.isEmpty()) {
            for (TableLayoutEntity table :
                    layoutEntities) {
                for (ColumnLayoutEntity entity :
                        table.getLayoutEntities()) {
                    updateLibraryMapper.createTableColumn(table, entity);
                }
            }
        }
        // 删除字段
        if (!deleteEntities.isEmpty()) {
            for (TableLayoutEntity table :
                    deleteEntities) {
                for (ColumnLayoutEntity entity :
                        table.getLayoutEntities()) {
                    updateLibraryMapper.deleteTableField(table, entity);
                }
            }
        }
    }

    /**
     * 效验表字段
     *
     * @param tableLayoutEntity 表信息
     * @return 返回要删了列
     */
    private TableLayoutEntity validationTableFields(TableLayoutEntity tableLayoutEntity) {
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
            return null;
        }
        List<String> columnList = columnEntities.stream()
                .map(TableColumnEntity::getColumnName)
                .collect(Collectors.toList());
        List<ColumnLayoutEntity> layoutEntities = layoutEntityList.stream()
                .filter(f -> !columnList.contains(f.getName()))
                .collect(Collectors.toList());
        tableLayoutEntity.setLayoutEntities(layoutEntities);

        // 找到要删除的列
        TableLayoutEntity entity = CopyUtils.copyProperties(tableLayoutEntity, TableLayoutEntity.class);
        List<String> notColumnList = layoutEntityList.stream()
                .map(ColumnLayoutEntity::getName)
                .collect(Collectors.toList());
        entity.setLayoutEntities(new ArrayList<>());
        List<ColumnLayoutEntity> entityList = entity.getLayoutEntities();
        for (TableColumnEntity column :
                columnEntities) {
            if (notColumnList.contains(column.getColumnName())) {
                continue;
            }
            ColumnLayoutEntity layoutEntity = new ColumnLayoutEntity();
            layoutEntity.setName(column.getColumnName());
            entityList.add(layoutEntity);
        }
        // 返回结果
        return entity;
    }
}
