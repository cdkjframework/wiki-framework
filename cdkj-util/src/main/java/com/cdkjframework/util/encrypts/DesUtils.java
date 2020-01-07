package com.cdkjframework.util.encrypts;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.encrypts
 * @ClassName: DesUtils
 * @Description: 对称加密
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class DesUtils {

    /**
     * 密码类型
     */
    private static final String PASSWORD_TYPE = "DES";

    /**
     * 密码
     */
    private static Cipher cipher;

    /**
     * 长度
     */
    private static int KEY_GENERATOR_LENGTH = 56;

    /**
     * DES 加密
     */
    public static byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = initKey();
        byte[] cipherBytes = cipher.doFinal(data);
        return cipherBytes;
    }

    /**
     * DES 解密
     */
    public static byte[] decrypt(byte[] data) throws Exception {
        Cipher cipher = initKey();
        byte[] plainBytes = cipher.doFinal(data);
        return plainBytes;
    }

    /**
     * 生成密钥
     *
     * @return 返回结果
     * @throws Exception 异常
     */
    private static Cipher initKey() throws Exception {
        if (cipher == null) {
            synchronized (DesUtils.class) {
                if (cipher == null) {
                    KeyGenerator keyGenerator = KeyGenerator.getInstance(PASSWORD_TYPE);
                    keyGenerator.init(KEY_GENERATOR_LENGTH);
                    SecretKey secretKey = keyGenerator.generateKey();
                    byte[] bytes = secretKey.getEncoded();
                    SecretKey key = new SecretKeySpec(bytes, PASSWORD_TYPE);
                    cipher = Cipher.getInstance(PASSWORD_TYPE);
                    cipher.init(Cipher.DECRYPT_MODE, key);
                }
            }
        }
        // 返回结果
        return cipher;
    }
}