package com.cdkjframework.util.make;

import com.cdkjframework.util.log.LogUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.util.make
 * @ClassName: ChineseToEnglishUtil
 * @Description: 将汉字转换为拼音类
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ChineseToEnglishUtil {
    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(ChineseToEnglishUtil.class);

    /**
     * 多音字
     */
    private static String polyPhone = "重庆|cq|chongqing,重庆市|cqs|chongqingshi";

    /**
     * 将汉字转换为全拼
     *
     * @param src
     * @return 返回拼音
     */
    public static String getPinYin(String src) {

        String[] array = polyPhone.split(",");
        for (String str :
                array) {
            if (str.contains(src + "|")) {
                String[] strings = str.split("\\|");
                return strings[2];
            }
        }

        StringBuilder sb = new StringBuilder("");
        char[] charArray = src.toCharArray();
        int len = charArray.length;

        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();

        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (int i = 0; i < len; i++) {
                //转换为字符串
                String strText = Character.toString(charArray[i]);
                // 判断是否为汉字字符
                if (strText.matches("[\\u4E00-\\u9FA5]+")) {
                    String[] strings = PinyinHelper.toHanyuPinyinStringArray(charArray[i], outputFormat);
                    if (strings == null || strings.length == 0) {
                        continue;
                    }
                    sb.append(strings[0]);
                } else {
                    sb.append(Character.toString(charArray[i]));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
            logUtil.error("生成全拼音失败！" + ex.getMessage());
        }
        //返回执行结果
        return sb.toString();
    }

    /**
     * 返回中文的首字母
     *
     * @param str
     * @return 返回简拼
     */
    public static String getPinYinHeadChar(String str) {

        String[] array = polyPhone.split(",");
        for (String strPy :
                array) {
            if (strPy.contains(str + "|")) {
                String[] strings = strPy.split("\\|");
                return strings[1];
            }
        }
        StringBuilder convert = new StringBuilder("");
        for (int j = 0; j < str.length(); j++) {
            //读取到第一个字符
            char word = str.charAt(j);
            //获取该字符是拼音
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null && pinyinArray.length > 0) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        //返回执行结果
        return convert.toString();
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr 字段串
     * @return 返回结果
     */
    public static String getCnAscii(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        //转换为字节
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }
}
