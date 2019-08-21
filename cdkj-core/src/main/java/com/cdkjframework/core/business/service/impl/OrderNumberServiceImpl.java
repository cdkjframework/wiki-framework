package com.cdkjframework.core.business.service.impl;

import com.cdkjframework.core.business.mapper.OrderNumberMapper;
import com.cdkjframework.core.business.service.OrderNumberService;
import com.cdkjframework.entity.generate.OrderNumberEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.number.impl
 * @ClassName: OrderNumberServiceImpl
 * @Description: 单号生成服务
 * @Author: xiaLin
 * @Version: 1.0
 */

@Service
public class OrderNumberServiceImpl implements OrderNumberService {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(OrderNumberServiceImpl.class);

    /**
     * 前缀常量
     */
    private static final String DATE_PATTERN = "yyMMdd";

    /**
     * 单据 mapper
     */
    @Autowired
    private OrderNumberMapper orderNumberMapper;

    /**
     * 生成单据号
     *
     * @param prefix 前缀
     * @param length 后缀长度
     * @return 返回单据结果
     */
    @Override
    public String generateDocumentNumber(String prefix, int length) throws GlobalException {
        //字母+日期+5位流水号
        //日期：年最后2位+月+天
        //客户代码：3位数字
        //采购订单：CG00117072500001
        //销售订单：XS00117072500001
        return generate(prefix, length, 0, true);
    }

    /**
     * 生成单据号（无日期）
     *
     * @param prefix 前缀
     * @param length 后缀长度
     * @return 返回单据结果
     * @throws GlobalException 异常信息
     */
    @Override
    public String generateNoDateNumber(String prefix, int length) throws GlobalException {
        return generate(prefix, length, 0, false);
    }

    /**
     * 查询已存在记录
     *
     * @param prefix 前缀
     * @param length 长度
     * @param init   初始值
     * @return 返回查询结果
     */
    @Override
    public OrderNumberEntity getOrderNumber(String prefix, int length, int init) {

        //查询已存在记录
        OrderNumberEntity number = orderNumberMapper.getOrderNumberByPrefix(prefix);

        //验证是否为空
        if (number == null) {
            //实例化
            number = new OrderNumberEntity(prefix, length, init);
            //添加记录
            orderNumberMapper.addOrderNumber(number);
        }

        //返回结果
        return number;
    }

    /**
     * 修改增加订单编号记录
     *
     * @param number 修改实体信息
     * @return 返回执行结果
     */
    @Override
    public int updateNextNumber(OrderNumberEntity number) {
        return orderNumberMapper.updateNextOrderNumber(number);
    }

    /**
     * 单号生成
     *
     * @param prefix 前缀
     * @param length 长度
     * @param init   初始值
     * @param isDate 是否加时间
     * @return 返回单号
     * @throws GlobalException 异常信息
     */
    private String generate(String prefix, int length, int init, boolean isDate) throws GlobalException {
        final int error = 5;
        //生成单号
        String number = autoRetryGenerate(prefix, length, init, error, isDate);

        //验证单号
        if (StringUtils.isNullAndSpaceOrEmpty(number)) {
            throw new GlobalException(String.format("获取单号失败[%s]", prefix));
        }

        //返回单号结果
        return number;
    }

    /**
     * 自动生成并重试
     *
     * @param prefix 前缀
     * @param length 长度
     * @param init   初始值
     * @param err    最多生成次数
     * @param isDate 是否加时间
     * @return
     */
    private String autoRetryGenerate(String prefix, int length, int init, int err, boolean isDate) {
        int error = 0;
        while (error < err) {
            try {
                //生成前缀信息
                StringBuffer buffer = new StringBuffer(prefix);
                if (isDate) {
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
                    buffer.append(sdf.format(new Date()));
                }

                //获取单号记录
                OrderNumberEntity number = this.getOrderNumber(buffer.toString(), length, init);
                //修改增加单号值
                int target = this.updateNextNumber(number);
                if (target > 0) {
                    //生成单号
                    return buffer.append(number.createNextNo()).toString();
                } else {
                    TimeUnit.MILLISECONDS.sleep(200);
                    throw new GlobalException("生成单号失败");
                }
            } catch (Exception ex) {
                error++;
                String errorMessage = String.format("第%s次获取单号失败[%s]，%s", String.valueOf(error), prefix, ex.getMessage());
                logUtil.error(errorMessage);
            }
        }

        //返回 null 说明生成单号失败
        return null;
    }
}
