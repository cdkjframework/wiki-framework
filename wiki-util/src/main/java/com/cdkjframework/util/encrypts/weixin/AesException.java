package com.cdkjframework.util.encrypts.weixin;

import lombok.Getter;

/**
 * @ProjectName: wiki-util
 * @Package: com.cdkjframework.util.encrypts.weixin
 * @ClassName: PaymentInterfaceService
 * @Description: 支付接口
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@SuppressWarnings("serial")
public class AesException extends Exception {

  /**
   * 错误码
   */
  public final static int OK = 0;
  /**
   * 签名验证错误
   */
  public final static int ValidateSignatureError = -40001;
  /**
   * xml解析失败
   */
  public final static int ParseXmlError = -40002;

  /**
   * 密钥验证错误
   */
  public final static int ComputeSignatureError = -40003;

  /**
   * 必填参数为空
   */
  public final static int IllegalAesKey = -40004;
  /**
   * appid 校验错误
   */
  public final static int ValidateAppidError = -40005;
  /**
   * aes 加密失败
   */
  public final static int EncryptAESError = -40006;
  /**
   * aes 解密失败
   */
  public final static int DecryptAESError = -40007;
  /**
   * base64加密错误
   */
  public final static int IllegalBuffer = -40008;

  /**
   * 错误信息
   */
  private int code;

  /**
   * 获取错误信息
   *
   * @param code 错误码
   * @return 错误信息
   */
  private static String getMessage(int code) {
    switch (code) {
      case ValidateSignatureError:
        return "签名验证错误";
      case ParseXmlError:
        return "xml解析失败";
      case ComputeSignatureError:
        return "sha加密生成签名失败";
      case IllegalAesKey:
        return "SymmetricKey非法";
      case ValidateAppidError:
        return "appid校验失败";
      case EncryptAESError:
        return "aes加密失败";
      case DecryptAESError:
        return "aes解密失败";
      case IllegalBuffer:
        return "解密后得到的buffer非法";
//		case EncodeBase64Error:
//			return "base64加密错误";
//		case DecodeBase64Error:
//			return "base64解密错误";
//		case GenReturnXmlError:
//			return "xml生成失败";
      default:
        return null;
    }
  }

  /**
   * 构造方法
   *
   * @param code 错误码
   */
  AesException(int code) {
    super(getMessage(code));
    this.code = code;
  }

}
