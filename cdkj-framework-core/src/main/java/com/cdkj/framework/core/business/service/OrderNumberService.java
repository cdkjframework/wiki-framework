package com.cdkj.framework.core.business.service;

import com.cdkj.framework.entity.OrderNumberEntity;
import com.cdkj.framework.exceptions.GlobalException;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.number
 * @ClassName: IOrderNumberService
 * @Description: 单号生成服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface OrderNumberService {

    /**
     * 生成单据号
     *
     * @param prefix 前缀
     * @param length 后缀长度
     * @return 返回单据结果
     * @throws GlobalException 异常信息
     */
    String generateDocumentNumber(String prefix, int length) throws GlobalException, GlobalException;

    /**
     * 生成单据号（无日期）
     *
     * @param prefix 前缀
     * @param length 后缀长度
     * @return 返回单据结果
     * @throws GlobalException 异常信息
     */
    String generateNoDateNumber(String prefix, int length) throws GlobalException;

    /**
     * 查询已存在记录
     *
     * @param prefix 前缀
     * @param length 长度
     * @param init   初始值
     * @return 返回查询结果
     */
    OrderNumberEntity getOrderNumber(String prefix, int length, int init);

    /**
     * 修改增加订单编号记录
     *
     * @param number 修改实体信息
     * @return 返回执行结果
     */
    int updateNextNumber(OrderNumberEntity number);
}
