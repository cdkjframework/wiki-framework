package com.cdkjframework.util.tool;

import com.cdkjframework.constant.RegexConsts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: RegexUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class RegexUtils {

    /**
     * 验证数字
     *
     * @param character 字符
     */
    public static boolean isNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_NUMBER);
    }

    /**
     * 验证是否为浮点数
     *
     * @param character 字符
     * @return 返回结果
     */
    public static boolean isFloatPoint(String character) {
        return isPattern(character, RegexConsts.REGEX_FLOAT_POINT);
    }

    /**
     * 只能输入n位的数字
     *
     * @param character 字符
     */
    public static boolean isMaxLengthNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_MAX_LENGTH_NUMBER);
    }

    /**
     * 只能输入至少n位的数字
     *
     * @param character 字符
     */
    public static boolean isLeastLengthNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_LEAST_LENGTH_NUMBER);
    }

    /**
     * 只能输入m~n位的数字
     *
     * @param character 字符
     */
    public static boolean isSpecifiedLengthNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_SPECIFIED_LENGTH_NUMBER);
    }

    /**
     * 只能输入零和非零开头的数字
     *
     * @param character 字符
     */
    public static boolean isNonZreoNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_NON_ZREO_NUMBER);
    }

    /**
     * 只能输入有两位小数的正实数
     *
     * @param character 字符
     */
    public static boolean isTwoDecimalFractionNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_TWO_DECIMAL_FRACTION_NUMBER);
    }

    /**
     * 只能输入有1~3位小数的正实数
     *
     * @param character 字符
     */
    public static boolean isSpecifiedDecimalFractionNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_SPECIFIED_DECIMAL_FRACTION_NUMBER);
    }

    /**
     * 只能输入非零的正整数
     *
     * @param character 字符
     */
    public static boolean isNonzeroPositiveIntegersNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_NONZERO_POSITIVE_INTEGERS_NUMBER);
    }

    /**
     * 只能输入非零的负整数
     *
     * @param character 字符
     */
    public static boolean isNonzeroNegativeIntegersNumber(String character) {
        return isPattern(character, RegexConsts.REGEX_NONZERO_NEGATIVE_INTEGERS_NUMBER);
    }

    /**
     * 只能输入长度为3的字符
     *
     * @param character 字符
     */
    public static boolean isThreeLengthCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_THREE_LENGTH_CHARACTER);
    }

    /**
     * 只能输入由26个英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isEnglishCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_EMAIL_CHARACTER);
    }

    /**
     * 只能输入由26个大写英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isCapitalizationEnglishCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_CAPITALIZATION_ENGLISH_CHARACTER);
    }

    /**
     * 只能输入由26个小写英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isLowercaseEnglishCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_LOWERCASE_ENGLISH_CHARACTER);
    }

    /**
     * 只能输入由数字和26个英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isEnglishAndNumberCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_ENGLISH_AND_NUMBER_CHARACTER);
    }

    /**
     * 只能输入由数字、26个英文字母或者下划线组成的字符串
     *
     * @param character 字符
     */
    public static boolean isNonSpecialCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_NON_SPECIAL_CHARACTER);
    }

    /**
     * 验证用户密码
     * 正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线;
     *
     * @param character 字符
     */
    public static boolean isPasswordCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_PASSWORD_CHARACTER);
    }

    /**
     * 只能输入汉字
     *
     * @param character 字符
     */
    public static boolean isChineseCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_CHINESE_CHARACTER);
    }

    /**
     * 验证Email地址
     *
     * @param character 字符
     */
    public static boolean isEmailCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_EMAIL_CHARACTER);
    }

    /**
     * 验证InternetURL
     *
     * @param character 字符
     */
    public static boolean isInternetUrlCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_INTERNET_URL_CHARACTER);
    }

    /**
     * 验证电话号码
     * 正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX";
     *
     * @param character 字符
     */
    public static boolean isPhoneCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_PHONE_CHARACTER);
    }

    /**
     * 验证身份证号（15位或18位数字）
     *
     * @param character 字符
     */
    public static boolean isIdNumberCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_ID_NUMBER_CHARACTER);
    }

    /**
     * 验证是否为UUID
     *
     * @param character 值
     * @return
     */
    public static boolean isUuidCharacter(String character) {
        return isPattern(character, RegexConsts.REGEX_UUID);
    }

    /**
     * 验证是否为指定类型
     *
     * @param character 字符
     * @param regex     正则
     * @return 返回布值
     */
    public static boolean isPattern(String character, String regex) {
        //正则
        Pattern pattern = compile(regex);
        //验证
        Matcher matcher = pattern.matcher(character);
        //返回结果
        return matcher.matches();
    }
}