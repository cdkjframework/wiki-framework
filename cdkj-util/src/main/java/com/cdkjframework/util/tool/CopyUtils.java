package com.cdkjframework.util.tool;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: CopyUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CopyUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(CopyUtils.class);

    /**
     * 获取到空值列
     *
     * @param source 原数据源
     * @return 返回结果
     */
    public static String[] getNullPropertyNames(Object source) {
        //包装 bean
        final BeanWrapper src = new BeanWrapperImpl(source);
        //获取属性描述符
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        //记录信息
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 拷贝 list 数据
     *
     * @param list   源数据
     * @param target 要转换成的对象
     * @return 返回对象数据集
     */
    public static List copyPropertiesList(List<?> list, Class target) {
        List<Object> result = new ArrayList();
        if (list != null) {
            for (Object o : list) {
                try {

                    Object d = target.newInstance();
                    CopyUtils.copyProperties(o, d);
                    result.add(d);
                } catch (Exception e) {
                    logUtil.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
}
