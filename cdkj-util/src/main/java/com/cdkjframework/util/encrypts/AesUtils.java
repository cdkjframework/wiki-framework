package com.cdkjframework.util.encrypts;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
     * 密码
     */
    private static Cipher cipher;

    /**
     * 密钥（16进制，和前台保持一致，或者是作为参数直接传过来也可以）
     */
    private static final String DEFAULT_KEY = "cn.framewiki.com";

    /**
     * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
     */
    private static String DEFAULT_IV = "hk.framewiki.com";

    /**
     * 算法 PKCS5 Padding
     */
    private static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";

    /**
     * 密码类型
     */
    private static final String PASSWORD_TYPE = "AES";

    /**
     * 编码类型
     */
    private static final String CHARSET_NAME = "utf-8";

    /**
     * Base 64 编码
     *
     * @param content 加密内容
     * @return 返回结果
     */
    public static String base64Encode(String content) {
        String encode = StringUtils.Empty;
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
        // 初始化键值
        Cipher cipher = initKey();

        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(), getIvParameterSpec());

        byte[] dataBytes = content.getBytes(CHARSET_NAME);
        byte[] plainText = byteLengthCompletion(cipher.getBlockSize(), dataBytes);

        // 返回加密结果
        return cipher.doFinal(plainText);
    }

    /**
     * Base 64 编码
     *
     * @param content 加密内容
     * @return 返回结果
     */
    public static String base64Decrypt(String content) {
        String decryptString = StringUtils.Empty;
        try {
            byte[] decodeDataToByte = Base64Utils.decodeDataToByte(content);
            decryptString = decrypt(decodeDataToByte);
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        // 编码结果
        return decryptString;
    }

    /**
     * Base 64 编码
     *
     * @param content 加密内容
     * @return 返回结果
     */
    public static String base64Decrypt(byte[] content) {
        String encryptString = StringUtils.Empty;
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
        // 转换为字节
        byte[] decryptBytes = content.getBytes(CHARSET_NAME);

        return decrypt(decryptBytes);
    }

    /**
     * AES解密
     *
     * @param content 解密内容
     * @return 返回结果
     * @throws Exception 异常信息
     */
    public static String decrypt(byte[] content) throws Exception {

        // 初始化键值
        Cipher cipher = initKey();

        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(), getIvParameterSpec());

        byte[] plainText = byteLengthCompletion(cipher.getBlockSize(), content);

        // 解密
        byte[] decryptBytes = cipher.doFinal(plainText);

        // 返回解密结果
        return new String(decryptBytes, CHARSET_NAME).trim();
    }

    /**
     * 获取到 Key
     *
     * @return 返回 Cipher
     */
    private static Cipher initKey() throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        if (cipher == null) {
            synchronized (AesUtils.class) {
                if (cipher == null) {
                    cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
                }
            }
        }
        // 返回结果
        return cipher;
    }

    /**
     * 字节长度补全
     *
     * @param blockSize 块大小
     * @param dataBytes 数据
     * @return 返回补全字节数组
     */
    private static byte[] byteLengthCompletion(int blockSize, byte[] dataBytes) {
        // 文本长度
        int plainTextLength = dataBytes.length;

        if (plainTextLength % blockSize != 0) {
            plainTextLength = plainTextLength + (blockSize - (plainTextLength % blockSize));
        }

        byte[] plainText = new byte[plainTextLength];
        System.arraycopy(dataBytes, 0, plainText, 0, dataBytes.length);

        // 返回结果
        return plainText;
    }


    /**
     * 密钥规范
     *
     * @return 返回 密钥规范
     * @throws UnsupportedEncodingException 异常信息
     */
    private static SecretKeySpec getSecretKeySpec() throws UnsupportedEncodingException {
        return new SecretKeySpec(DEFAULT_KEY.getBytes(CHARSET_NAME), PASSWORD_TYPE);
    }

    /**
     * 四、参数说明
     *
     * @return 返回 参数说明
     * @throws UnsupportedEncodingException 异常信息
     */
    private static IvParameterSpec getIvParameterSpec() throws UnsupportedEncodingException {
        return new IvParameterSpec(DEFAULT_IV.getBytes(CHARSET_NAME));
    }
}
