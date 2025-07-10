package com.cdkjframework.pay;


import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.dto.PaymentResultDto;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @ProjectName: cdkjframework.pay
 * @Package: com.cdkjframework.pay.qrcode
 * @ClassName: PaymentInterfaceService
 * @Description: 支付接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PaymentService<T> {

  /**
   * 构造支付订单
   *
   * @param businessNo 业务单号
   * @param request    请求信息
   * @return 订单信息
   * @throws Exception 异常信息
   */
  PaymentResultDto buildPayOrder(String businessNo, HttpServletRequest request) throws Exception;

  /**
   * 生成支付订单及完成支付
   *
   * @param configEntity 支付配置
   * @param recordEntity 支付记录信息
   * @throws GlobalException 异常信息
   */
  void buildPayOrderRecord(PayConfigEntity configEntity, PayRecordEntity recordEntity) throws GlobalException;

  /**
   * 获取支付连接
   *
   * @param t            实体
   * @param configEntity 配置
   * @param recordEntity 支付记录
   * @throws Exception 异常信息
   */
  void buildPaymentConnection(T t, PayConfigEntity configEntity, PayRecordEntity recordEntity) throws Exception;

  /**
   * 生成二维码并返回 base64 编码
   *
   * @param address 支付地址
   * @return 返回结果
   */
  String generateQrCode(String address);

  /**
   * 生成支付配置
   *
   * @param t            实体对象
   * @param configEntity 配置信息
   * @param recordEntity 记录信息
   * @param request      请求信息
   * @throws Exception 异常信息
   */
  void buildPaymentData(T t, PayConfigEntity configEntity, PayRecordEntity recordEntity, HttpServletRequest request) throws Exception;

  /**
   * 支付查询结果
   *
   * @param configEntity 配置
   * @param recordEntity 支付记录
   * @return 返回结果
   */
  boolean paymentQueryResults(PayConfigEntity configEntity, PayRecordEntity recordEntity);


}
