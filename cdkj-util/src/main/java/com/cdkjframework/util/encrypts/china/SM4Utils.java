package com.cdkjframework.util.encrypts.china;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.HexUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.encrypts.china
 * @ClassName: SM4Utils
 * @Author: xiaLin
 * @Description: SM4算法工具类
 * @Date: 2024/5/12 21:51
 * @Version: 1.0
 */
public class SM4Utils {

  /**
   * 编码
   */
  private static final String ENCODING = StandardCharsets.UTF_8.toString();

  /**
   * 算法名称
   */
  public static final String ALGORITHM_NAME = "SM4";

  /**
   * 加密算法/分组加密模式/分组填充方式
   * PKCS5Padding-以8个字节为一组进行分组加密
   * 定义分组加密模式使用：PKCS5Padding
   */
  public static final String ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS5Padding";
  /**
   * 128-32位16进制；256-64位16进制
   */
  public static final int DEFAULT_KEY_SIZE = 128;

  /**
   * 静态初始
   */
  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  /**
   * 自动生成密钥
   *
   * @return 返回密钥
   */
  public static byte[] generateKey() throws Exception {
    return generateKey(DEFAULT_KEY_SIZE);
  }


  /**
   * 自动生成密钥
   *
   * @return 返回结果
   * @throws Exception 异常信息
   */
  public static String generateKeyString() throws Exception {
    byte[] bytes = generateKey();
    return HexUtils.byteToHexString(bytes);
  }

  /**
   * 生成KEY
   *
   * @param keySize KEY大小
   * @return 返回结果
   * @throws Exception 异常信息
   */
  public static byte[] generateKey(int keySize) throws Exception {
    KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
    kg.init(keySize, new SecureRandom());
    return kg.generateKey().getEncoded();
  }

  /**
   * sm4加密
   *
   * @param hexKey   16进制密钥（忽略大小写）
   * @param paramStr 待加密字符串
   * @return 返回16进制的加密字符串
   * @throws Exception
   * @explain 加密模式：CBC
   */
  public static String encrypt(String hexKey, String paramStr) throws Exception {
    // 16进制字符串-->byte[]
    byte[] keyData = HexUtils.hexToByteArray(hexKey);
    // 待加密字符串-->byte[]String-->byte[]
    byte[] srcData = paramStr.getBytes(ENCODING);
    // 加密后的数组
    byte[] cipherArray = encryptCbcPadding(keyData, srcData);

    // byte[]-->hexString
    return HexUtils.encodeHexStr(cipherArray);
  }

  /**
   * 加密模式之CBC
   *
   * @param key  key
   * @param data 数据
   * @return 返回结果
   * @throws Exception 异常
   */
  public static byte[] encryptCbcPadding(byte[] key, byte[] data) throws Exception {
    Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.ENCRYPT_MODE, key);
    return cipher.doFinal(data);
  }

  /**
   * 加密模式之CBC
   *
   * @param algorithmName 算法名称
   * @param mode          类型
   * @param key           key
   * @return 返回结果
   * @throws Exception 异常信息
   */
  private static Cipher generateCbcCipher(String algorithmName, int mode, byte[] key) throws Exception {
    Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
    Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
    cipher.init(mode, sm4Key, generateIV());
    return cipher;
  }

  /**
   * 生成iv
   *
   * @return 返回结果
   * @throws Exception 异常
   */
  public static AlgorithmParameters generateIV() throws Exception {
    // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
    byte[] iv = new byte[IntegerConsts.SIXTEEN];
    AlgorithmParameters params = AlgorithmParameters.getInstance(ALGORITHM_NAME);
    params.init(new IvParameterSpec(iv));
    return params;
  }

  /**
   * sm4解密
   *
   * @param hexKey 16进制密钥
   * @param text   16进制的加密字符串（忽略大小写）
   * @return 解密后的字符串
   * @throws Exception
   * @explain 解密模式：采用CBC
   */
  public static String decrypt(String hexKey, String text) throws Exception {
    // 用于接收解密后的字符串
    String result;
    // hexString-->byte[]
    byte[] keyData = HexUtils.hexToByteArray(hexKey);
    // hexString-->byte[]
    byte[] resultData = HexUtils.hexToByteArray(text);
    // 解密
    byte[] srcData = decryptCbcPadding(keyData, resultData);
    // byte[]-->String
    result = new String(srcData, ENCODING);
    // 返回结果
    return result;
  }

  /**
   * 解密
   *
   * @param key        KEY
   * @param cipherText 密码文本
   * @return 返回字节
   * @throws Exception 异常
   */
  public static byte[] decryptCbcPadding(byte[] key, byte[] cipherText) throws Exception {
    Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.DECRYPT_MODE, key);
    return cipher.doFinal(cipherText);
  }

  public static void main(String[] args) throws Exception {

    String str = "lzzhyl123456";
    System.out.println("==========生成密钥==========");
    String generateKey = generateKeyString();
    System.out.println(generateKey);
    System.out.println("==========加密==========");
    String encrypt = encrypt(generateKey, str);
    System.out.println(encrypt);
    System.out.println("==========解密==========");
    String decrypt = decrypt(generateKey, encrypt);
    System.out.println(decrypt);
  }
}
