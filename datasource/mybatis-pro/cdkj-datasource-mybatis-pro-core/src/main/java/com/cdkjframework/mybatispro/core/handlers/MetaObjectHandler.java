package com.cdkjframework.mybatispro.core.handlers;

import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 元对象字段填充控制器抽象类，实现公共字段自动写入<p>
 * <p>
 * 所有入参的 MetaObject 必定是 entity 或其子类的 MetaObject
 *
 */
public interface MetaObjectHandler {

    /**
     * 是否开启了插入填充
     *
     * @deprecated 3.5.6 {@link #openInsertFill(MappedStatement)}
     */
    @Deprecated
    default boolean openInsertFill() {
        return true;
    }

    /**
     * 是否开启插入填充
     *
     * @param mappedStatement {@link MappedStatement}
     * @return 是否开启
     */
    default boolean openInsertFill(MappedStatement mappedStatement) {
        return true;
    }

    /**
     * 是否开启了更新填充
     *
     * @deprecated 3.5.6 {@link #openUpdateFill(MappedStatement)}
     */
    @Deprecated
    default boolean openUpdateFill() {
        return true;
    }

    /**
     * 是否开启了更新填充
     *
     * @param mappedStatement {@link MappedStatement}
     * @return 是否开启
     */
    default boolean openUpdateFill(MappedStatement mappedStatement) {
        return true;
    }

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    void insertFill(MetaObject metaObject);

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    void updateFill(MetaObject metaObject);

    /**
     * 通用填充
     *
     * @param fieldName  java bean property name
     * @param fieldVal   java bean property value
     * @param metaObject meta object parameter
     */
    default MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (Objects.nonNull(fieldVal) && metaObject.hasSetter(fieldName)) {
            metaObject.setValue(fieldName, fieldVal);
        }
        return this;
    }

    /**
     * get value from java bean by propertyName
     *
     * @param fieldName  java bean property name
     * @param metaObject parameter wrapper
     * @return 字段值
     */
    default Object getFieldValByName(String fieldName, MetaObject metaObject) {
        return metaObject.hasGetter(fieldName) ? metaObject.getValue(fieldName) : null;
    }

    /**
     * find the tableInfo cache by metaObject </p>
     * 获取 TableInfo 缓存
     *
     * @param metaObject meta object parameter
     * @return TableInfo
     */
    default TableInfo findTableInfo(MetaObject metaObject) {
        return TableInfoHelper.getTableInfo(metaObject.getOriginalObject().getClass());
    }

    /**
     * @param metaObject metaObject meta object parameter
     * @return this
     * @since 3.3.0
     */
    default <T, E extends T> MetaObjectHandler strictInsertFill(MetaObject metaObject, String fieldName, Class<T> fieldType, E fieldVal) {
        return strictInsertFill(findTableInfo(metaObject), metaObject, Collections.singletonList(StrictFill.of(fieldName, fieldType, fieldVal)));
    }

    /**
     * @param metaObject metaObject meta object parameter
     * @return this
     * @since 3.3.0
     */
    default <T, E extends T> MetaObjectHandler strictInsertFill(MetaObject metaObject, String fieldName, Supplier<E> fieldVal, Class<T> fieldType) {
        return strictInsertFill(findTableInfo(metaObject), metaObject, Collections.singletonList(StrictFill.of(fieldName, fieldVal, fieldType)));
    }

    /**
     * @param metaObject metaObject meta object parameter
     * @return this
     * @since 3.3.0
     */
    default MetaObjectHandler strictInsertFill(TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        return strictFill(true, tableInfo, metaObject, strictFills);
    }

    /**
     * @param metaObject metaObject meta object parameter
     * @return this
     * @since 3.3.0
     */
    default <T, E extends T> MetaObjectHandler strictUpdateFill(MetaObject metaObject, String fieldName, Supplier<E> fieldVal, Class<T> fieldType) {
        return strictUpdateFill(findTableInfo(metaObject), metaObject, Collections.singletonList(StrictFill.of(fieldName, fieldVal, fieldType)));
    }

    /**
     * @param metaObject metaObject meta object parameter
     * @return this
     * @since 3.3.0
     */
    default <T, E extends T> MetaObjectHandler strictUpdateFill(MetaObject metaObject, String fieldName, Class<T> fieldType, E fieldVal) {
        return strictUpdateFill(findTableInfo(metaObject), metaObject, Collections.singletonList(StrictFill.of(fieldName, fieldType, fieldVal)));
    }

    /**
     * @param metaObject metaObject meta object parameter
     * @return this
     * @since 3.3.0
     */
    default MetaObjectHandler strictUpdateFill(TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        return strictFill(false, tableInfo, metaObject, strictFills);
    }

    /**
     * 严格填充,只针对非主键的字段,只有该表注解了fill 并且 字段名和字段属性 能匹配到才会进行填充(null 值不填充)
     *
     * @param insertFill  是否验证在 insert 时填充
     * @param tableInfo   cache 缓存
     * @param metaObject  metaObject meta object parameter
     * @param strictFills 填充信息
     * @return this
     * @since 3.3.0
     */
    default MetaObjectHandler strictFill(boolean insertFill, TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        if ((insertFill && tableInfo.isWithInsertFill()) || (!insertFill && tableInfo.isWithUpdateFill())) {
            strictFills.forEach(i -> {
                final String fieldName = i.getFieldName();
                final Class<?> fieldType = i.getFieldType();
                tableInfo.getFieldList().stream()
                    .filter(j -> j.getProperty().equals(fieldName) && fieldType.equals(j.getPropertyType()) &&
                        ((insertFill && j.isWithInsertFill()) || (!insertFill && j.isWithUpdateFill()))).findFirst()
                    .ifPresent(j -> strictFillStrategy(metaObject, fieldName, i.getFieldVal()));
            });
        }
        return this;
    }

    /**
     * 填充策略,默认有值不覆盖,如果提供的值为null也不填充
     *
     * @param metaObject metaObject meta object parameter
     * @param fieldName  java bean property name
     * @param fieldVal   java bean property value of Supplier
     * @return this
     * @since 3.3.0
     */
    default MetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
        if (getFieldValByName(fieldName, metaObject) == null) {
            setFieldValByName(fieldName, fieldVal, metaObject);
        }
        return this;
    }

    /**
     * 严格模式填充策略,默认有值不覆盖,如果提供的值为null也不填充
     *
     * @param metaObject metaObject meta object parameter
     * @param fieldName  java bean property name
     * @param fieldVal   java bean property value of Supplier
     * @return this
     * @since 3.3.0
     */
    default MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        if (metaObject.getValue(fieldName) == null) {
            Object obj = fieldVal.get();
            if (Objects.nonNull(obj)) {
                metaObject.setValue(fieldName, obj);
            }
        }
        return this;
    }
}
