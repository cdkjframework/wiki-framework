package com.cdkjframework.util.encrypts;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.encrypts
 * @ClassName: AesUtils
 * @Description: 加密工具类 实现aes加密、解密
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class AesUtils {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(AesUtils.class);

    /**
     * 密钥（16进制，和前台保持一致，或者是作为参数直接传过来也可以）
     */
    private static final String DEFAULT_KEY = "cn.framewiki.com";

    /**
     * 算法 PKCS5 Padding
     */
    private static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * 密码类型
     */
    private static final String PASSWORD_TYPE = "AES";

    /**
     * 编码类型
     */
    private static final String CHARSET_NAME = "utf-8";

    /**
     * 长度
     */
    private static int KEY_GENERATOR_LENGTH = 128;

    /**
     * Base 64 编码
     *
     * @param content 加密内容
     * @return 返回结果
     */
    public static String base64Encode(String content) {
        String encode = "";
        try {
            byte[] bytes = encrypt(content);
            encode = Base64Utils.encode(bytes);
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        // 编码结果
        return encode;
    }

    /**
     * AES 加密
     *
     * @param content 加密内容
     * @return 返回结果
     * @throws Exception 异常信息
     */
    public static byte[] encrypt(String content) throws Exception {
        Cipher cipher = initKey();
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(DEFAULT_KEY.getBytes(), PASSWORD_TYPE));

        return cipher.doFinal(content.getBytes(CHARSET_NAME));
    }

    /**
     * Base 64 编码
     *
     * @param content 加密内容
     * @return 返回结果
     */
    public static String base64Decrypt(String content) {
        String encryptString = "";
        try {
            encryptString = Base64Utils.decodeData(content);
            encryptString = decrypt(encryptString);
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        // 编码结果
        return encryptString;
    }

    /**
     * Base 64 编码
     *
     * @param content 加密内容
     * @return 返回结果
     */
    public static String base64Decrypt(byte[] content) {
        String encryptString = "";
        try {
            byte[] bytes = Base64Utils.decodeDataToByte(content);
            encryptString = decrypt(bytes);
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        // 编码结果
        return encryptString;
    }

    /**
     * AES解密
     *
     * @param content 解密内容
     * @return 返回结果
     * @throws Exception 异常信息
     */
    public static String decrypt(String content) throws Exception {
        byte[] encryptBytes = content.getBytes(CHARSET_NAME);
        Cipher cipher = initKey();
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(DEFAULT_KEY.getBytes(), PASSWORD_TYPE));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes, CHARSET_NAME);
    }

    /**
     * AES解密
     *
     * @param content 解密内容
     * @return 返回结果
     * @throws Exception 异常信息
     */
    public static String decrypt(byte[] content) throws Exception {
        Cipher cipher = initKey();
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(DEFAULT_KEY.getBytes(), PASSWORD_TYPE));
        byte[] decryptBytes = cipher.doFinal(content);

        return new String(decryptBytes, CHARSET_NAME);
    }

    /**
     * 获取到 Key
     *
     * @return 返回 Cipher
     */
    private static Cipher initKey() throws NoSuchAlgorithmException, NoSuchPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(PASSWORD_TYPE);
        keyGenerator.init(KEY_GENERATOR_LENGTH);
        return Cipher.getInstance(AES_ECB_PKCS5_PADDING);
    }
}
