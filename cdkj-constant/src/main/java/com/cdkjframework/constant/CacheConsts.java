package com.cdkjframework.constant;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.consts
 * @ClassName: CacheConstant
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CacheConsts {


  /**
   * 用户缓存前缀
   */
  public static final String USER_PREFIX = "user:";

  /**
   * 用户登录缓存
   */
  public static final String USER_LOGIN = USER_PREFIX + "userLogin-";

  /**
   * 用户资源
   */
  public static final String USER_RESOURCE = USER_PREFIX + "resource-";

  /**
   * 用户资源
   */
  public static final String USER_WECHAT_MP = USER_PREFIX + "mp-%s";

  /**
   * 支付配置
   */
  public static final String PAY_CONFIG = "payConfig";

  /**
   * 验证码缓存
   */
  public static final String VERIFY_CODE = "verifyCode";

  /**
   * 登录验证码
   */
  public static final String LOGIN_VERIFY_CODE = "loginVerifyCode";

}
