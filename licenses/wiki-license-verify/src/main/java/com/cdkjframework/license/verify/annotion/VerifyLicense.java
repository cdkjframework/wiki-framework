package com.cdkjframework.license.verify.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: com.cdkjframework
 * @Package: com.lesmarthome.bms.client
 * @ClassName: VerifyLicense
 * @Author: xiaLin
 * @Version: 1.0
 * @Description: 验证  Lic
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyLicense {

  /**
   * 推送范围
   *
   * @return 返回组
   */
  String[] verifies() default {};
}
