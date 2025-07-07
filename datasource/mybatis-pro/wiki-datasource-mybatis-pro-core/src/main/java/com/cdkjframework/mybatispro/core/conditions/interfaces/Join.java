package com.cdkjframework.mybatispro.core.conditions.interfaces;

import java.io.Serializable;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions.interfaces
 * @ClassName: Join
 * @Description: 查询条件封装
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public interface Join<Children> extends Serializable {

    /**
     * 拼接 OR
     *
     * @return children
     */
    default Children or() {
        return or(true);
    }

    /**
     * 拼接 OR
     *
     * @param condition 执行条件
     * @return children
     */
    Children or(boolean condition);

    /**
     * 拼接 sql
     * <p>!! 会有 sql 注入风险 !!</p>
     * <p>例1: apply("id = 1")</p>
     * <p>例2: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")</p>
     * <p>例3: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", LocalDate.now())</p>
     * <p>例4: apply("type={0,javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler}", "待处理字符串")</p>
     *
     * @param values 数据数组
     * @return children
     */
    default Children apply(String applySql, Object... values) {
        return apply(true, applySql, values);
    }

    /**
     * 拼接 sql
     * <p>!! 会有 sql 注入风险 !!</p>
     * <p>例1: apply("id = 1")</p>
     * <p>例2: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")</p>
     * <p>例3: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", LocalDate.now())</p>
     * <p>例4: apply("type={0,javaType=int,jdbcType=NUMERIC,typeHandler=xxx.xxx.MyTypeHandler}", "待处理字符串")</p>
     *
     * @param condition 执行条件
     * @param values    数据数组
     * @return children
     */
    Children apply(boolean condition, String applySql, Object... values);

    /**
     * 无视优化规则直接拼接到 sql 的最后(有sql注入的风险,请谨慎使用)
     * <p>例: last("limit 1")</p>
     * <p>注意只能调用一次,多次调用以最后一次为准</p>
     *
     * @param lastSql sql语句
     * @return children
     */
    default Children last(String lastSql) {
        return last(true, lastSql);
    }

    /**
     * 无视优化规则直接拼接到 sql 的最后(有sql注入的风险,请谨慎使用)
     * <p>例: last("limit 1")</p>
     * <p>注意只能调用一次,多次调用以最后一次为准</p>
     *
     * @param condition 执行条件
     * @param lastSql   sql语句
     * @return children
     */
    Children last(boolean condition, String lastSql);

    /**
     * sql 注释(会拼接在 sql 的最后面)
     *
     * @param comment sql注释
     * @return children
     */
    default Children comment(String comment) {
        return comment(true, comment);
    }

    /**
     * sql 注释(会拼接在 sql 的最后面)
     *
     * @param condition 执行条件
     * @param comment   sql注释
     * @return children
     */
    Children comment(boolean condition, String comment);

    /**
     * sql 起始句（会拼接在SQL语句的起始处）
     *
     * @param firstSql 起始语句
     * @return children
     */
    default Children first(String firstSql) {
        return first(true, firstSql);
    }

    /**
     * sql 起始句（会拼接在SQL语句的起始处）
     *
     * @param condition 执行条件
     * @param firstSql  起始语句
     * @return children
     */
    Children first(boolean condition, String firstSql);

    /**
     * 拼接 EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: exists("select id from table where age = 1")</p>
     *
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    default Children exists(String existsSql, Object... values) {
        return exists(true, existsSql, values);
    }

    /**
     * 拼接 EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: exists("select id from table where age = 1")</p>
     *
     * @param condition 执行条件
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    Children exists(boolean condition, String existsSql, Object... values);

    /**
     * 拼接 NOT EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: notExists("select id from table where age = 1")</p>
     *
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    default Children notExists(String existsSql, Object... values) {
        return notExists(true, existsSql, values);
    }

    /**
     * 拼接 NOT EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: notExists("select id from table where age = 1")</p>
     *
     * @param condition 执行条件
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    Children notExists(boolean condition, String existsSql, Object... values);
}
