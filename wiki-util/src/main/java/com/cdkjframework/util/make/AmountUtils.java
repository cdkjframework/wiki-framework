package com.cdkjframework.util.make;

import com.cdkjframework.constant.IntegerConsts;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.make
 * @ClassName: AmountUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AmountUtils {

    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖"};
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONEY_UNIT = {"分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟"};
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = IntegerConsts.TWO;
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZERO_FULL = "零元" + CN_FULL;

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney 输入的金额
     * @return 对应的汉语大写
     */
    public static String start(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int sigNum = numberOfMoney.signum();
        // 零元整的情况
        if (sigNum == IntegerConsts.ZERO) {
            return CN_ZERO_FULL;
        }
        // 这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(IntegerConsts.ZERO, RoundingMode.HALF_UP).abs().longValue();
        // 得到小数点后两位值
        long scale = number % IntegerConsts.ONE_HUNDRED;
        int numUnit = IntegerConsts.ZERO, numIndex = IntegerConsts.ZERO;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = IntegerConsts.ZERO, 01 = 1, IntegerConsts.TEN, 11
        if (scale <= IntegerConsts.ZERO) {
            numIndex = IntegerConsts.TWO;
            number = number / IntegerConsts.ONE_HUNDRED;
            getZero = true;
        }
        if ((scale > IntegerConsts.ZERO) && (scale % IntegerConsts.TEN <= IntegerConsts.ZERO)) {
            numIndex = IntegerConsts.ONE;
            number = number / IntegerConsts.TEN;
            getZero = true;
        }
        int zeroSize = IntegerConsts.ZERO;
        while (true) {
            if (number <= IntegerConsts.ZERO) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % IntegerConsts.TEN);
            if (numUnit > IntegerConsts.ZERO) {
                if ((numIndex == IntegerConsts.NINE) && (zeroSize >= IntegerConsts.THREE)) {
                    sb.insert(IntegerConsts.ZERO, CN_UPPER_MONEY_UNIT[IntegerConsts.SIX]);
                }
                if ((numIndex == IntegerConsts.THIRTEEN) && (zeroSize >= IntegerConsts.THREE)) {
                    sb.insert(IntegerConsts.ZERO, CN_UPPER_MONEY_UNIT[IntegerConsts.TEN]);
                }
                sb.insert(IntegerConsts.ZERO, CN_UPPER_MONEY_UNIT[numIndex]);
                sb.insert(IntegerConsts.ZERO, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = IntegerConsts.ZERO;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(IntegerConsts.ZERO, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == IntegerConsts.TWO) {
                    if (number > IntegerConsts.ZERO) {
                        sb.insert(IntegerConsts.ZERO, CN_UPPER_MONEY_UNIT[numIndex]);
                    }
                } else if (((numIndex - IntegerConsts.TWO) % IntegerConsts.FOUR == IntegerConsts.ZERO) && (number % IntegerConsts.ONE_THOUSAND > IntegerConsts.ZERO)) {
                    sb.insert(IntegerConsts.ZERO, CN_UPPER_MONEY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / IntegerConsts.TEN;
            ++numIndex;
        }
        // 如果 sigNum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (sigNum == IntegerConsts.MINUS_ONE) {
            sb.insert(IntegerConsts.ZERO, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (scale <= IntegerConsts.ZERO) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }
}
