package com.cdkjframework.datasource.rw.source;

import com.cdkjframework.datasource.rw.holder.DynamicDataSourceHolder;
import com.cdkjframework.enums.datasource.DynamicDataSourceGlobal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.rw.source
 * @ClassName: DynamicDataSource
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 返回分配的数据库的key
     *
     * @return 返回结果
     */
    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
        if (dynamicDataSourceGlobal == null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE) {
            return DynamicDataSourceGlobal.WRITE.name();
        }
        return DynamicDataSourceGlobal.READ.name();
    }
}
