package com.cdkjframework.util.tool;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ProjectName: cdkj.framework.core
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: DigitalCompareUtil
 * @Description: 数字比较
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class CompareUtil {

    /**
     * 验证前一个数据是否大于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @return 返回结果
     */
    public static Boolean greater(BigDecimal sourceDigital, BigDecimal targetDigital) {
        //验证是否为 NULL
        if (sourceDigital == null) {
            sourceDigital = BigDecimal.ZERO;
        }
        if (targetDigital == null) {
            targetDigital = BigDecimal.ZERO;
        }

        //返回结果
        return compareResult(sourceDigital, targetDigital) == 1;
    }

    /**
     * 验证前一个数据是否大于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @param scale         保留位数
     * @return 返回结果
     */
    public static Boolean greater(Double sourceDigital, Double targetDigital, int scale) {

        BigDecimal source = doubleToDecimal(sourceDigital, scale);
        BigDecimal target = doubleToDecimal(targetDigital, scale);

        //返回结果
        return greater(source, target);
    }

    /**
     * 验证前一个数据是否大于等于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @return 返回结果
     */
    public static Boolean greaterAndEqual(BigDecimal sourceDigital, BigDecimal targetDigital) {
        //验证是否为 NULL
        if (sourceDigital == null) {
            sourceDigital = BigDecimal.ZERO;
        }
        if (targetDigital == null) {
            targetDigital = BigDecimal.ZERO;
        }

        //返回结果
        return compareResult(sourceDigital, targetDigital) >= 1;
    }

    /**
     * 验证前一个数据是否大于等于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @param scale         保留位数
     * @return 返回结果
     */
    public static Boolean greaterAndEqual(Double sourceDigital, Double targetDigital, int scale) {
        BigDecimal source = doubleToDecimal(sourceDigital, scale);
        BigDecimal target = doubleToDecimal(targetDigital, scale);

        //返回结果
        return greaterAndEqual(source, target);
    }

    /**
     * 验证前一个数据是否小于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @return 返回结果
     */
    public static Boolean less(BigDecimal sourceDigital, BigDecimal targetDigital) {
        //验证是否为 NULL
        if (sourceDigital == null) {
            sourceDigital = BigDecimal.ZERO;
        }
        if (targetDigital == null) {
            targetDigital = BigDecimal.ZERO;
        }

        //返回结果
        return compareResult(sourceDigital, targetDigital) == -1;
    }

    /**
     * 验证前一个数据是否小于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @param scale         保留位数
     * @return 返回结果
     */
    public static Boolean less(Double sourceDigital, Double targetDigital, int scale) {

        BigDecimal source = doubleToDecimal(sourceDigital, scale);
        BigDecimal target = doubleToDecimal(targetDigital, scale);

        //返回结果
        return less(source, target);
    }

    /**
     * 验证前一个数据是否等于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @return 返回结果
     */
    public static Boolean equal(BigDecimal sourceDigital, BigDecimal targetDigital) {
        //验证是否为 NULL
        if (sourceDigital == null) {
            sourceDigital = BigDecimal.ZERO;
        }
        if (targetDigital == null) {
            targetDigital = BigDecimal.ZERO;
        }

        //返回结果
        return compareResult(sourceDigital, targetDigital) == 0;
    }

    /**
     * 验证前一个数据是否等于
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @param scale         保留位数
     * @return 返回结果
     */
    public static Boolean equal(Double sourceDigital, Double targetDigital, int scale) {

        BigDecimal source = doubleToDecimal(sourceDigital, scale);
        BigDecimal target = doubleToDecimal(targetDigital, scale);

        //返回结果
        return equal(source, target);
    }

    /**
     * 将 double 转换为 decimal
     *
     * @param sourceDigital 源数据
     * @param scale         小数据位数
     * @return 返回转换结果
     */
    public static BigDecimal doubleToDecimal(Double sourceDigital, int scale) {
        //验证是否为空
        if (sourceDigital == null) {
            sourceDigital = 0d;
        }
        if (scale < 0) {
            scale = 2;
        }
        //返回数据
        return BigDecimal.valueOf(sourceDigital).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 比较结果
     *
     * @param sourceDigital 源数据
     * @param targetDigital 比较数据
     * @return 返回结果
     */
    private static int compareResult(BigDecimal sourceDigital, BigDecimal targetDigital) {
        return sourceDigital.compareTo(targetDigital);
    }
}
