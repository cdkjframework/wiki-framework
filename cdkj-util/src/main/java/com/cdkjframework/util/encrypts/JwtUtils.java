package com.cdkjframework.util.encrypts;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import com.google.gson.Gson;
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
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setPayload(new Gson().toJson(map))
                //估计是第三段密钥
                .signWith(signatureAlgorithm, base64Security.getBytes());
        //生成JWT
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
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setExpiration(new Date(expiration))
                //估计是第三段密钥
                .signWith(signatureAlgorithm, base64Security.getBytes());
        //生成JWT
        return builder.compact();
    }


    /**
     * 检查token
     *
     * @param jwtToken       token
     * @param base64Security base64安全性
     * @return 返回 redis token
     * @throws Exception 异常信息
     */
    public static String checkToken(String jwtToken, String base64Security) throws Exception {
        String token;
        try {
            Claims claims = parseJwt(jwtToken, base64Security);
            Long effective = ConvertUtils.convertLong(claims.get("effective"));
            Long time = ConvertUtils.convertLong(claims.get("time"));
            // 验证 token 是否过期
            LocalDateTime localDateTime = LocalDateUtils.timestampToLocalDateTime(time)
                    .plusSeconds(effective);
            if (LocalDateUtils.greater(LocalDateTime.now(), localDateTime)) {
                throw new GlobalException("token 过期！");
            }

            // 验证 token 签名
            String loginName = String.valueOf(claims.get("loginName"));
            String userAgent = HttpServletUtils.getRequest().getHeader(com.cdkjframework.consts.HttpHeaderConsts.USER_AGENT);
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("loginName=%s&effective=%s&time=%s&userAgent=%s", loginName, effective, time, userAgent));
            token = String.valueOf(claims.get("token"));
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
        String token = JwtUtils.createJwt(map, key, System.currentTimeMillis());

        try {
            JwtUtils.checkToken(token, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("JWT加密的结果：" + token);
        System.out.println("JWT解密的结果：" + parseJwt(token, key));
    }
}