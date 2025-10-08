package com.cdkjframework.oauth2.service.impl;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.oauth2.config.Oauth2Config;
import com.cdkjframework.oauth2.entity.AuthorizationCode;
import com.cdkjframework.oauth2.entity.OAuth2Token;
import com.cdkjframework.oauth2.entity.TokenResponse;
import com.cdkjframework.oauth2.provider.JwtTokenProvider;
import com.cdkjframework.oauth2.repository.AuthorizationCodeRepository;
import com.cdkjframework.oauth2.repository.CustomRegisteredClientRepository;
import com.cdkjframework.oauth2.repository.OAuth2TokenRepository;
import com.cdkjframework.oauth2.service.CustomOauth2AuthorizationService;
import com.cdkjframework.oauth2.service.Oauth2TokenService;
import com.cdkjframework.oauth2.service.TokenEventService;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * OAuth2授权服务实现类
 *
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.service.impl
 * @ClassName: Oauth2AuthorizationServiceImpl
 * @Description: OAuth2授权服务实现类
 * @Author: xiaLin
 * @Date: 2025/7/31 21:16
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomOauth2AuthorizationServiceImpl implements CustomOauth2AuthorizationService {

  /**
   * 自定义注册的客户端存储库
   */
  private final CustomRegisteredClientRepository registeredClientRepository;

  /**
   * 授权码存储库
   */
  private final AuthorizationCodeRepository authorizationCodeRepository;

  /**
   * OAuth2客户端存储库
   */
  private final OAuth2TokenRepository auth2TokenRepository;

  /**
   * oauth2令牌服务
   */
  private final Oauth2TokenService oauth2TokenService;

  /**
   * Oauth2配置
   */
  private final Oauth2Config oauth2Config;

  /**
   * 事件服务（新增字段）
   */
  private final TokenEventService tokenEventService;

  /**
   * 安全字符集（排除易混淆字符）
   */
  private static final String SAFE_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz23456789";

  /**
   * 授权端点
   *
   * @param clientId     客户端ID
   * @param responseType 响应类型
   * @param scope        授权范围
   * @return code
   */
  @Override
  public ResponseBuilder authorizationCode(String clientId, String responseType, String scope) {
    HttpServletResponse response = HttpServletUtils.getResponse();
    // 验证客户端
    RegisteredClient client = registeredClientRepository.findByClientId(clientId);
    if (client == null) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(CODE_ERROR, "Invalid client ID");
    }
    // 生成授权编码
    String authCode = generate(IntegerConsts.SEVEN);

    AuthorizationCode code = new AuthorizationCode();
    code.setCode(authCode);
    code.setClientId(clientId);
    code.setRedirectUri(client.getRedirectUris().stream().findFirst().orElse(null));
    code.setIssuedAt(LocalDateTime.now());
    // 设置过期时间为10分钟后
    code.setExpiryAt(code.getIssuedAt().plusMinutes(IntegerConsts.TEN));

    // 检查授权码是否已存在
    authorizationCodeRepository.save(code);

    // 返回授权码
    return ResponseBuilder.successBuilder(authCode);
  }

  /**
   * 获取访问令牌
   *
   * @param grantType    授权类型
   * @param clientId     客户端ID
   * @param code         授权码
   * @param timestamp    时间戳
   * @param refreshToken 刷新令牌
   * @param signature    签名
   * @return 访问令牌
   */
  @Override
  public TokenResponse token(String grantType, String clientId, String code, String timestamp, String refreshToken, String signature) {
    HttpServletResponse response = HttpServletUtils.getResponse();
    HttpServletRequest request = HttpServletUtils.getRequest();
    long start = System.currentTimeMillis();

    // 记录请求事件（使用 REQUEST 常量）
    safeAddTokenEvent(clientId, grantType, REQUEST, true, 0, null, null, request, Map.of("phase", "request"));

    if (StringUtils.isNullAndSpaceOrEmpty(grantType)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      recordError(grantType, clientId, start, null, "grant_type not found", request);
      throw new GlobalRuntimeException(GRANT_TYPE, "grant_type not found");
    }

    AuthorizationGrantType authorizationGrantType = new AuthorizationGrantType(grantType.toLowerCase());
    try {
      if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(authorizationGrantType)) {
        return wrapIssue(start, grantType, clientId,
            () -> handleAuthorizationCode(response, authorizationGrantType, clientId, code, timestamp, signature), request);
      } else if (AuthorizationGrantType.REFRESH_TOKEN.equals(authorizationGrantType)) {
        return wrapRefresh(start, grantType, clientId,
            () -> handleRefreshToken(response, authorizationGrantType, refreshToken), request);
      } else {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        recordError(grantType, clientId, start, null, "Unsupported grant_type", request);
        throw new GlobalRuntimeException(GRANT_TYPE, "Unsupported grant_type");
      }
    } catch (RuntimeException ex) {
      // wrapIssue / wrapRefresh 已处理失败事件，这里只兜底非 GlobalRuntimeException
      if (!(ex instanceof GlobalRuntimeException)) {
        recordError(grantType, clientId, start, null, ex.getMessage(), request);
      }
      throw ex;
    }
  }

  // 包装授权码模式发放流程
  private TokenResponse wrapIssue(long start, String grantType, String clientId, Callable<TokenResponse> supplier, HttpServletRequest request) {
    try {
      TokenResponse tr = supplier.call();
      int latency = (int) (System.currentTimeMillis() - start);
      safeAddTokenEvent(clientId, grantType, ISSUED, true, latency, sha256(tr.getAccessToken()), null, request, Map.of("mode", "authorization_code"));
      return tr;
    } catch (Exception ex) {
      int latency = (int) (System.currentTimeMillis() - start);
      safeAddTokenEvent(clientId, grantType, ISSUED, false, latency, null, null, request, Map.of("error", ex.getMessage()));
      if (ex instanceof RuntimeException re) throw re; else throw new RuntimeException(ex);
    }
  }

  // 包装刷新令牌流程
  private TokenResponse wrapRefresh(long start, String grantType, String clientId, Callable<TokenResponse> supplier, HttpServletRequest request) {
    try {
      TokenResponse tr = supplier.call();
      int latency = (int) (System.currentTimeMillis() - start);
      // 使用自定义事件名 REFRESH（常量中无，复用 ISSUED/REQUEST 也可，这里用 REVOKED 不合适）
      safeAddTokenEvent(clientId, grantType, "REFRESH", true, latency, sha256(tr.getAccessToken()), null, request, Map.of("mode", "refresh_token"));
      return tr;
    } catch (Exception ex) {
      int latency = (int) (System.currentTimeMillis() - start);
      safeAddTokenEvent(clientId, grantType, "REFRESH", false, latency, null, null, request, Map.of("error", ex.getMessage()));
      if (ex instanceof RuntimeException re) throw re; else throw new RuntimeException(ex);
    }
  }

  // 错误统一封装使用 ERROR 常量
  private void recordError(String grantType, String clientId, long start, String tokenId, String reason, HttpServletRequest request) {
    int latency = (int) (System.currentTimeMillis() - start);
    safeAddTokenEvent(clientId, grantType, "ERROR", false, latency, tokenId, null, request, Map.of("reason", reason));
  }

  private void safeAddTokenEvent(String clientId, String grantType, String eventType, boolean success, Integer latency,
                                 String tokenId, String principal, HttpServletRequest request, Map<String, Object> extra) {
    try {
      String ip = clientIp(request);
      String ua = userAgent(request);
      tokenEventService.addTokenEvent(clientId, grantType, eventType, success, latency, tokenId, principal, ip, ua, extra);
    } catch (Exception ignored) {
    }
  }

  private String clientIp(HttpServletRequest request) {
    if (request == null) return null;
    String ip = request.getHeader(X_FORWARDED_FOR);
    if (ip == null || ip.isBlank()) ip = request.getRemoteAddr();
    return ip;
  }

  private String userAgent(HttpServletRequest request) {
    return request == null ? null : request.getHeader(USER_AGENT);
  }

  private String sha256(String token) {
    if (token == null) return null;
    try {
      return tokenEventService.sha256Hex(token);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 验证时间戳
   *
   * @param timestamp 时间戳
   */
  private void verifyTimestamp(String timestamp) {
    LocalDateTime currentTime = LocalDateTime.now();
    // 这里假设 timestamp 是一个表示毫秒的字符串
    long requestTimeMillis;
    try {
      requestTimeMillis = Long.parseLong(timestamp);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid timestamp format");
    }

    //
    LocalDateTime requestTime = LocalDateTime.ofEpochSecond(requestTimeMillis, IntegerConsts.ZERO, java.time.ZoneOffset.UTC);
    LocalDateTime beijingTime = requestTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Shanghai"))
        .toLocalDateTime();

    // 允许的时间窗口（例如5分钟）
    long allowedWindowMillis = IntegerConsts.FIVE * IntegerConsts.SIXTY * IntegerConsts.ONE_THOUSAND;

    long timeDifference = Math.abs(java.time.Duration.between(currentTime, beijingTime).toMillis());
    if (timeDifference > allowedWindowMillis) {
      throw new IllegalArgumentException("Timestamp is out of the allowed range");
    }
  }

  /**
   * 验证签名 md5 签名
   *
   * @param client    注册的客户端
   * @param signature 签名
   * @param clientId  客户端ID
   * @param timestamp 时间戳
   */
  private void verifySignature(RegisteredClient client, String signature, String clientId, String timestamp) {
    String sign = JwtTokenProvider.md5Signature(client.getClientSecret(), clientId, timestamp);
    if (!sign.equals(signature)) {
      throw new IllegalArgumentException("Invalid signature");
    }
  }

  /**
   * 生成指定长度的随机字符串
   *
   * @param length 生成字符串的长度
   * @return 随机字符串
   */
  public String generate(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("Length must be positive");
    }

    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(SAFE_CHARACTERS.length());
      sb.append(SAFE_CHARACTERS.charAt(index));
    }
    return sb.toString();
  }

  // ======= 恢复原私有方法：处理授权码、刷新令牌与构建令牌 =======
  /**
   * 处理授权码模式
   */
  private TokenResponse handleAuthorizationCode(HttpServletResponse response, AuthorizationGrantType grantType,
                                                String clientId, String code, String timestamp, String signature) {
    try {
      verifyTimestamp(timestamp);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(TIMESTAMP_ERROR, e.getMessage());
    }
    AuthorizationCode authorizationCode = authorizationCodeRepository.findByCode(code);
    if (ObjectUtils.isEmpty(authorizationCode)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(CODE_ERROR, "Invalid authorization code");
    }
    if (authorizationCode.isExpired()) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(CODE_EXPIRED, "expired authorization code");
    }
    RegisteredClient client = registeredClientRepository.findByClientId(clientId);
    if (ObjectUtils.isEmpty(client)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(CLIENT_ERROR, "client_id not found");
    }
    try {
      verifySignature(client, signature, clientId, timestamp);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(SIGNATURE_ERROR, e.getMessage());
    }
    if (!client.getAuthorizationGrantTypes().contains(grantType)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(GRANT_TYPE_ERROR, "Invalid grant_type");
    }
    OAuth2Token oauth2Token = new OAuth2Token();
    oauth2Token.setUserId(code);
    return buildToken(client, oauth2Token);
  }

  /**
   * 刷新访问令牌
   */
  private TokenResponse handleRefreshToken(HttpServletResponse response, AuthorizationGrantType grantType, String refreshToken) {
    if (StringUtils.isNullAndSpaceOrEmpty(refreshToken)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(REFRESH_TOKEN_ERROR, "Invalid refresh token");
    }
    OAuth2Token oauth2Token = auth2TokenRepository.findByRefreshToken(refreshToken);
    if (ObjectUtils.isEmpty(oauth2Token) || StringUtils.isNullAndSpaceOrEmpty(oauth2Token.getRefreshToken())) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(REFRESH_TOKEN_ERROR, "Invalid refresh token");
    }
    int status = ConvertUtils.convertInt(oauth2Token.getStatus());
    if (!IntegerConsts.ONE.equals(status)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(REFRESH_TOKEN_ERROR, "Invalid refresh token");
    }
    if (oauth2Token.isExpired()) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      throw new GlobalRuntimeException(REFRESH_TOKEN_EXPIRED, "Refresh token has expired");
    }
    RegisteredClient client = registeredClientRepository.findByClientId(oauth2Token.getClientId());
    assert client != null;
    return buildToken(client, oauth2Token);
  }

  /**
   * 构建并返回新的访问令牌和刷新令牌
   */
  private TokenResponse buildToken(RegisteredClient client, OAuth2Token oauth2Token) {
    TokenSettings settings = client.getTokenSettings();
    Long accessTokenTimeToLive, refreshTokenTimeToLive;
    if (settings != null && CollectUtils.isNotEmpty(settings.getSettings())) {
      Map<String, Object> map = settings.getSettings();
      accessTokenTimeToLive = ConvertUtils.convertLong(map.get(ACCESS_TOKEN_TIME_TO_LIVE));
      refreshTokenTimeToLive = ConvertUtils.convertLong(map.get(REFRESH_TOKEN_TIME_TO_LIVE));
    } else {
      accessTokenTimeToLive = oauth2Config.getAccessTokenTimeToLive();
      refreshTokenTimeToLive = oauth2Config.getRefreshTokenTimeToLive();
    }
    String newAccessToken = JwtTokenProvider.generateToken(client.getClientId(), accessTokenTimeToLive);
    oauth2TokenService.validateToken(newAccessToken);
    String newRefreshToken = JwtTokenProvider.generateRefreshToken(client.getClientId(), refreshTokenTimeToLive);
    LocalDateTime now = LocalDateTime.now();
    oauth2Token.setAccessToken(newAccessToken);
    oauth2Token.setRefreshToken(newRefreshToken);
    oauth2Token.setIssuedAt(now);
    oauth2Token.setClientId(client.getClientId());
    oauth2Token.setExpiration(now.plusDays(IntegerConsts.SEVEN));
    auth2TokenRepository.save(oauth2Token);
    TokenResponse tokenResponse = new TokenResponse();
    tokenResponse.setAccessToken(newAccessToken);
    tokenResponse.setRefreshToken(newRefreshToken);
    tokenResponse.setExpiresIn(accessTokenTimeToLive);
    return tokenResponse;
  }
  // ======= 恢复私有方法结束 =======
}
