package com.cdkjframework.util.encrypts;

import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: UnicodeUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class UnicodeUtils {

    /**
     * unicode 转换为 汉字
     *
     * @param unicode 编码
     * @return 返回结果
     */
    public static String unicodeToCn(String unicode) {

        StringBuilder builder = new StringBuilder();
        int i;
        int pos = 0;
        final String unicodeValue = "\\u";
        while ((i = unicode.indexOf(unicodeValue, pos)) != -1) {
            builder.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                builder.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        //如果pos位置后，有非中文字符，直接添加
        builder.append(unicode.substring(pos));

        //返回结果
        return builder.toString();
    }

    /**
     * 汉字 转换为 unicode
     *
     * @param cn 汉字
     * @return 返回编码信息
     */
    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder unicode = new StringBuilder();
        for (char c :
                chars) {
            // 标准ASCII范围内的字符，直接输出
            if (c >= 0 && c <= 127) {
                unicode.append(c);
                continue;
            }
            unicode.append("\\u" + Integer.toString(c, 16));
        }

        //返回结果
        return unicode.toString();
    }
}
