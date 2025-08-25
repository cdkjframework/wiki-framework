package com.cdkjframework.oauth2.service.impl;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.oauth2.entity.AuthorizationCode;
import com.cdkjframework.oauth2.entity.OAuth2Token;
import com.cdkjframework.oauth2.provider.JwtTokenProvider;
import com.cdkjframework.oauth2.repository.*;
import com.cdkjframework.oauth2.service.Oauth2AuthorizationService;
import com.cdkjframework.oauth2.service.Oauth2TokenService;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * OAuth2授权服务实现类
 *
 * @ProjectName: cdjsyun-tool
 * @Package: com.cdkjframework.oauth2.service.impl
 * @ClassName: Oauth2AuthorizationServiceImpl
 * @Description: OAuth2授权服务实现类
 * @Author: xiaLin
 * @Date: 2025/7/31 21:16
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class Oauth2AuthorizationServiceImpl implements Oauth2AuthorizationService {

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
   * 安全字符集（排除易混淆字符）
   */
  private static final String SAFE_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz23456789";

  /**
   * 授权端点
   *
   * @param clientId     客户端ID
   * @param responseType 响应类型
   * @param scope        授权范围
   * @return 授权页面或信息
   */
  @Override
  public ResponseBuilder authorizationCode(String clientId, String responseType, String scope) {
    // 验证客户端
    RegisteredClient client = registeredClientRepository.findByClientId(clientId);
    if (client == null) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(TIMESTAMP_ERROR, "Invalid client ID")));
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
   * @param code      授权码
   * @param timestamp 时间戳
   * @param signature 签名
   * @param clientId  客户端ID
   * @param grantType 授权类型
   * @return 访问令牌
   */
  @Override
  public ResponseBuilder accessToken(String code, String timestamp, String signature, String clientId,
                                     String grantType) {
    // 验证时间戳
    try {
      verifyTimestamp(timestamp);
    } catch (IllegalArgumentException e) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(TIMESTAMP_ERROR, e.getMessage())));
    }

    // 1. 验证授权码是否有效
    AuthorizationCode authorizationCode = authorizationCodeRepository.findByCode(code);
    if (ObjectUtils.isEmpty(authorizationCode)) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(CODE_ERROR, "Invalid authorization code")));
    }
    if (authorizationCode.isExpired()) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(CODE_EXPIRED, "expired authorization code")));
    }

    // 2. 校验客户端 ID 和客户端密钥
    if (!StringUtils.hasText(grantType)) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(GRANT_TYPE, "grant_type not found")));
    }
    // 根据 clientId 查找注册的客户端
    RegisteredClient client = registeredClientRepository.findByClientId(clientId);
    if (ObjectUtils.isEmpty(client)) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(CLIENT_ERROR, "client_id not found")));
    }

    // 验证签名
    try {
      verifySignature(client, signature, clientId, timestamp);
    } catch (IllegalArgumentException e) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(), SIGNATURE_ERROR + e.getMessage());
    }

    AuthorizationGrantType authorizationGrantType = new AuthorizationGrantType(grantType.toLowerCase());
    if (!client.getAuthorizationGrantTypes().contains(authorizationGrantType)) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(GRANT_TYPE_ERROR, "Invalid grant_type")));
    }

    // 3. 生成访问令牌（Access Token）
    String accessToken = JwtTokenProvider.generateToken(clientId);
    oauth2TokenService.validateToken(accessToken);
    // 4. 生成刷新令牌（Refresh Token）
    String refreshToken = JwtTokenProvider.generateRefreshToken(clientId);

    // 5. 将生成的令牌存储到数据库中
    OAuth2Token oauth2Token = new OAuth2Token();
    oauth2Token.setClientId(clientId);
    oauth2Token.setUserId(code);
    oauth2Token.setAccessToken(accessToken);
    oauth2Token.setRefreshToken(refreshToken);
    oauth2Token.setIssuedAt(LocalDateTime.now());
    // 设置访问令牌过期时间为 7 天
    oauth2Token.setExpiration(LocalDateTime.now().plusDays(IntegerConsts.SEVEN));
    auth2TokenRepository.save(oauth2Token);

    // 6. 返回令牌信息
    Map<String, Object> response = new HashMap<>(IntegerConsts.THREE);
    response.put(REFRESH_TOKEN, refreshToken);
    response.put(ACCESS_TOKEN, accessToken);
    response.put(EXPIRES_IN, 3600 * IntegerConsts.TWENTY_TWO * IntegerConsts.SEVEN);
    response.put(TOKEN_TYPE, BEARER);

    // 返回成功响应
    return ResponseBuilder.successBuilder(response);
  }

  /**
   * 刷新访问令牌
   *
   * @param refreshToken 刷新令牌
   * @return 刷新后的访问令牌
   */
  @Override
  public ResponseBuilder refreshToken(String refreshToken) {
    if (!StringUtils.hasText(refreshToken)) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(REFRESH_TOKEN_ERROR, "Invalid refresh token")));
    }
    // 1. 验证刷新令牌是否有效
    OAuth2Token oauth2Token = auth2TokenRepository.findByRefreshToken(refreshToken);
    if (ObjectUtils.isEmpty(oauth2Token) || !StringUtils.hasText(oauth2Token.getRefreshToken())) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(REFRESH_TOKEN_ERROR, "Invalid refresh token")));
    }
    // 2. 检查刷新令牌是否过期
    if (oauth2Token.isExpired()) {
      throw new GlobalRuntimeException(HttpStatus.BAD_REQUEST.value(),
          JsonUtils.objectToJsonString(ResponseBuilder.failBuilder(REFRESH_TOKEN_EXPIRED, "Refresh token has expired")));
    }

    // 3. 重新生成新的访问令牌
    String newAccessToken = JwtTokenProvider.generateToken(oauth2Token.getClientId());

    // 4. 生成新的刷新令牌（可选）
    String newRefreshToken = JwtTokenProvider.generateRefreshToken(oauth2Token.getClientId());
    LocalDateTime localDateTime = LocalDateTime.now();
    // 5. 更新数据库中的刷新令牌（这里我们选择创建一个新刷新令牌）
    oauth2Token.setIssuedAt(localDateTime);
    oauth2Token.setAccessToken(newAccessToken);
    oauth2Token.setRefreshToken(newRefreshToken);
    // 6. 设置访问令牌过期时间为 7 天
    oauth2Token.setExpiration(localDateTime.plusDays(IntegerConsts.SEVEN));
    auth2TokenRepository.save(oauth2Token);

    // 1. 返回令牌信息
    Map<String, Object> response = new HashMap<>(IntegerConsts.THREE);
    response.put(REFRESH_TOKEN, newRefreshToken);
    response.put(ACCESS_TOKEN, newAccessToken);
    response.put(TOKEN_TYPE, BEARER);
    response.put(EXPIRES_IN, 3600 * 24 * IntegerConsts.SEVEN);

    // 返回新的访问令牌
    return ResponseBuilder.successBuilder(response);
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
    LocalDateTime requestTime = LocalDateTime.ofEpochSecond(requestTimeMillis, 0, java.time.ZoneOffset.UTC);
    LocalDateTime beijingTime = requestTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Shanghai"))
        .toLocalDateTime();

    // 允许的时间窗口（例如5分钟）
    long allowedWindowMillis = 5 * 60 * 1000;

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
}
