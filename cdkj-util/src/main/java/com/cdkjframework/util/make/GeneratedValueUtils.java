package com.cdkjframework.util.make;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.make
 * @ClassName: GeneratedValues
 * @Description: 生成值
 * @Author: xiaLin
 * @Version: 1.0
 */

public class GeneratedValueUtils extends AbstractUUIDGenerator {

    /**
     * 使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
     */
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 获取有序UUID
     *
     * @return 返回UUID字符串
     */
    public static String getOrderlyUuid() {
        return
                format(getJVM()) + StringUtils.HORIZONTAL
                        + format(getHiTime()) + StringUtils.HORIZONTAL
                        + format(getLoTime()) + StringUtils.HORIZONTAL
                        + format(getIP()) + StringUtils.HORIZONTAL
                        + format(getCount());
    }

    /**
     * 获取有序UUID
     *
     * @return 返回UUID字符串
     */
    public static String getOrderlyShortUuid() {
        return format(getJVM()) + format(getHiTime()) + format(getLoTime()) + format(getIP()) + format(getCount());
    }


    /**
     * 格式化为 8 的长度
     *
     * @param intValue 值
     * @return 返回结果
     */
    private static String format(int intValue) {
        String formatted = Integer.toHexString(intValue);
        StringBuilder buf = new StringBuilder("00000000");
        int start = IntegerConsts.EIGHT - formatted.length();
        buf.replace(start, IntegerConsts.EIGHT, formatted);
        return buf.toString();
    }

    /**
     * 获取结果
     *
     * @param shortValue short值
     * @return 返回结果
     */
    private static String format(short shortValue) {
        String formatted = Integer.toHexString(shortValue);
        StringBuilder buf = new StringBuilder("0000");
        int start = IntegerConsts.FOUR - formatted.length();
        buf.replace(start, IntegerConsts.FOUR, formatted);
        return buf.toString();
    }


    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
        System.out.println(currentTimeMillis >>> 8);
    }

    /**
     * 返回UUID 值
     *
     * @return UUID
     */
    public static UUID getUuid() {
        return UUID.randomUUID();
    }

    /**
     * 生成 UUID
     *
     * @return 返回结果
     */
    public static String getUuidString() {
        return getUuid().toString();
    }

    /**
     * 返回不带横线UUID
     *
     * @return 返回
     */
    public static String getUuidNotTransverseLine() {
        return getUuidString().replace(StringUtils.HORIZONTAL, StringUtils.Empty);
    }

    /**
     * 生成指定位数整数随机数
     *
     * @param digit 指定位数
     * @return 返回随机数
     */
    public static Integer getRandom(int digit) {
        int ans = IntegerConsts.ZERO;
        while (Math.log10(ans) + IntegerConsts.ONE < digit) {
            ans = (int) (Math.random() * Math.pow(IntegerConsts.TEN, digit));
        }
        return ans;
    }

    /**
     * 随机字符
     *
     * @param verifySize 长度
     * @param sources    源字符
     * @return 返回结果
     */
    public static String getRandomCharacter(int verifySize, String sources) {
        if (StringUtils.isNullAndSpaceOrEmpty(sources)) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = IntegerConsts.ZERO; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 随机字符（默认 4 位）
     *
     * @return 返回结果
     */
    public static String getRandomCharacter() {
        return getRandomCharacter(IntegerConsts.FOUR);
    }

    /**
     * 随机字符（默认 4 位）
     *
     * @param verifySize
     * @return
     */
    public static String getRandomCharacter(int verifySize) {
        return getRandomCharacter(verifySize, VERIFY_CODES);
    }

    /**
     * 生成指定大小整数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 返回结果
     */
    public static int getRandomInteger(int min, int max) {
        Random random = new Random();
        int value = random.nextInt(max);
        if (value < min) {
            return getRandomInteger(min, max);
        }
        return value;
    }

    /**
     * 生成指定大小 double
     *
     * @param min 最小值
     * @param max 最大值
     * @return 返回结果
     */
    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        double value = min + random.nextDouble() * (max - min);
        if (value < min) {
            return getRandomDouble(min, max);
        }
        return value;
    }
}
