package com.cdkjframework.entity.search;

import com.cdkjframework.entity.base.MongoBaseEntity;
import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.search
 * @ClassName: SearchEnginesEntity
 * @Author: frank
 * @Version: 1.0
 * @Description: 搜索引擎
 */
@Data
public class SearchEnginesEntity extends MongoBaseEntity {

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 业务 名称
     */
    private String businessName;

    /**
     * 业务 编码(编号)
     */
    private String businessNo;

    /**
     * 检索次数
     */
    private long searchesNumber;
}
