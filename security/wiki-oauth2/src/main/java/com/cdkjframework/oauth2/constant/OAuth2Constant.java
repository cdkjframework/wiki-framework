package com.cdkjframework.oauth2.constant;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.constant
 * @ClassName: OAuth2Constant
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/31 16:51
 * @Version: 1.0
 */
public interface OAuth2Constant {

    /**
     * 授权端点
     */
    // 注意：不以双斜杠结尾，避免路径拼接出现 //
    String OAUTH2 = "/oauth2/";

    /**
     * 授权端点
     */
    String AUTHORIZATION_CODE = OAUTH2 + "authorize";

    /**
     * 访问令牌端点
     */
    String OAUTH2_ACCESS_TOKEN = OAUTH2 + "access_token";
    /**
     * 刷新令牌端点
     */
    String OAUTH2_REFRESH_TOKEN = OAUTH2 + "refresh_token";
    /**
     * 撤销令牌端点
     */
    String OAUTH2_REVOKE = OAUTH2 + "revoke";

    /**
     * 密钥
     */
    String SECRET_KEY = "cdkj-framework-jwt";

    /**
     * 权限
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 权限值
     */
    String BEARER = "Bearer ";

    /**
     * 授权类型
     */
    String CLIENT_ID = "client_id";

    /**
     * 空
     */
    String EMPTY = "";

    /**
     * 刷新授权码
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 授权码
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * 过期时间
     */
    String EXPIRES_IN = "expires_in";

    /**
     * 授权类型
     */
    String TOKEN_TYPE = "token_type";

    /**
     * 授权编码错误
     */
    Integer CODE_ERROR = 10000;

    /**
     * 授权编码过期
     */
    Integer CODE_EXPIRED = 10001;

    /**
     * client_id 错误
     */
    Integer CLIENT_ERROR = 10002;

    /**
     * 密钥错误
     */
    Integer SECRET_ERROR = 10003;

    /**
     * 刷新令牌错误
     */
    Integer REFRESH_TOKEN_ERROR = 10004;

    /**
     * 刷新令牌过期
     */
    Integer REFRESH_TOKEN_EXPIRED = 10005;

    /**
     * 授权类型未找到
     */
    Integer GRANT_TYPE = 10006;

    /**
     * 授权类型错误
     */
    Integer GRANT_TYPE_ERROR = 10007;

    /**
     * 时间戳错误
     */
    Integer TIMESTAMP_ERROR = 10008;

    /**
     * 签名错误
     */
    Integer SIGNATURE_ERROR = 10009;

    /**
     * 系统错误
     */
    Integer ERROR = 999;
}
