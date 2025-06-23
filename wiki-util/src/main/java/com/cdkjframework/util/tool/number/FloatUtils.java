package com.cdkjframework.util.tool.number;

import org.springframework.stereotype.Component;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.tool.number
 * @ClassName: FloatUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class FloatUtils {

    /**
     * float数据相加
     *
     * @param floatList 数据列表
     * @return 返回相加结果
     */
    public static float addition(float... floatList) {
        float result = 0;
        // 验证是否有数据
        if (floatList == null || floatList.length == 0) {
            return result;
        }

        // 值相加
        for (float value :
                floatList) {
            result = result + ConvertUtils.convertFloat(value);
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
    public static float divide(float dividend, float divisor) {
        final float targetDigital = 0;
        if (ConvertUtils.convertFloat(dividend) == targetDigital ||
                ConvertUtils.convertFloat(divisor) == targetDigital) {
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
    public static float multiply(float multiplier, float multiplicand) {
        final float targetDigital = 0;
        if (ConvertUtils.convertFloat(multiplier) == targetDigital ||
                ConvertUtils.convertFloat(multiplicand) == targetDigital) {
            return targetDigital;
        }

        //返回结果
        return multiplier / multiplicand;
    }
}
