package com.cdkjframework.oauth2.constant;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.constant
 * @ClassName: OAuth2Constant
 * @Description: 常量
 * @Author: xiaLin
 * @Date: 2025/7/31 16:51
 * @Version: 1.0
 */
public interface OAuth2Constant {

  /**
   * 授权端点
   * 注意：不以双斜杠结尾，避免路径拼接出现
   */
  String OAUTH2 = "/oauth2";

  /**
   * 未知
   */
  String UNKNOWN = "unknown";

  /**
   * 请求
   */
  String REQUEST = "REQUEST";

  /**
   * 代理
   */
  String USER_AGENT = "User-Agent";

  /**
   * 认证成功
   */
  String AUTH_SUCCESS = "AUTH_SUCCESS";

  /**
   * 认证成功总数
   */
  String AUTH_SUCCESS_TOTAL = AUTH_SUCCESS.toLowerCase() + "_total";

  /**
   * 认证失败
   */
  String AUTH_FAILURE = "AUTH_FAILURE";

  /**
   * 认证失败总数
   */
  String AUTH_FAILURE_TOTAL = AUTH_FAILURE.toLowerCase() + "_total";

  /**
   * 认证失败总数
   */
  String AUTH_EVENTS_TOTAL = "AUTH_EVENTS_TOTAL";

  /**
   * 令牌请求总数
   */
  String TOKEN_REQUESTS_TOTAL = "token_requests_total";

  /**
   * 令牌请求延迟秒数
   */
  String TOKEN_LATENCY_SECONDS = "token_latency_seconds";

  /**
   * 授权端点
   */
  String AUTHORIZE = OAUTH2 + "/authorize";

  /**
   * 代理头
   */
  String X_FORWARDED_FOR = "X-Forwarded-For";

  /**
   * 访问令牌端点
   */
  String OAUTH2_ACCESS_TOKEN = OAUTH2 + "/token";

  /**
   * 访问令牌端点
   */
  String TOKEN = "/token";
  /**
   * 撤销令牌端点
   */
  String OAUTH2_REVOKE = OAUTH2 + "/revoke";

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
   * 客户端ID
   */
  String CLIENT_ID = "client_id";
  /**
   * 远程IP
   */
  String REMOTE_IP = "remote_ip";
  /**
   * 开始时间
   */
  String START_TIME = "start_time";

  /**
   * 令牌请求总数
   */
  String TOKEN_ISSUED_TOTAL = "token_issued_total";

  /**
   * 令牌撤销总数
   */
  String TOKEN_REVOKED_TOTAL = "token_revoked_total";

  /**
   * 令牌撤销失败总数
   */
  String TOKEN_REVOCATION_FAILURES_TOTAL = "token_revocation_failures_total";

  /**
   * 签发
   */
  String ISSUED = "ISSUED";

  /**
   * 撤销
   */
  String REVOKED = "REVOKED";

  /**
   * 空
   */
  String EMPTY = "";

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
   * 令牌时间
   */
  String ACCESS_TOKEN_TIME_TO_LIVE = "accessTokenTimeToLive";

  /**
   * 刷新令牌时间
   */
  String REFRESH_TOKEN_TIME_TO_LIVE = "refreshTokenTimeToLive";

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
