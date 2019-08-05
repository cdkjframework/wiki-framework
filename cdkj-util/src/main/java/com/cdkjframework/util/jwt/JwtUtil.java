package com.cdkjframework.util.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

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
public class JwtUtil {
    /**
     * 解密
     *
     * @param jsonWebToken   token
     * @param base64Security 密钥
     * @return 返回结果
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
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
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setPayload(new Gson().toJson(map))
                //估计是第三段密钥
                .signWith(signatureAlgorithm, base64Security.getBytes());
        //生成JWT
        return builder.compact();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", "13e3fa74-ba00-4609-98d3-7d7215020300");


        //密钥
        String key = "ht-oms-project-jwt";
        String token = JwtUtil.createJwt(map, key);
        System.out.println("JWT加密的结果：" + token);
        System.out.println("JWT解密的结果：" + parseJWT(token, key));
    }
}