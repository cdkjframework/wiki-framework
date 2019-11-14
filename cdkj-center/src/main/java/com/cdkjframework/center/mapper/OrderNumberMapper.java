package com.cdkjframework.center.mapper;

import com.cdkjframework.entity.generate.OrderNumberEntity;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.number.mapper
 * @ClassName: OrderNumberMapper
 * @Description: 单号 mapper
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface OrderNumberMapper {

    /**
     * 获取订单信息
     *
     * @param prefix 前缀
     * @return 返回结果
     */
    OrderNumberEntity getOrderNumberByPrefix(String prefix);

    /**
     * 添加记录
     *
     * @param entity 实体
     * @return 返回执行结果
     */
    int addOrderNumber(OrderNumberEntity entity);

    /**
     * 修改增加单号值
     *
     * @param entity 实体
     * @return 返回执行结果
     */
    int updateNextOrderNumber(OrderNumberEntity entity);
}
