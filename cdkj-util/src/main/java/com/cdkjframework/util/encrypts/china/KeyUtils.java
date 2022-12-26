package com.cdkjframework.util.encrypts.china;

import com.cdkjframework.util.log.LogUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.util.encrypts.china
 * @ClassName: KeyUtils
 * @Description: 国密公私钥对 工具类
 * @Author: xiaLin
 * @Date: 2022/12/10 13:05
 * @Version: 1.0
 */
public class KeyUtils {

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(KeyUtils.class);

  /**
   * STD 名称
   */
  final static String stdName = "sm2p256v1";

  /**
   * STD 名称
   */
  final static String algorithm = "EC";

  /**
   * 生成国密公私钥对
   *
   * @return 返回结果
   * @throws Exception 异常信息
   */
  public static String[] generateSmKey() throws Exception {
    KeyPairGenerator keyPairGenerator = null;
    SecureRandom secureRandom = new SecureRandom();
    ECGenParameterSpec sm2Spec = new ECGenParameterSpec(stdName);
    keyPairGenerator = KeyPairGenerator.getInstance(algorithm, new BouncyCastleProvider());
    keyPairGenerator.initialize(sm2Spec);
    keyPairGenerator.initialize(sm2Spec, secureRandom);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();
    //String[0] 公钥
    //String[1] 私钥
    String[] result = {
        new String(Base64.getEncoder().encode(publicKey.getEncoded())),
        new String(Base64.getEncoder().encode(privateKey.getEncoded()))
    };
    // 返回结果
    return result;
  }

  /**
   * 将Base64转码的公钥串，转化为公钥对象
   *
   * @param publicKey 公钥匙
   * @return 返回结果
   */
  public static PublicKey createPublicKey(String publicKey) {
    PublicKey key = null;
    try {
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
      KeyFactory keyFactory = KeyFactory.getInstance(algorithm, new BouncyCastleProvider());
      key = keyFactory.generatePublic(publicKeySpec);
    } catch (Exception e) {
      logUtils.error(e, e.getMessage());
    }
    return key;
  }

  /**
   * 将Base64转码的私钥串，转化为私钥对象
   *
   * @param privateKey 私钥匙
   * @return 返回结果
   */
  public static PrivateKey createPrivateKey(String privateKey) {
    PrivateKey key = null;
    try {
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
      KeyFactory keyFactory = KeyFactory.getInstance(algorithm, new BouncyCastleProvider());
      key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    } catch (Exception e) {
      logUtils.error(e, e.getMessage());
    }
    return key;
  }
}
