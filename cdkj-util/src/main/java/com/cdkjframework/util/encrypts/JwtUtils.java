package com.cdkjframework.util.encrypts;

import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.util.jwt
 * @ClassName: jwtUtil
 * @Description: jwt 工具
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class JwtUtils {

  /**
   * 签名算法
   */
  private static final SignatureAlgorithm signatureAlgorithm;

  /**
   * 静态默认值
   */
  static {
    signatureAlgorithm = SignatureAlgorithm.HS512;
  }

  /**
   * 解密
   *
   * @param jsonWebToken   token
   * @param base64Security 密钥
   * @return 返回结果
   */
  public static Claims parseJwt(String jsonWebToken, String base64Security) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(base64Security.getBytes())
          .parseClaimsJws(jsonWebToken).getBody();
      return claims;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * 前三个参数为自己用户token的一些信息比如id，权限，名称等。不要将隐私信息放入（大家都可以获取到）
   *
   * @param map            参数
   * @param base64Security 密钥
   * @return 返回结果
   */
  public static String createJwt(Map<String, Object> map, String base64Security) {
    //添加构成JWT的参数
    JwtBuilder builder = builderJwt(map, base64Security);
    //生成JWT token
    return builder.compact();
  }

  /**
   * 前三个参数为自己用户token的一些信息比如id，权限，名称等。不要将隐私信息放入（大家都可以获取到）
   *
   * @param map            参数
   * @param base64Security 密钥
   * @param expiration     过期时间
   * @return 返回结果
   */
  public static String createJwt(Map<String, Object> map, String base64Security, long expiration) {
    //添加构成JWT的参数
    JwtBuilder builder = builderJwt(map, base64Security);
    builder.setExpiration(new Date(expiration));
    //生成JWT
    return builder.compact();
  }

  /**
   * 构建 jwt
   *
   * @param map            值
   * @param base64Security 密钥
   * @return 返回结果
   */
  private static JwtBuilder builderJwt(Map<String, Object> map, String base64Security) {
    return Jwts.builder().setHeaderParam("typ", "JWT")
        .setClaims(map)
        //估计是第三段密钥
        .signWith(signatureAlgorithm, base64Security.getBytes());
  }

  /**
   * 检查token
   *
   * @param jwtToken       token
   * @param base64Security base64安全性
   * @param userAgent      用户代理
   * @return 返回 redis token
   * @throws Exception 异常信息
   */
  public static String checkToken(String jwtToken, String base64Security, String userAgent) throws GlobalException {
    String token;
    try {
      Claims claims = parseJwt(jwtToken, base64Security);
      long effective = IntegerConsts.TWENTY_FOUR * IntegerConsts.SIXTY * IntegerConsts.SIXTY;
      Long time = ConvertUtils.convertLong(claims.get(BusinessConsts.EXP));
      // 验证 token 是否过期
      LocalDateTime localDateTime = LocalDateUtils.timestampToLocalDateTime(time)
          .plusSeconds(effective);
      if (LocalDateUtils.greater(localDateTime, LocalDateTime.now())) {
        throw new GlobalException("token 过期！");
      }

      // 验证 token 签名
      Object loginName = claims.get(BusinessConsts.USER_NAME);
      if (StringUtils.isNullAndSpaceOrEmpty(loginName)) {
        loginName = claims.get(BusinessConsts.LOGIN_NAME);
      }
      StringBuilder builder = new StringBuilder();
      builder.append(String.format("loginName=%s&effective=%s&time=%s&userAgent=%s", loginName, effective, time, userAgent));
      token = String.valueOf(claims.get(BusinessConsts.HEADER_TOKEN));
      String tokenPassword = Md5Utils.getMd5(builder.toString());
      if (!tokenPassword.equals(token)) {
        throw new GlobalException("token 签名验证失败！");
      }
    } catch (Exception ex) {
      String message = "token 验证失败！";
      if (ex instanceof GlobalException) {
        message = ex.getMessage();
      }
      throw new GlobalException(message);
    }

    // 返回结果
    return token;
  }

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<String, Object>(61);
    map.put("token", "13e3fa74-ba00-4609-98d3-7d7215020300");

    //密钥
    String key = "ht-oms-project-jwt";
    String token = JwtUtils.createJwt(map, key, System.currentTimeMillis() + 10000);

    try {
      JwtUtils.checkToken(token, key, "");
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("JWT加密的结果：" + token);
    System.out.println("JWT解密的结果：" + parseJwt(token, key));
  }
}