package com.cdkjframework.util.tool.number;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.RegexUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.cdkjframework.util.tool.StringUtils.isNullAndSpaceOrEmpty;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ConvertUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ConvertUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(ConvertUtils.class);

    /**
     * 将值转换为整数
     *
     * @param obj 值
     * @return 返回结果
     */
    public static int convertInt(Object obj) {
        int value = 0;
        try {
            //验证是否为数字
            if (!dataNumberType(obj)) {
                return value;
            }

            value = Integer.valueOf(obj.toString());
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        //返回结果
        return value;
    }

    /**
     * 将值转换为浮动
     *
     * @param obj 值
     * @return 返回结果
     */
    public static Float convertFloat(Object obj) {
        Float value = 0F;
        try {
            //验证是否为数字
            if (!dataNumberType(obj)) {
                return value;
            }

            value = Float.valueOf(obj.toString());
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        //返回结果
        return value;
    }

    /**
     * 将值转换为双精度
     *
     * @param obj 值
     * @return 返回结果
     */
    public static Double convertDouble(Object obj) {
        Double value = 0D;
        try {
            //验证是否为数字
            if (!dataNumberType(obj)) {
                return value;
            }

            value = Double.valueOf(obj.toString());
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        //返回结果
        return value;
    }

    /**
     * 将值转换为大十进制
     *
     * @param obj 值
     * @return 返回结果
     */
    public static BigDecimal convertDecimal(Object obj) {
        BigDecimal value = BigDecimal.ZERO;
        try {
            value = BigDecimal.valueOf(convertDouble(obj));
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        //返回结果
        return value;
    }

    /**
     * 转换为长整形
     *
     * @param obj 数据
     * @return 返回结果
     */
    public static Long convertLong(Object obj) {
        Long value = 0L;
        try {
            //验证是否为数字
            if (!dataNumberType(obj)) {
                return value;
            }

            value = Long.valueOf(obj.toString());
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        //返回结果
        return value;
    }

    /**
     * 将值转换为布尔
     *
     * @param obj 值
     * @return 返回结果
     */
    public static Boolean convertBoolean(Object obj) {
        Boolean value = false;
        try {
            //验证是否为数字
            if (dataNumberType(obj.toString())) {
                int intValue = convertInt(obj);
                return intValue > 0;
            }
            //直接转换
            value = Boolean.valueOf(obj.toString());
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        //返回结果
        return value;
    }

    /**
     * 转换为字段串类型
     *
     * @param obj 数据
     * @return 返回结果
     */
    public static String convertString(Object obj) {
        if (StringUtils.isNullAndSpaceOrEmpty(obj)) {
            return "";
        }

        // 返回结果
        return String.valueOf(obj);
    }

    /**
     * 数据类型判断
     *
     * @param obj 值
     * @return 返回结果
     */
    private static boolean dataNumberType(Object obj) {
        //是否为空
        if (isNullAndSpaceOrEmpty(obj)) {
            return false;
        }
        //验证是否为数字
        return RegexUtils.isNumber(String.valueOf(obj)) ||
                RegexUtils.isFloatPoint(String.valueOf(obj));
    }

}
