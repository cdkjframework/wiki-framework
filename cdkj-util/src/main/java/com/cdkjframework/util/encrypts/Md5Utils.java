package com.cdkjframework.util.encrypts;

import com.cdkjframework.util.log.LogUtils;

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
     * 加密类型
     */
    private static final String ALGORITHM = "MD5";

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(Md5Utils.class);

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String getMd5(String str) {
        try {
            // 计算md5函数
            byte[] md5 = getMd5(str.getBytes(Charset.defaultCharset()));
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
    public static byte[] getMd5(byte[] data) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            // 计算md5函数
            messageDigest.update(data);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logUtils.error(e);
        }
        return new byte[]{};
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
            builder.append(Integer.toHexString(by & 0xff));
        }
        // 返回结果
        return builder.toString();
    }
}
