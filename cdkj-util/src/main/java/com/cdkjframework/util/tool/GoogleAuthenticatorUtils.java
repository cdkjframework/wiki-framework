package com.cdkjframework.util.tool;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: GoogleAuthenticatorUtils
 * @Description: 身份验证
 * @Author: xiaLin
 * @Date: 2023/4/3 15:16
 * @Version: 1.0
 */
public class GoogleAuthenticatorUtils {

    /**
     * 生成的key长度( Generate secret key length)
     */
    public static final int SECRET_SIZE = 10;

    /**
     * SEED 值
     */
    public static final String SEED = "g8GjEvTbW5oVSV7avL47357438reyhreyuryetredLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";

    /**
     * Java实现随机数算法
     */
    public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    /**
     * 最多可偏移的时间
     * // default 3 - max 17
     */
    private int window_size = 3;

    /**
     * 设置窗口大小。这是一个整数值，表示
     * 我们允许30秒的窗口窗口越大
     * 时钟歪斜。
     *
     * @param s window size - must be >=1 and <=17. Other values are ignored
     */
    public void setWindowSize(int s) {
        if (s >= 1 && s <= 17)
            window_size = s;
    }

    /**
     * 生成一个随机密钥。这必须由服务器保存，并且
     * 与用户帐户关联，以验证谷歌显示的代码
     * 身份验证人。用户必须在其设备上注册此机密。
     * 生成一个随机秘钥
     *
     * @return 返回 secret key
     */
    public static String generateSecretKey() {
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            random.setSeed(Base64.decodeBase64(SEED));
            byte[] buffer = random.generateSeed(SECRET_SIZE);
            Base32 codec = new Base32();
            byte[] bEncodedKey = codec.encode(buffer);
            String encodedKey = new String(bEncodedKey);
            return encodedKey;
        } catch (NoSuchAlgorithmException e) {
            // should never occur... configuration error
        }
        return null;
    }

    /**
     * 生成一个google身份验证器，识别的字符串，只需要把该方法返回值生成二维码扫描就可以了。
     *
     * @param user   账号
     * @param secret 密钥
     * @return 返回结果
     */
    public static String getQRBarcode(String user, String secret, String issuer) {
        String format = "otpauth://totp/%s?secret=%s&issuer=%s";
        return String.format(format, user, secret, issuer);
    }

    /**
     * 验证code是否合法
     *
     * @param secret   用户的秘密
     * @param code     用户设备上显示的代码
     * @param timeMsec 时间（毫秒） (System.currentTimeMillis() for example)
     * @return 返回结果
     */
    public boolean checkCode(String secret, long code, long timeMsec) {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        // convert unix msec time into a 30 second "window"
        // this is per the TOTP spec (see the RFC for details)
        long t = (timeMsec / 1000L) / 30L;
        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        for (int i = -window_size; i <= window_size; ++i) {
            long hash;
            try {
                hash = verifyCode(decodedKey, t + i);
            } catch (Exception e) {
                // Yes, this is bad form - but
                // the exceptions thrown would be rare and a static
                // configuration problem
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
                // return false;
            }
            if (hash == code) {
                return true;
            }
        }
        // The validation code is invalid.
        return false;
    }

    /**
     * 验证随机码
     *
     * @param key 值
     * @param t   时间
     * @return 返回结果
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }
}
