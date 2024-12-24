package com.cdkjframework.swagger.customizer;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.core.conf.ExtensionsConstants;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.github.xiaoymin.knife4j.extend.util.ExtensionUtils;
import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * @ProjectName: common
 * @Package: com.cdkjframework.swagger.customizer
 * @ClassName: CdkjJakartaOperationCustomizer
 * @Author: xiaLin
 * @Description: JakartaOperation定制器
 * @Date: 2024/9/7 18:00
 * @Version: 1.0
 */
public class CdkjJakartaOperationCustomizer implements GlobalOperationCustomizer {
  /**
   * 自定义操作
   *
   * @param operation     输入操作
   * @param handlerMethod 原始处理程序方法
   * @return 定制操作
   */
  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    ApiOperationSupport operationSupport = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ApiOperationSupport.class);
    if (operationSupport != null) {
      String author = ExtensionUtils.getAuthors(operationSupport);
      if (StrUtil.isNotBlank(author)) {
        operation.addExtension(ExtensionsConstants.EXTENSION_AUTHOR, author);
      }
      if (operationSupport.order() != 0) {
        operation.addExtension(ExtensionsConstants.EXTENSION_ORDER, operationSupport.order());
      }
    } else {
      // 如果方法级别不存在，再找一次class级别的
      ApiSupport apiSupport = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ApiSupport.class);
      if (apiSupport != null) {
        String author = ExtensionUtils.getAuthor(apiSupport);
        if (StrUtil.isNotBlank(author)) {
          operation.addExtension(ExtensionsConstants.EXTENSION_AUTHOR, author);
        }
        if (apiSupport.order() != 0) {
          operation.addExtension(ExtensionsConstants.EXTENSION_ORDER, apiSupport.order());
        }
      }
    }
    return operation;
  }
}
