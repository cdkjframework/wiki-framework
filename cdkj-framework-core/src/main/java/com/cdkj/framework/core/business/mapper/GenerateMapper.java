package com.cdkj.framework.core.business.mapper;

import com.cdkj.framework.entity.generate.DatabaseEntity;
import com.cdkj.framework.entity.generate.TableColumnEntity;
import com.cdkj.framework.entity.generate.TableEntity;

import java.util.List;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.core.business.mapper
 * @ClassName: GenerateMapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface GenerateMapper {

    /**
     * 获取当前用户数据库
     *
     * @return 返回结果
     */
    List<DatabaseEntity> findDatabase();

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
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    List<TableColumnEntity> findTableColumnList(TableColumnEntity columnEntity);
}
