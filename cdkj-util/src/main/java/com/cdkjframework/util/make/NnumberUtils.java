package com.cdkjframework.util.make;

import com.cdkjframework.constant.IntegerConsts;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.make
 * @ClassName: AmountUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class NnumberUtils {
    /**
     * 单位进位，中文默认为4位即（万、亿）
     */
    public static int UNIT_STEP = IntegerConsts.FOUR;

    /**
     * 单位
     */
    public static String[] CN_UNITS = new String[]{"个", "十", "百", "千", "万", "十",
            "百", "千", "亿", "十", "百", "千", "万"};

    /**
     * 汉字
     */
    public static String[] CN_CHARS = new String[]{"零", "一", "二", "三", "四",
            "五", "六", "七", "八", "九"};

    /**
     * 将阿拉伯数字转换为中文数字123=》一二三
     *
     * @param srcNum
     * @return
     */
    public static String getCnNum(int srcNum) {
        String desCNNum = "";
        if (srcNum <= IntegerConsts.ZERO) {
            desCNNum = "零";
        } else {
            int singleDigit;
            while (srcNum > IntegerConsts.ZERO) {
                singleDigit = srcNum % IntegerConsts.TEN;
                desCNNum = CN_CHARS[singleDigit] + desCNNum;
                srcNum /= IntegerConsts.TEN;
            }
        }

        return desCNNum;
    }

    /**
     * 数值转换为中文字符串 如2转化为贰
     */
    public static String cvt(long num) {
        return cvt(num, false);
    }

    /**
     * 数值转换为中文字符串(口语化)
     *
     * @param num          需要转换的数值
     * @param isColloquial 是否口语化。例如12转换为'十二'而不是'一十二'。
     * @return
     */
    public static String cvt(String num, boolean isColloquial) {
        int integer, decimal;
        StringBuffer buffer = new StringBuffer(IntegerConsts.THIRTY_TWO);
        String[] splitNum = num.split("\\.");
        //整数部分
        integer = Integer.parseInt(splitNum[IntegerConsts.ZERO]);
        //小数部分
        decimal = Integer.parseInt(splitNum[IntegerConsts.ONE]);
        String[] result_1 = convert(integer, isColloquial);
        for (String str1 : result_1) {
            buffer.append(str1);
        }
        if (decimal != IntegerConsts.ZERO) {
            //例如5.IntegerConsts.THIRTY_TWO，小数部分展示三二，而不是三十二
            String result_2 = getCnNum(decimal);
            buffer.append("点");
            buffer.append(result_2);
        }
        return buffer.toString();
    }

    /**
     * 对于int,long类型的数据处理
     *
     * @param num
     * @param isColloquial
     * @return
     */
    public static String cvt(long num, boolean isColloquial) {
        String[] result = convert(num, isColloquial);
        StringBuffer buffer = new StringBuffer(IntegerConsts.THIRTY_TWO);
        for (String str : result) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * 将数值转换为中文
     *
     * @param num          需要转换的数值
     * @param isColloquial 是否口语化。例如12转换为'十二'而不是'一十二'。
     * @return
     */
    public static String[] convert(long num, boolean isColloquial) {
        // 10以下直接返回对应汉字
        if (num < IntegerConsts.TEN) {
            // ASCII2int
            return new String[]{CN_CHARS[(int) num]};
        }
        char[] chars = String.valueOf(num).toCharArray();
        // 超过单位表示范围的返回空
        if (chars.length > CN_UNITS.length) {
            return new String[]{};
        }
        // 记录上次单位进位
        boolean isLastUnitStep = false;
        // 创建数组，将数字填入单位对应的位置
        ArrayList<String> cnChars = new ArrayList<String>(chars.length * IntegerConsts.TWO);
        // 从低位向高位循环
        for (int pos = chars.length - IntegerConsts.ONE; pos >= IntegerConsts.ZERO; pos--) {
            char ch = chars[pos];
            // ascii2int 汉字
            String cnChar = CN_CHARS[ch - '0'];
            // 对应的单位坐标
            int unitPos = chars.length - pos - IntegerConsts.ONE;
            // 单位
            String cnUnit = CN_UNITS[unitPos];
            // 是否为0
            boolean isZero = (ch == '0');
            // 是否低位为0
            boolean isZeroLow = (pos + IntegerConsts.ONE < chars.length && chars[pos + IntegerConsts.ONE] == '0');
            // 当前位是否需要单位进位
            boolean isUnitStep = (unitPos >= UNIT_STEP && (unitPos % UNIT_STEP == IntegerConsts.ZERO));
            // 去除相邻的上一个单位进位
            if (isUnitStep && isLastUnitStep) {
                int size = cnChars.size();
                cnChars.remove(size - IntegerConsts.ONE);
                // 补0
                if (!CN_CHARS[IntegerConsts.ZERO].equals(cnChars.get(size - IntegerConsts.TWO))) {
                    cnChars.add(CN_CHARS[IntegerConsts.ZERO]);
                }
            }
            // 单位进位(万、亿)，或者非0时加上单位
            if (isUnitStep || !isZero) {
                cnChars.add(cnUnit);
                isLastUnitStep = isUnitStep;
            }
            // 当前位为0低位为0，或者当前位为0并且为单位进位时进行省略
            if (isZero && (isZeroLow || isUnitStep)) {
                continue;
            }
            cnChars.add(cnChar);
            isLastUnitStep = false;
        }

        Collections.reverse(cnChars);
        // 清除最后一位的0
        int chSize = cnChars.size();
        String chEnd = cnChars.get(chSize - IntegerConsts.ONE);
        if (CN_CHARS[IntegerConsts.ZERO].equals(chEnd) || CN_UNITS[IntegerConsts.ZERO].equals(chEnd)) {
            cnChars.remove(chSize - IntegerConsts.ONE);
        }

        // 口语化处理
        if (isColloquial) {
            String chFirst = cnChars.get(IntegerConsts.ZERO);
            String chSecond = cnChars.get(IntegerConsts.ONE);
            // 是否以'一'开头，紧跟'十'
            if (chFirst.equals(CN_CHARS[IntegerConsts.ONE]) && chSecond.startsWith(CN_UNITS[IntegerConsts.ONE])) {
                cnChars.remove(IntegerConsts.ZERO);
            }
        }
        return cnChars.toArray(new String[]{});
    }
}
