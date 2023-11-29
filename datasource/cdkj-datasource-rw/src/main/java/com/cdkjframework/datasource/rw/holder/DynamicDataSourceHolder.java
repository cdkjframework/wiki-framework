package com.cdkjframework.datasource.rw.holder;

import com.cdkjframework.enums.datasource.DynamicDataSourceGlobal;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.datasource.rw.holder
 * @ClassName: DynamicDataSourceHolder
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public final class DynamicDataSourceHolder {

    /**
     * 本地线程
     */
    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<>();

    /**
     * 构造函数
     */
    private DynamicDataSourceHolder() {
    }

    /**
     * 放置数据源
     *
     * @param dataSource 数据源
     */
    public static void putDataSource(DynamicDataSourceGlobal dataSource) {
        holder.set(dataSource);
    }

    /**
     * 获取数据源枚举
     *
     * @return 返回数据源类型
     */
    public static DynamicDataSourceGlobal getDataSource() {
        return holder.get();
    }

    /**
     * 清空数据源
     */
    public static void clearDataSource() {
        holder.remove();
    }
}
