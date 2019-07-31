package com.cdkj.framework.util.tool;

import com.cdkj.framework.util.log.LogUtil;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkj.framework.core.util.tool
 * @ClassName: StringUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class StringUtil {

    /**
     * <p>The maximum size to which the padding constant(s) can expand.</p>
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(StringUtil.class);

    /**
     * 验证是否为空
     *
     * @param obj 数据
     * @return 返回布尔值
     */
    public static boolean isNullAndSpaceOrEmpty(Object obj) {
        //验证是否为 null
        if (obj == null) {
            return true;
        }
        //验证是否为空
        if (obj.toString().trim() == "") {
            return true;
        }
        //返回结果
        return false;
    }

    /**
     * 是否不为空
     *
     * @param obj 值
     * @return 返回结果
     */
    public static boolean isNotNullAndEmpty(Object obj) {
        return !isNullAndSpaceOrEmpty(obj);
    }

    /**
     * 包含指定
     *
     * @param str        原数据
     * @param searchChar 搜索字符
     * @return 返回结果
     */
    public static boolean contains(Object str, char searchChar) {
        if (isNullAndSpaceOrEmpty(str)) {
            return false;
        }
        return str.toString().indexOf(searchChar) >= 0;
    }

    /**
     * 包含指定
     *
     * @param str       原数据
     * @param searchStr 搜索字符
     * @return 返回结果
     */
    public static boolean contains(Object str, String searchStr) {
        if (isNullAndSpaceOrEmpty(str) || isNullAndSpaceOrEmpty(searchStr)) {
            return false;
        }
        return str.toString().indexOf(searchStr) >= 0;
    }

    /**
     * 将值转换为整数
     *
     * @param obj 值
     * @return 返回结果
     */
    public static int convertInt(Object obj) {
        int value = 0;
        try {
            if (!isNullAndSpaceOrEmpty(obj)) {
                value = Integer.valueOf(obj.toString());
            }
        } catch (Exception ex) {
            logUtil.error(ex);
        }
        //返回结果
        return value;
    }

    /**
     * 转换为长整形
     *
     * @param obj 数据
     * @return 返回结果
     */
    public static Long convertLong(Object obj) {
        Long value = 0L;
        try {
            if (!isNullAndSpaceOrEmpty(obj)) {
                value = Long.valueOf(obj.toString());
            }
        } catch (Exception ex) {
            logUtil.error(ex);
        }
        //返回结果
        return value;
    }

    /**
     * 将值转换为布尔
     *
     * @param obj 值
     * @return 返回结果
     */
    public static Boolean convertBoolean(Object obj) {
        Boolean value = false;
        try {
            int intValue = convertInt(obj);
            if (intValue > 0) {
                value = true;
            } else if (!isNullAndSpaceOrEmpty(obj)) {
                value = Boolean.valueOf(obj.toString());
            }
        } catch (Exception ex) {
            logUtil.error(ex);
        }
        //返回结果
        return value;
    }

    /**
     * 类名格式
     *
     * @param clazzName 类型名
     * @return 返回结果
     */
    public static String classFormat(String clazzName) {
        //分割字符
        String[] clazzList = clazzName.split("_");

        StringBuilder builder = new StringBuilder();
        for (String name :
                clazzList) {
            builder.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));
        }

        //返回结果
        return builder.toString();
    }

    /**
     * 属性名
     *
     * @param attributeName 属性名称
     * @return 返回结果
     */
    public static String attributeNameFormat(String attributeName) {
        //分割字符
        String[] clazzList = attributeName.split("_");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < clazzList.length; i++) {
            String name = clazzList[i];
            if (i == 0) {
                builder.append(Character.toLowerCase(name.charAt(0))).append(name.substring(1));
                continue;
            }
            builder.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));
        }

        //返回结果
        return builder.toString();
    }

    /**
     * 左边2
     *
     * @param str
     * @param size
     * @param padChar
     * @return 返回字符
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    /**
     * 内
     *
     * @param repeat
     * @param padChar
     * @return 返回字符
     * @throws IndexOutOfBoundsException 异常信息
     */
    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    /**
     * 左边
     *
     * @param str    字符
     * @param size   大小
     * @param padStr 衬垫窜
     * @return 返回结果
     */
    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isNullAndSpaceOrEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }
}
