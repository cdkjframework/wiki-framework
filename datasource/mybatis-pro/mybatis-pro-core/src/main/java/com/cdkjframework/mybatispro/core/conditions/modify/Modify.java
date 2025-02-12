package com.cdkjframework.mybatispro.core.conditions.modify;

import java.io.Serializable;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions.modify
 * @ClassName: Modify
 * @Description: 修改接口
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public interface Modify<Children, R> extends Serializable {

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param column 字段
     * @param val    值
     * @return children
     */
    default Children set(R column, Object val) {
        return set(true, column, val);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param condition 是否加入 set
     * @param column    字段
     * @param val       值
     * @return children
     */
    default Children set(boolean condition, R column, Object val) {
        return set(condition, column, val, null);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param column  字段
     * @param val     值
     * @param mapping 例: javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler
     * @return children
     */
    default Children set(R column, Object val, String mapping) {
        return set(true, column, val, mapping);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param condition 是否加入 set
     * @param column    字段
     * @param val       值
     * @param mapping   例: javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler
     * @return children
     */
    Children set(boolean condition, R column, Object val, String mapping);

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param setSql set sql
     *               例1: setSql("id=1")
     *               例2: setSql("dateColumn={0}", LocalDate.now())
     *               例4: setSql("type={0,javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler}", "待处理字符串")
     * @return children
     */
    default Children setSql(String setSql, Object... params) {
        return setSql(true, setSql, params);
    }

    /**
     * 设置 更新 SQL 的 SET 片段
     *
     * @param condition 执行条件
     * @param setSql    set sql
     *                  例1: setSql("id=1")
     *                  例2: setSql("dateColumn={0}", LocalDate.now())
     *                  例4: setSql("type={0,javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler}", "待处理字符串")
     * @return children
     */
    Children setSql(boolean condition, String setSql, Object... params);

    /**
     * 字段自增变量 val 值
     *
     * @param column 字段
     * @param val    变量值 1 字段自增 + 1
     */
    default Children setIncrBy(R column, Number val) {
        return setIncrBy(true, column, val);
    }

    /**
     * 字段自增变量 val 值
     *
     * @param condition 是否加入 set
     * @param column    字段
     * @param val       变量值 1 字段自增 + 1
     */
    Children setIncrBy(boolean condition, R column, Number val);

    /**
     * 字段自减变量 val 值
     *
     * @param column 字段
     * @param val    变量值 1 字段自减 - 1
     */
    default Children setDecrBy(R column, Number val) {
        return setDecrBy(true, column, val);
    }

    /**
     * 字段自减变量 val 值
     *
     * @param condition 是否加入 set
     * @param column    字段
     * @param val       变量值 1 字段自减 - 1
     */
    Children setDecrBy(boolean condition, R column, Number val);

    /**
     * 获取 更新 SQL 的 SET 片段
     */
    String getSqlSet();
}
