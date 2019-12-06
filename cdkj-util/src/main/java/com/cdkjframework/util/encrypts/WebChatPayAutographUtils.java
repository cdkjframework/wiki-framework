package com.cdkjframework.util.encrypts;

import com.cdkjframework.constant.WebChatPayConsts;
import com.cdkjframework.enums.AutographTypeEnums;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.encrypts
 * @ClassName: WebChatPayAutographUtils
 * @Description: 微信支付签名
 * @Author: xiaLin
 * @Version: 1.0
 */

public class WebChatPayAutographUtils {

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key  API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, AutographTypeEnums.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data     待签名数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, AutographTypeEnums signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder builder = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WebChatPayConsts.FIELD_SIGN)) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (data.get(k).trim().length() > 0) {
                builder.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        builder.append("key=").append(key);
        // 签名加密
        String signString;
        switch (signType) {
            case HMACSHA256:
                signString = HMACSHA256(builder.toString(), key);
                break;
            case MD5:
                signString = MD5(builder.toString()).toUpperCase();
                break;
            default:
                throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
        // 返回签名字符串
        return signString;
    }


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (byte item : array) {
            builder.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (byte item : array) {
            builder.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return builder.toString().toUpperCase();
    }
}
