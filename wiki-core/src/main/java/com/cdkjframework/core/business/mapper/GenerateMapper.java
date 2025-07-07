package com.cdkjframework.core.business.mapper;

import com.cdkjframework.entity.generate.template.DatabaseEntity;
import com.cdkjframework.entity.generate.template.TableColumnEntity;
import com.cdkjframework.entity.generate.template.TableEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.mapper
 * @ClassName: GenerateMapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public interface GenerateMapper {

    /**
     * 获取当前用户数据库
     *
     * @return 返回结果
     */
    List<DatabaseEntity> findDatabase();
    /**
     * 获取当前用户数据库
     *
     * @return 返回结果
     */
    List<DatabaseEntity> findDatabaseByPostgre();

    /**
     * 获取数据库
     *
     * @return 返回结果
     */
    List<DatabaseEntity> findDatabaseList();

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    List<TableEntity> findDatabaseTableList(TableEntity tableEntity);
    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    List<TableEntity> findDatabaseTableListByPostgre(TableEntity tableEntity);

    /**
     * 获取数据库表
     *
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    List<TableColumnEntity> findTableColumnList(TableColumnEntity columnEntity);

    /**
     * 获取数据库表
     *
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    List<TableColumnEntity> findTableColumnListByPostgre(TableColumnEntity columnEntity);
}
