package com.cdkj.framework.util.encrypts;

import com.cdkj.framework.util.log.LogUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 将String进行base64编码解码，使用utf-8
 *
 * @author xialin
 */

@Component
public class Base64Util {

    /**
     * 日志记录
     */
    private static LogUtil logUtil = LogUtil.getLogger(Base64Util.class);

    /**
     * 默认编码
     */
    private static final String UTF_8 = "UTF-8";

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return "";
            }
            return new String(Base64.decodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            logUtil.error(e);
        }

        return "";
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    public static String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return "";
            }
            return new String(encode(inputData.getBytes(UTF_8)));
        } catch (UnsupportedEncodingException e) {
            logUtil.error(e);
        }

        return "";
    }

    /**
     * byte 生成 base 64
     *
     * @param dataList 数据
     * @return 返回结果
     */
    public static String encode(byte[] dataList) {
        try {
            if (null == dataList || dataList.length == 0) {
                return "";
            }
            return new String(Base64.encodeBase64(dataList), UTF_8);
        } catch (UnsupportedEncodingException e) {
            logUtil.error("生成二维码出错误");
            logUtil.error(e);
        }
        return "";
    }
}
