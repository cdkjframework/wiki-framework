package com.cdkjframework.util.encrypts;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

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
            // 生成一个MD5加密计算摘要
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            // 计算md5函数
            messageDigest.update(str.getBytes(Charset.defaultCharset()));
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(IntegerConsts.ONE, messageDigest.digest()).toString(IntegerConsts.SIXTEEN);
        } catch (Exception e) {
            logUtils.error(e);
            return null;
        }
    }
}
