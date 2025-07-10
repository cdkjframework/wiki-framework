package com.cdkjframework.pay.webchat.app;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.pay.webchat.app
 * @ClassName: AppPayNotifyServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface AppPayNotifyService {

  /**
   * 验证结果常量
   */
  String RESULTS_SUCCESS = "SUCCESS";
  /**
   * 验证结果常量
   */
  String RESULTS_FAIL = "FAIL";

  /**
   * 返回消息
   */
  String RESULTS_MSG = "OK";

  /**
   * 支付结果
   *
   * @param builder 返回结果
   * @return 返回订单号
   * @throws Exception 异常信息
   */
  String payNotifyCallback(StringBuilder builder) throws Exception;
}
