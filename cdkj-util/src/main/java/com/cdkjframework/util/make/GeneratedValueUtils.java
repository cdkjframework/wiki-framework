package com.cdkjframework.util.make;

import com.cdkjframework.util.tool.StringUtil;

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

public class GeneratedValueUtils {

    /**
     * 使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
     */
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

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
        return getUuidString().replace("-", "");
    }

    /**
     * 生成指定位数整数随机数
     *
     * @param digit 指定位数
     * @return 返回随机数
     */
    public static Integer getRandom(int digit) {
        int ans = 0;
        while (Math.log10(ans) + 1 < digit) {
            ans = (int) (Math.random() * Math.pow(10, digit));
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
        if (StringUtil.isNullAndSpaceOrEmpty(sources)) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
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
        return getRandomCharacter(4);
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
}
