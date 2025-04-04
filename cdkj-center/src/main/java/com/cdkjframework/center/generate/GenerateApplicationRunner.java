package com.cdkjframework.center.generate;

import com.cdkjframework.center.service.UpdateDatabaseService;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.center.library.ColumnLayoutEntity;
import com.cdkjframework.entity.center.library.TableLayoutEntity;
import com.cdkjframework.enums.datasource.DataTypeContrastEnums;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.center.runner
 * @ClassName: GenerateApplicationRunner
 * @Description: 生成
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@Order(Integer.MIN_VALUE + 100)
public class GenerateApplicationRunner implements ApplicationRunner {

    /**
     * 个性数据服务
     */
    private final UpdateDatabaseService updateDatabaseServiceImpl;

    @Autowired
    public GenerateApplicationRunner(UpdateDatabaseService updateDatabaseServiceImpl) {
        this.updateDatabaseServiceImpl = updateDatabaseServiceImpl;
    }

    /**
     * 执行
     *
     * @param args 参数
     * @throws Exception 异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (true) {
            return;
        }
        // 获取需要更新表实体
        Map<String, Object> map = Application.applicationContext.getBeansWithAnnotation(Table.class);
        // 获取表
        List<TableLayoutEntity> layoutEntities = new ArrayList<>();
        analyticEntity(map, layoutEntities);

        // 验证是否为空
        if (layoutEntities.isEmpty()) {
            return;
        }
        updateDatabaseServiceImpl.updateEntityTable(layoutEntities);

    }

    /**
     * 解析实体
     *
     * @param map            map
     * @param layoutEntities 解析结果
     */
    private void analyticEntity(Map<String, Object> map, List<TableLayoutEntity> layoutEntities) {
        for (Map.Entry<String, Object> entrySet :
                map.entrySet()) {
            Class clazz = entrySet.getValue().getClass();
            Table table = (Table) clazz.getAnnotation(Table.class);
            TableLayoutEntity layoutEntity = new TableLayoutEntity();
            layoutEntity.setName(table.name());
            layoutEntity.setLayoutEntities(new ArrayList<>());
            layoutEntity.setSchema(table.schema());
            layoutEntities.add(layoutEntity);

            // 实体
            final int pos = 0;
            Field[] fields = clazz.getDeclaredFields();
            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            Field[] fieldList = new Field[fields.length + superFields.length];
            System.arraycopy(superFields, pos, fieldList, pos, superFields.length);
            System.arraycopy(fields, pos, fieldList, superFields.length, fields.length);

            for (Field field :
                    fieldList) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                // 数据类型
                String name = field.getType().getName();

                ColumnLayoutEntity entity = new ColumnLayoutEntity();
                buildColumnField(column, name, entity);
                layoutEntity.getLayoutEntities().add(entity);
            }
        }
    }

    /**
     * 生成字段
     *
     * @param column 字段标签
     * @param entity 实体
     */
    private void buildColumnField(Column column, String name, ColumnLayoutEntity entity) {
        if (StringUtils.isNullAndSpaceOrEmpty(name)) {
            name = "String";
        } else {
            String[] arrayString = name.split("\\.");
            name = arrayString[arrayString.length - 1];
        }
        final String defaultValue = "String";
        final int length = 4000;
        // 数据类型为字符串且长度大于4000则个性类型
        if (defaultValue.equals(name)) {
            if (column.length() > length) {
                name = "TEXT";
            } else if (column.length() > length * IntegerConsts.TWO) {
                name = "MEDIUMTEXT";
            }
        }

        DataTypeContrastEnums dataType = DataTypeContrastEnums.valueOf(name.toUpperCase());
        entity.setDataType(dataType.getDataType());
        entity.setComment(column.columnDefinition());
        switch (dataType.getDataType()) {
            case "DATETIME":
            case "TIME":
            case "TIMESTAMP":
                entity.setLength(0);
                break;
            default:
                entity.setLength(column.length());
                break;
        }
        entity.setName(column.name());
        entity.setUnique(column.unique());
        entity.setNullable(column.nullable());
        entity.setScale(column.scale());
    }
}
