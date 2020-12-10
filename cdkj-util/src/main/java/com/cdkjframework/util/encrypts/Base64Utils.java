package com.cdkjframework.util.encrypts;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 将String进行base64编码解码，使用utf-8
 *
 * @author xialin
 */

@Component
public class Base64Utils {

    /**
     * 日志记录
     */
    private static LogUtils logUtil = LogUtils.getLogger(Base64Utils.class);

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
            logUtil.error(e.getCause(), e.getMessage());
        }

        return "";
    }

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static byte[] decodeDataToByte(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return Base64.decodeBase64(inputData.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            logUtil.error(e.getCause(), e.getMessage());
        }

        return null;
    }

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static String decodeData(byte[] inputData) {
        try {
            if (null == inputData) {
                return "";
            }
            return new String(Base64.decodeBase64(inputData), UTF_8);
        } catch (UnsupportedEncodingException e) {
            logUtil.error(e.getCause(), e.getMessage());
        }

        return "";
    }

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static byte[] decodeDataToByte(byte[] inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return Base64.decodeBase64(inputData);
        } catch (Exception e) {
            logUtil.error(e.getCause(), e.getMessage());
        }

        return null;
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
            logUtil.error(e.getCause(), e.getMessage());
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
            logUtil.error("将字节转换为字符串");
            logUtil.error(e.getCause(), e.getMessage());
        }
        return "";
    }

    /**
     * base64 转 InputStream
     *
     * @param base64String 编码字符
     * @return 返回位图
     */
    public static InputStream base64ToInputStream(String base64String) {
        ByteArrayInputStream stream = null;
        byte[] bytes = decodeDataToByte(base64String);
        try {
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            logUtil.error(e.getCause(), e.getMessage());
        }
        return stream;
    }

    /**
     * 流转换为 base64
     *
     * @param input 文件流
     * @return 返回字符
     */
    public static String inputStreamToBase64(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[IntegerConsts.BYTE_LENGTH];
        String base64String = StringUtils.Empty;
        try {
            while (input.read(buffer) > 0) {
                output.write(buffer);
            }
            byte[] bytes = output.toByteArray();
            base64String = encode(bytes);
        } catch (IOException e) {
            logUtil.error(e.getCause(), e.getMessage());
        }

        // 返回结果
        return base64String;
    }
}
