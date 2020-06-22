package com.cdkjframework.datasource.rw.transaction;

import com.cdkjframework.datasource.rw.holder.DynamicDataSourceHolder;
import com.cdkjframework.enums.datasource.DynamicDataSourceGlobal;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.datasource.rw.transaction
 * @ClassName: DynamicDataSourceTransactionManager
 * @Description: 动态数据源事务管理
 * @Author: xiaLin
 * @Version: 1.0
 */
@SuppressWarnings("serial")
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    /**
     * 只读事务到读库，读写事务到写库
     *
     * @param transaction 事务
     * @param definition  定义
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        // 设置数据源
        boolean readOnly = definition.isReadOnly();
        if (readOnly) {
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.READ);
        } else {
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    /**
     * 清理本地线程的数据源
     *
     * @param transaction 事务
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DynamicDataSourceHolder.clearDataSource();
    }
}
