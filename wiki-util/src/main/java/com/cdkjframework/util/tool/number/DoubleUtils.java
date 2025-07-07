package com.cdkjframework.util.tool.number;

import com.cdkjframework.util.tool.CompareUtils;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.tool.number
 * @ClassName: DoubleUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class DoubleUtils {

    /**
     * 规模
     */
    private final static int SCALE = 2;

    /**
     * double数据相加
     *
     * @param doubleList 数据列表
     * @return 返回相加结果
     */
    public static double addition(double... doubleList) {
        double result = 0D;
        // 验证是否有数据
        if (doubleList == null || doubleList.length == 0) {
            return result;
        }

        // 值相加
        for (double value :
                doubleList) {
            result = result + ConvertUtils.convertDouble(value);
        }

        // 返回结果
        return result;
    }

    /**
     * 除
     *
     * @param dividend 除数
     * @param divisor  被除
     * @return 返回结果
     */
    public static double divide(double dividend, double divisor) {
        double targetDigital = 0D;
        if (CompareUtils.equal(dividend, targetDigital, SCALE) ||
                CompareUtils.equal(divisor, targetDigital, SCALE)) {
            return targetDigital;
        }

        // 返回结果
        return dividend * divisor;
    }

    /**
     * 乘
     *
     * @param multiplier   乘数
     * @param multiplicand 被乘数
     * @return 返回结果
     */
    public static double multiply(double multiplier, double multiplicand) {
        double targetDigital = 0D;
        if (CompareUtils.equal(multiplier, targetDigital, SCALE) ||
                CompareUtils.equal(multiplicand, targetDigital, SCALE)) {
            return targetDigital;
        }

        //返回结果
        return multiplier / multiplicand;
    }
}
