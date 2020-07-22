package com.cdkjframework.constant;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.consts
 * @ClassName: RegexConstant
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class RegexConsts {

    /**
     * 验证正数字
     */
    public static final String REGEX_NUMBER = "^[+-]?[0-9]*$";

    /**
     * 匹配浮点数
     * ^[1-9]\d*$　 　 //匹配正整数
     * ^-[1-9]\d*$ 　 //匹配负整数
     * ^-?[1-9]\d*$　　 //匹配整数
     * ^[1-9]\d*|0$　 //匹配非负整数（正整数 + 0）
     * ^-[1-9]\d*|0$　　 //匹配非正整数（负整数 + 0）
     * ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$　　 //匹配正浮点数
     * ^-([1-9]\d*\.\d*|0\.\d*[1-9]\d*)$　 //匹配负浮点数
     * ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$　 //匹配浮点数
     * ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$　　 //匹配非负浮点数（正浮点数 + 0）
     * ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$　　//匹配非正浮点数（负浮点数 + 0）
     */
    public static final String REGEX_FLOAT_POINT = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";

    /**
     * 只能输入n位的数字
     */
    public static final String REGEX_MAX_LENGTH_NUMBER = "^\\d{n}$";

    /**
     * 只能输入至少n位的数字
     */
    public static final String REGEX_LEAST_LENGTH_NUMBER = "^\\d{n,}$";

    /**
     * 只能输入m~n位的数字
     */
    public static final String REGEX_SPECIFIED_LENGTH_NUMBER = "^\\d{m,n}$";

    /**
     * 只能输入零和非零开头的数字
     */
    public static final String REGEX_NON_ZREO_NUMBER = "^(0|[1-9][0-9]*)$";

    /**
     * 只能输入有两位小数的正实数
     */
    public static final String REGEX_TWO_DECIMAL_FRACTION_NUMBER = "^[0-9]+(.[0-9]{2})?$";

    /**
     * 只能输入有1~3位小数的正实数
     */
    public static final String REGEX_SPECIFIED_DECIMAL_FRACTION_NUMBER = "^[0-9]+(.[0-9]{2})?$";

    /**
     * 只能输入非零的正整数
     */
    public static final String REGEX_NONZERO_POSITIVE_INTEGERS_NUMBER = "^\\+?[1-9][0-9]*$";

    /**
     * 只能输入非零的负整数
     */
    public static final String REGEX_NONZERO_NEGATIVE_INTEGERS_NUMBER = "^\\-[1-9][]0-9*$";
    /**
     * 只能输入长度为3的字符
     */
    public static final String REGEX_THREE_LENGTH_CHARACTER = "^.{3}$";

    /**
     * 只能输入由26个英文字母组成的字符串
     */
    public static final String REGEX_ENGLISH_CHARACTER = "^[A-Za-z]+$";

    /**
     * 只能输入由26个大写英文字母组成的字符串
     */
    public static final String REGEX_CAPITALIZATION_ENGLISH_CHARACTER = "^[A-Z]+$";

    /**
     * 只能输入由26个小写英文字母组成的字符串
     */
    public static final String REGEX_LOWERCASE_ENGLISH_CHARACTER = "^[a-z]+$";

    /**
     * 只能输入由数字和26个英文字母组成的字符串
     */
    public static final String REGEX_ENGLISH_AND_NUMBER_CHARACTER = "^[A-Za-z0-9]+$";

    /**
     * 只能输入由数字、26个英文字母或者下划线组成的字符串
     */
    public static final String REGEX_NON_SPECIAL_CHARACTER = "^\\w+$";

    /**
     * 验证用户密码
     * 正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线;
     */
    public static final String REGEX_PASSWORD_CHARACTER = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 只能输入汉字
     */
    public static final String REGEX_CHINESE_CHARACTER = "^[\u4e00-\u9fa5]{0,}$";

    /**
     * 验证Email地址
     */
    public static final String REGEX_EMAIL_CHARACTER = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 验证InternetURL
     */
    public static final String REGEX_INTERNET_URL_CHARACTER = "^http://%28[/\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

    /**
     * 验证电话号码
     * 正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX";
     */
    public static final String REGEX_PHONE_CHARACTER = "^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";

    /**
     * 验证身份证号（15位或18位数字）
     */
    public static final String REGEX_ID_NUMBER_CHARACTER = "^\\d{15}|\\d{18}$";

    /**
     * IE
     */
    public static final String IE = "msie ([\\d.]+)";

    /**
     * edge ie
     */
    public static final String IE_EDGE = "edge ([\\d.]+)";

    /**
     * Microsoft Edge
     */
    public static final String MICROSOFT_EDGE = "edg/([\\d.]+)";

    /**
     * firefox
     */
    public static final String FIREFOX = "firefox/([\\d.]+)";

    /**
     * chrome
     */
    public static final String CHROME = "chrome/([\\d.]+)";

    /**
     * opera
     */
    public static final String OPERA = "opr/([\\d.]+)";

    /**
     * safari
     */
    public static final String SAFARI = "version/([\\d.]+).*safari";
}
