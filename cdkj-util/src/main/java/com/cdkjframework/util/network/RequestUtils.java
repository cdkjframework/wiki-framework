package com.cdkjframework.util.network;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.FieldMappingUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
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
        int len = -1;
        // 缓冲区
        byte[] bytes = new byte[1024];
        // 捕获内存缓冲区的数据转换为字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 循环读取内容,将输入流的内容放进缓冲区中
        while ((len = stream.read(bytes)) != IntegerConsts.MINUS_ONE) {
            // 将缓冲区内容写进输出流，0是从起始偏移量，len是指定的字符个数
            baos.write(bytes, IntegerConsts.ZERO, len);
        }
        // 最终结果，将字节数组转换
        return new String(baos.toByteArray());
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
            t = clazz.newInstance();

            // 获取目标参数所有字段
            List<Field> fieldList = ReflectionUtils.getDeclaredFields(clazz);
            for (Field field : fieldList) {
                field.setAccessible(true);
                //从注解中获取目标字段
                String fieldName = FieldMappingUtils.getFieldNameByField(field);
                // 参数值
                String value = request.getParameter(fieldName);
                if (StringUtils.isNullAndSpaceOrEmpty(value)) {
                    continue;
                }
                String targetFieldName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
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
        }

        return t;
    }
}