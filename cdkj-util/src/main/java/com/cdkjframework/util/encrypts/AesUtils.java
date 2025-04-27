package com.cdkjframework.util.encrypts;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

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
   * 配置信息
   */
  private static CustomConfig customConfig;

  /**
   * 构造函数
   *
   * @param customConfig 配置
   */
  public AesUtils(CustomConfig customConfig) {
    AesUtils.customConfig = customConfig;
  }


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
    if (customConfig.getAesType().equals(IntegerConsts.ZERO)) {
      cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(), getIvParameterSpec());
    } else {
      cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec());
    }
    byte[] dataBytes = content.getBytes(customConfig.getCharsetName());
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
      content = content.replace(StringUtils.BLANK_SPACE, StringUtils.PLUS);
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
    byte[] decryptBytes = content.getBytes(customConfig.getCharsetName());

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
    return new String(decryptBytes, customConfig.getCharsetName()).trim();
  }

  /**
   * 获取到 Key
   *
   * @return 返回 Cipher
   */
  private static Cipher initKey() throws NoSuchAlgorithmException, NoSuchPaddingException {
    if (cipher == null) {
      synchronized (AesUtils.class) {
        if (cipher == null) {
          if (customConfig.getAesType().equals(IntegerConsts.ZERO)) {
            cipher = Cipher.getInstance(customConfig.getAesCbcNoPadding());
          } else {
            cipher = Cipher.getInstance(customConfig.getAesEcbNoPadding());
          }
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
    return new SecretKeySpec(customConfig.getDefaultKey().getBytes(customConfig.getCharsetName()),
        customConfig.getPasswordType());
  }

  /**
   * 四、参数说明
   *
   * @return 返回 参数说明
   * @throws UnsupportedEncodingException 异常信息
   */
  private static IvParameterSpec getIvParameterSpec() throws UnsupportedEncodingException {
    return new IvParameterSpec(customConfig.getDefaultIv().getBytes(customConfig.getCharsetName()));
  }
}
