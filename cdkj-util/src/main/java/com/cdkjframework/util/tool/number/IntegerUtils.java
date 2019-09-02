package com.cdkjframework.util.tool.number;

import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool.number
 * @ClassName: IntegerUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class IntegerUtils {

    /**
     * Integer数据相加
     *
     * @param IntegerList 数据列表
     * @return 返回相加结果
     */
    public static Integer addition(Integer... IntegerList) {
        Integer result = 0;
        // 验证是否有数据
        if (IntegerList == null || IntegerList.length == 0) {
            return result;
        }

        // 值相加
        for (Integer value :
                IntegerList) {
            result = result + ConvertUtils.convertInt(value);
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
    public static Integer divide(Integer dividend, Integer divisor) {
        final Integer targetDigital = 0;
        if (ConvertUtils.convertInt(dividend) == targetDigital ||
                ConvertUtils.convertInt(divisor) == targetDigital) {
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
    public static Integer multiply(Integer multiplier, Integer multiplicand) {
        final Integer targetDigital = 0;
        if (ConvertUtils.convertInt(multiplier) == targetDigital ||
                ConvertUtils.convertInt(multiplicand) == targetDigital) {
            return targetDigital;
        }

        //返回结果
        return multiplier / multiplicand;
    }
}
