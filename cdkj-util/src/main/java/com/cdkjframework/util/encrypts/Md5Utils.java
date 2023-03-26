package com.cdkjframework.util.encrypts;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.enums.AlgorithmTypeEnums;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;

import javax.crypto.Mac;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: com.cdkjframework
 * @Package: com.cdkjframework.util.encrypts
 * @ClassName: Md5Utils
 * @Description: 实现 md5 加密
 * @Author: xiaLin
 * @Version: 1.0
 */
public class Md5Utils {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(Md5Utils.class);

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param content 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String getMd5(String content) {
        try {
            // 计算md5函数
            byte[] md5 = getMd5ByByte(content.getBytes(Charset.defaultCharset()), AlgorithmTypeEnums.MD5);
            // 转换为16进制
            return toHexString(md5);
        } catch (Exception e) {
            logUtils.error(e);
            return null;
        }
    }

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param content 传入要加密的字符串
     * @return MD5加密后的字符串 16 位
     */
    public static String getMd5Hex(String content) {
        String hex = getMd5(content);
        if (StringUtils.isNullAndSpaceOrEmpty(hex)) {
            return hex;
        }
        return hex.substring(IntegerConsts.EIGHT, IntegerConsts.TWENTY_FOUR);
    }

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param data 传入要加密的 byte
     * @return MD5加密后的字符串
     */
    public static String getMd5(byte[] data) {
        try {
            // 计算md5函数
            byte[] md5 = getMd5ByByte(data, AlgorithmTypeEnums.MD5);
            // 转换为16进制
            return toHexString(md5);
        } catch (Exception e) {
            logUtils.error(e);
            return null;
        }
    }

    /**
     * 对字符串HMAC_SHA256加密(小写+字母)
     *
     * @param content 传入要加密的字符串
     * @return 加密后的字符串
     */
    public static String getSha256(String content) {
        try {
            // 计算md5函数
            byte[] md5 = getMd5ByByte(content.getBytes(Charset.defaultCharset()),
                    AlgorithmTypeEnums.SHA256);
            // 转换为16进制
            return toHexString(md5);
        } catch (Exception e) {
            logUtils.error(e);
            return null;
        }
    }

    /**
     * 对字符串 SHA512 加密(小写+字母)
     *
     * @param content 传入要加密的字符串
     * @return 加密后的字符串
     */
    public static String getSha512(String content) {
        try {
            // 计算md5函数
            byte[] md5 = getMd5ByByte(content.getBytes(Charset.defaultCharset()),
                    AlgorithmTypeEnums.SHA512);
            // 转换为16进制
            return toHexString(md5);
        } catch (Exception e) {
            logUtils.error(e);
            return null;
        }
    }

    /**
     * 对字符串HMAC_SHA384加密(小写+字母)
     *
     * @param content 传入要加密的字符串
     * @return 加密后的字符串
     */
    public static String getSha384(String content) {
        try {
            // 计算md5函数
            byte[] md5 = getMd5ByByte(content.getBytes(Charset.defaultCharset()),
                    AlgorithmTypeEnums.SHA384);
            // 转换为16进制
            return toHexString(md5);
        } catch (Exception e) {
            logUtils.error(e);
            return null;
        }
    }

    /**
     * 将字节数组进行 MD5 加密
     *
     * @param data 字节数组
     * @return 返回加密结果
     */
    private static byte[] getMd5ByByte(byte[] data, AlgorithmTypeEnums autographType) {
        byte[] bytes;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest messageDigest = MessageDigest.getInstance(autographType.getValue());
            // 计算md5函数
            messageDigest.update(data);
            bytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logUtils.error(e);
            bytes = new byte[]{};
        }
        return bytes;
    }

    /**
     * 将加密后的字节数组，转换成16进制的字符串
     *
     * @param md5 md5值
     * @return 返回结果
     */
    private static String toHexString(byte[] md5) {
        StringBuilder builder = new StringBuilder();
        for (byte by : md5) {
            String hex = Integer.toHexString(by & 0xff);
            if (hex.length() == IntegerConsts.ONE) {
                builder.append(StringUtils.ZERO);
            }
            builder.append(hex);
        }
        // 返回结果
        return builder.toString();
    }
}
