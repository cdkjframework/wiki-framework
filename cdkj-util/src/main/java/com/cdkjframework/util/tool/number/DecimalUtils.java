package com.cdkjframework.util.tool.number;

import com.cdkjframework.util.tool.CompareUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ProjectName: oto-tms-parent
 * @Package: com.scli.oto.tms.local.common.util
 * @ClassName: DecimalUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class DecimalUtils {

    /**
     * decimal数据相加
     *
     * @param decimalList 数据列表
     * @return 返回相加结果
     */
    public static BigDecimal addition(BigDecimal... decimalList) {
        // 返回结果
        return addition(Integer.valueOf("2"), decimalList);
    }

    /**
     * decimal数据相加
     *
     * @param decimalList 数据列表
     * @return 返回相加结果
     */
    public static BigDecimal addition(int scale, BigDecimal... decimalList) {
        BigDecimal result = BigDecimal.ZERO;
        // 验证是否有数据
        if (decimalList == null || decimalList.length == 0) {
            return result;
        }

        // 值相加
        for (BigDecimal value :
                decimalList) {
            result = result.add(ConvertUtils.convertDecimal(value));
        }

        // 返回结果
        return result.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除
     *
     * @param dividend 除数
     * @param divisor  被除
     * @return 返回结果
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return divide(dividend, divisor, Integer.valueOf("2"));
    }


    /**
     * 除
     *
     * @param dividend 除数
     * @param divisor  被除
     * @param scale    保留小数位数
     * @return 返回结果
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
        if (CompareUtils.equal(dividend, BigDecimal.ZERO) || CompareUtils.equal(divisor, BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        // 返回结果
        return dividend.divide(divisor).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘
     *
     * @param multiplier   乘数
     * @param multiplicand 被乘数
     * @return 返回结果
     */
    public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand) {
        return multiply(multiplier, multiplicand, Integer.valueOf("2"));
    }

    /**
     * 乘
     *
     * @param multiplier   乘数
     * @param multiplicand 被乘数
     * @param scale        保留小数位数
     * @return 返回结果
     */
    public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand, int scale) {
        if (CompareUtils.equal(multiplier, BigDecimal.ZERO) || CompareUtils.equal(multiplicand, BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        //返回结果
        return multiplier.multiply(multiplicand).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减
     *
     * @param reduction 减数
     * @param minuend   被减数
     * @return 返回结果
     */
    public static BigDecimal subtract(BigDecimal reduction, BigDecimal minuend) {
        return subtract(reduction, minuend, Integer.valueOf("2"));
    }

    /**
     * 减
     *
     * @param reduction 减数
     * @param minuend   被减数
     * @param scale     保留小数位数
     * @return 返回结果
     */
    public static BigDecimal subtract(BigDecimal reduction, BigDecimal minuend, int scale) {
        if (CompareUtils.equal(reduction, BigDecimal.ZERO)) {
            reduction = BigDecimal.ZERO;
        }
        if (CompareUtils.equal(minuend, BigDecimal.ZERO)) {
            minuend = BigDecimal.ZERO;
        }

        //返回结果
        return reduction.subtract(minuend).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
}
