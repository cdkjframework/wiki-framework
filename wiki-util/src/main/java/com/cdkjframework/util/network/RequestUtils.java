package com.cdkjframework.util.network;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.FieldMappingUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.util.http
 * @ClassName: HttpRequestUtil
 * @Description: HTTP请求
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class RequestUtils {

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(RequestUtils.class);

  /**
   * 请求字节流转换为字符串
   *
   * @param stream 流
   * @return 返回字符串
   */
  public static String inputStreamToString(InputStream stream) throws IOException {
    // 初始值，起标志位作用
    int len;
    // 缓冲区
    byte[] bytes = new byte[IntegerConsts.BYTE_LENGTH];
    // 捕获内存缓冲区的数据转换为字节数组
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // 循环读取内容,将输入流的内容放进缓冲区中
    while ((len = stream.read(bytes)) != IntegerConsts.MINUS_ONE) {
      // 将缓冲区内容写进输出流，0是从起始偏移量，len是指定的字符个数
      outputStream.write(bytes, IntegerConsts.ZERO, len);
    }
    // 最终结果，将字节数组转换
    return new String(outputStream.toByteArray());
  }

  /**
   * 参数转换为实体
   *
   * @param request 请求
   * @param clazz   实体类
   * @param <T>     类型
   * @return 返回实体
   */
  public static <T> T parameterToEntity(HttpServletRequest request, Class<T> clazz) {
    T t;
    try {
      t = clazz.getDeclaredConstructor().newInstance();

      // 获取目标参数所有字段
      List<Field> fieldList = ReflectionUtils.getDeclaredFields(clazz);
      final String SET = "set";
      for (Field field : fieldList) {
        field.setAccessible(true);
        //从注解中获取目标字段
        String fieldName = FieldMappingUtils.getFieldNameByField(field);
        // 参数值
        String value = request.getParameter(fieldName);
        if (StringUtils.isNullAndSpaceOrEmpty(value)) {
          continue;
        }
        String targetFieldName = SET + fieldName
            .substring(IntegerConsts.ZERO, IntegerConsts.ONE).toUpperCase() + fieldName.substring(IntegerConsts.ONE);
        //获取数据类型
        String dataType = field.getType().getName();
        // 设置值
        ReflectionUtils.invokeMethod(t, targetFieldName,
            new Class[]{field.getType()}, new Object[]{value}, dataType);
      }
    } catch (InstantiationException e) {
      logUtils.error(e.getMessage());
      t = null;
    } catch (IllegalAccessException e) {
      logUtils.error(e.getMessage());
      t = null;
    } catch (InvocationTargetException e) {
      logUtils.error(e.getMessage());
      t = null;
    } catch (NoSuchMethodException e) {
      logUtils.error(e.getMessage());
      t = null;
    }

    return t;
  }
}