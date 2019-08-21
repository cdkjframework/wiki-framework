package com.cdkjframework.util.tool;

import com.cdkjframework.constant.RegexConstant;

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
        return isPattern(character, RegexConstant.regexNumber);
    }

    /**
     * 只能输入n位的数字
     *
     * @param character 字符
     */
    public static boolean isMaxLengthNumber(String character) {
        return isPattern(character, RegexConstant.regexMaxLengthNumber);
    }

    /**
     * 只能输入至少n位的数字
     *
     * @param character 字符
     */
    public static boolean isLeastLengthNumber(String character) {
        return isPattern(character, RegexConstant.regexLeastLengthNumber);
    }

    /**
     * 只能输入m~n位的数字
     *
     * @param character 字符
     */
    public static boolean isSpecifiedLengthNumber(String character) {
        return isPattern(character, RegexConstant.regexSpecifiedLengthNumber);
    }

    /**
     * 只能输入零和非零开头的数字
     *
     * @param character 字符
     */
    public static boolean isNonZreoNumber(String character) {
        return isPattern(character, RegexConstant.regexNonZreoNumber);
    }

    /**
     * 只能输入有两位小数的正实数
     *
     * @param character 字符
     */
    public static boolean isTwoDecimalFractionNumber(String character) {
        return isPattern(character, RegexConstant.regexTwoDecimalFractionNumber);
    }

    /**
     * 只能输入有1~3位小数的正实数
     *
     * @param character 字符
     */
    public static boolean isSpecifiedDecimalFractionNumber(String character) {
        return isPattern(character, RegexConstant.regexSpecifiedDecimalFractionNumber);
    }

    /**
     * 只能输入非零的正整数
     *
     * @param character 字符
     */
    public static boolean isNonzeroPositiveIntegersNumber(String character) {
        return isPattern(character, RegexConstant.regexNonzeroPositiveIntegersNumber);
    }

    /**
     * 只能输入非零的负整数
     *
     * @param character 字符
     */
    public static boolean isNonzeroNegativeIntegersNumber(String character) {
        return isPattern(character, RegexConstant.regexNonzeroNegativeIntegersNumber);
    }

    /**
     * 只能输入长度为3的字符
     *
     * @param character 字符
     */
    public static boolean isThreeLengthCharacter(String character) {
        return isPattern(character, RegexConstant.regexThreeLengthCharacter);
    }

    /**
     * 只能输入由26个英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isEnglishCharacter(String character) {
        return isPattern(character, RegexConstant.regexEmailCharacter);
    }

    /**
     * 只能输入由26个大写英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isCapitalizationEnglishCharacter(String character) {
        return isPattern(character, RegexConstant.regexCapitalizationEnglishCharacter);
    }

    /**
     * 只能输入由26个小写英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isLowercaseEnglishCharacter(String character) {
        return isPattern(character, RegexConstant.regexLowercaseEnglishCharacter);
    }

    /**
     * 只能输入由数字和26个英文字母组成的字符串
     *
     * @param character 字符
     */
    public static boolean isEnglishAndNumberCharacter(String character) {
        return isPattern(character, RegexConstant.regexEnglishAndNumberCharacter);
    }

    /**
     * 只能输入由数字、26个英文字母或者下划线组成的字符串
     *
     * @param character 字符
     */
    public static boolean isNonSpecialCharacter(String character) {
        return isPattern(character, RegexConstant.regexNonSpecialCharacter);
    }

    /**
     * 验证用户密码
     * 正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线;
     *
     * @param character 字符
     */
    public static boolean isPasswordCharacter(String character) {
        return isPattern(character, RegexConstant.regexPasswordCharacter);
    }

    /**
     * 只能输入汉字
     *
     * @param character 字符
     */
    public static boolean isChineseCharacter(String character) {
        return isPattern(character, RegexConstant.regexChineseCharacter);
    }

    /**
     * 验证Email地址
     *
     * @param character 字符
     */
    public static boolean isEmailCharacter(String character) {
        return isPattern(character, RegexConstant.regexEmailCharacter);
    }

    /**
     * 验证InternetURL
     *
     * @param character 字符
     */
    public static boolean isInternetUrlCharacter(String character) {
        return isPattern(character, RegexConstant.regexInternetUrlCharacter);
    }

    /**
     * 验证电话号码
     * 正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX";
     *
     * @param character 字符
     */
    public static boolean isPhoneCharacter(String character) {
        return isPattern(character, RegexConstant.regexPhoneCharacter);
    }

    /**
     * 验证身份证号（15位或18位数字）
     *
     * @param character 字符
     */
    public static boolean isIdNumberCharacter(String character) {
        return isPattern(character, RegexConstant.regexIdNumberCharacter);
    }

    /**
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