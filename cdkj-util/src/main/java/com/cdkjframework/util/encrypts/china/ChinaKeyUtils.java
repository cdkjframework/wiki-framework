//package com.cdkjframework.util.encrypts.china;
//
//import com.cdkjframework.constant.IntegerConsts;
//import com.cdkjframework.util.log.LogUtils;
//import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
//import org.bouncycastle.crypto.InvalidCipherTextException;
//import org.bouncycastle.crypto.engines.SM2Engine;
//import org.bouncycastle.crypto.params.ECDomainParameters;
//import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
//import org.bouncycastle.crypto.params.ECPublicKeyParameters;
//import org.bouncycastle.crypto.params.ParametersWithRandom;
//import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
//import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.jce.spec.ECParameterSpec;
//import org.springframework.stereotype.Component;
//
//import java.security.*;
//import java.util.Base64;
//
///**
// * @ProjectName: common-core
// * @Package: com.cdkjframework.util.encrypts.china
// * @ClassName: ChinaKeyUtils
// * @Description: SM2 加密
// * @Author: xiaLin
// * @Date: 2022/12/10 13:02
// * @Version: 1.0
// */
//@Component
//public class ChinaKeyUtils {
//  /**
//   * 日志
//   */
//  private static LogUtils logUtils = LogUtils.getLogger(ChinaKeyUtils.class);
//
//  /**
//   * 公钥
//   */
//  private static PublicKey publicKey = null;
//
//  /**
//   * 私钥
//   */
//  private static PrivateKey privateKey = null;
//
//  /**
//   * 静态执行
//   */
//  static {
//    Security.addProvider(new BouncyCastleProvider());
//    //生成公私钥对
//    try {
//      String[] keys = KeyUtils.generateSmKey();
//      publicKey = KeyUtils.createPublicKey(keys[IntegerConsts.ZERO]);
//      privateKey = KeyUtils.createPrivateKey(keys[IntegerConsts.ONE]);
//    } catch (Exception e) {
//      logUtils.error(e, e.getMessage());
//    }
//  }
//
//  /**
//   * 根据publicKey对原始数据data，使用SM2加密
//   *
//   * @param data 加密数据
//   * @return 返回加密数据
//   */
//  public static String encrypt(String data) {
//    byte[] encrypt = encrypt(data.getBytes(), publicKey);
//    return Base64.getEncoder().encodeToString(encrypt);
//  }
//
//  /**
//   * 根据publicKey对原始数据data，使用SM2加密
//   *
//   * @param data      加密数据
//   * @param publicKey 公钥匙
//   * @return 返回加密数据
//   */
//  public static byte[] encrypt(byte[] data, PublicKey publicKey) {
//    ECPublicKeyParameters localECPublicKeyParameters = null;
//    if (publicKey instanceof BCECPublicKey) {
//      BCECPublicKey localECPublicKey = (BCECPublicKey) publicKey;
//      ECParameterSpec localECParameterSpec = localECPublicKey.getParameters();
//      ECDomainParameters localECDomainParameters = new ECDomainParameters(localECParameterSpec.getCurve(),
//          localECParameterSpec.getG(), localECParameterSpec.getN());
//      localECPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), localECDomainParameters);
//    }
//    SM2Engine localSM2Engine = new SM2Engine();
//    localSM2Engine.init(true, new ParametersWithRandom(localECPublicKeyParameters, new SecureRandom()));
//    try {
//      return localSM2Engine.processBlock(data, IntegerConsts.ZERO, data.length);
//    } catch (InvalidCipherTextException e) {
//      logUtils.error(e, e.toString());
//      return null;
//    }
//  }
//
//  /**
//   * 根据privateKey对加密数据 encodeData，使用SM2解密
//   *
//   * @param encodeData 加密数据
//   * @return 返回结果
//   */
//  public static String decrypt(String encodeData) {
//    byte[] decode = Base64.getDecoder().decode(encodeData);
//    byte[] decrypt = decrypt(decode, privateKey);
//
//    // 返回结果
//    return new String(decrypt);
//  }
//
//  /**
//   * 根据privateKey对加密数据 encodeData，使用SM2解密
//   *
//   * @param encodeData 加密数据
//   * @param privateKey 私钥
//   * @return
//   */
//  public static byte[] decrypt(byte[] encodeData, PrivateKey privateKey) {
//    SM2Engine localSM2Engine = new SM2Engine();
//    BCECPrivateKey sm2PriK = (BCECPrivateKey) privateKey;
//    ECParameterSpec localECParameterSpec = sm2PriK.getParameters();
//    ECDomainParameters localECDomainParameters = new ECDomainParameters(localECParameterSpec.getCurve(),
//        localECParameterSpec.getG(), localECParameterSpec.getN());
//    ECPrivateKeyParameters localECPrivateKeyParameters = new ECPrivateKeyParameters(sm2PriK.getD(),
//        localECDomainParameters);
//    localSM2Engine.init(false, localECPrivateKeyParameters);
//    try {
//      return localSM2Engine.processBlock(encodeData, IntegerConsts.ZERO, encodeData.length);
//    } catch (InvalidCipherTextException e) {
//      logUtils.error(e, e.toString());
//      return null;
//    }
//  }
//
//  /**
//   * 私钥签名
//   *
//   * @param data       数据
//   * @param privateKey 私钥
//   * @return 返回结果
//   * @throws Exception 异常信息
//   */
//  public static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
//    Signature sig = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), BouncyCastleProvider.PROVIDER_NAME);
//    sig.initSign(privateKey);
//    sig.update(data);
//    return sig.sign();
//  }
//
//  /**
//   * 公钥验签
//   *
//   * @param data 数据
//   * @return 返回结果
//   * @throws Exception 异常信息
//   */
//  public static boolean verifyByPublicKey(String data) throws Exception {
//    byte[] bytes = data.getBytes();
//    byte[] sign = signByPrivateKey(bytes, privateKey);
//    return verifyByPublicKey(bytes, publicKey, sign);
//  }
//
//  /**
//   * 公钥验签
//   *
//   * @param data      数据
//   * @param publicKey 公钥
//   * @return 返回结果
//   * @throws Exception 异常信息
//   */
//  public static boolean verifyByPublicKey(byte[] data, PublicKey publicKey, byte[] signature) throws Exception {
//    Signature sig = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), BouncyCastleProvider.PROVIDER_NAME);
//    sig.initVerify(publicKey);
//    sig.update(data);
//    return sig.verify(signature);
//  }
//}
