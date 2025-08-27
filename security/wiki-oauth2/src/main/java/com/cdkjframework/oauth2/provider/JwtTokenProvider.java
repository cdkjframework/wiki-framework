package com.cdkjframework.oauth2.provider;

import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.oauth2.constant.OAuth2Constant;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.TOKEN_TYPE;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.service
 * @ClassName: JwtTokenProvider
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/31 13:30
 * @Version: 1.0
 */
@Component
public class JwtTokenProvider {

  /**
   * 生成 JWT Token
   *
   * @param clientId 客户端ID
   * @return 返回  JWT Token
   */
  public static String generateToken(String clientId) {
    LocalDateTime now = LocalDateTime.now();
    // 1 天有效期
    LocalDateTime expiryDate = now.plusDays(7);
    Instant time = expiryDate.atZone(ZoneId.systemDefault()).toInstant();
    Long seconds = time.getEpochSecond() * IntegerConsts.ONE_THOUSAND;

    return JwtUtils.createJwt(parseToken(clientId, time.getEpochSecond()), OAuth2Constant.SECRET_KEY, seconds);
  }

  /**
   * 生成 Refresh Token
   *
   * @param clientId 客户端ID
   * @return 返回 Refresh Token
   */
  public static String generateRefreshToken(String clientId) {
    Instant time = Instant.now().plus(IntegerConsts.THIRTY, ChronoUnit.DAYS);
    // 构建包含客户端标识和唯一性的JWT
    return Jwts.builder()
        // JWT唯一标识
        .setId(UUID.randomUUID().toString())
        // 绑定客户端ID[1,8](@ref)
        .setSubject(clientId)
        // 签发时间
        .setIssuedAt(Date.from(Instant.now()))
        .setClaims(parseToken(clientId, time.getEpochSecond()))
        // 30 天有效期[4](@ref)
        .setExpiration(Date.from(time))
        // 明确令牌类型
        .claim(TOKEN_TYPE, "refresh")
        // 强加密签名[2](@ref)
        .signWith(SignatureAlgorithm.HS512, OAuth2Constant.SECRET_KEY)
        .compact();
  }

  /**
   * 解析 Token
   *
   * @param clientId 客户端ID
   * @param time     时间
   * @return 返回 解析后的 Token 信息
   */
  private static Map<String, Object> parseToken(String clientId, Long time) {
    // 生成 JWT token
    Map<String, Object> map = new HashMap<>(IntegerConsts.FOUR);
    map.put(BusinessConsts.LOGIN_NAME, clientId);
    map.put(BusinessConsts.TIME, time);
    map.put(BusinessConsts.USER_NAME, clientId);
    map.put(BusinessConsts.USER_TYPE, clientId);
    map.put(BusinessConsts.DISPLAY_NAME, clientId);
    String token = Md5Utils.getMd5(clientId);
    map.put(BusinessConsts.HEADER_TOKEN, token);

    return map;
  }

  /**
   * 解析 Token 获取用户名（clientId）
   *
   * @param token 令牌
   * @return 返回 用户名（clientId）
   */
  public static String getClientIdFromToken(String token) {
    Claims claims = JwtUtils.parseJwt(token, OAuth2Constant.SECRET_KEY);
    if (claims == null) {
      return null;
    }
    return ConvertUtils.convertString(claims.get(BusinessConsts.LOGIN_NAME));
  }

  /**
   * 验证 Token 是否有效
   *
   * @param token 令牌
   * @return 返回是否有效
   */
  public static boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(OAuth2Constant.SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 生成 MD5 签名
   *
   * @param clientSecret 客户端密钥
   * @param clientId     客户端ID
   * @param timestamp    时间戳
   * @return 返回 MD5 签名
   */
  public static String md5Signature(String clientSecret, String clientId, String timestamp) {
    String input = "client_id=" + clientId + "&client_secret=" + clientSecret + "&timestamp=" + timestamp;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes());
      byte[] digest = md.digest();
      StringBuilder sb = new StringBuilder();
      for (byte b : digest) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      // Log the error for better debugging, instead of just printing the stack trace
      return "";
    }
  }
}
