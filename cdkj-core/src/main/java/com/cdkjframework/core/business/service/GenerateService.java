package com.cdkjframework.core.business.service;

import com.cdkjframework.entity.generate.template.DatabaseEntity;
import com.cdkjframework.entity.generate.template.TableColumnEntity;
import com.cdkjframework.entity.generate.template.TableEntity;
import com.cdkjframework.entity.generate.template.TreeEntity;

import java.util.List;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.business.service
 * @ClassName: GenerateService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface GenerateService {

    /**
     * 获取数据库
     *
     * @return 返回结果
     */
    DatabaseEntity findDatabase();

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    List<TreeEntity> findDatabaseTableList(TableEntity tableEntity);

    /**
     * 获取数据库表
     *
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    List<TableColumnEntity> findTableColumnList(TableColumnEntity columnEntity);

    /**
     * 生成业务代码
     *
     * @param entityList 选择结果
     * @param dataBase   数据库
     * @return 返回是否成功
     */
    Boolean generateCode(List<TreeEntity> entityList, String dataBase);
}
