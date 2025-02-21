package org.com.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "CHENSHIWU";
    //接收业务数据，生成token
    public static String genToken(String account) {
        // 创建载荷（claims），将账号信息放入
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", account);

        // 生成 Token
        return JWT.create()
                .withClaim("claims", claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(Algorithm.HMAC256(KEY)); // 指定算法
    }

    //解析验证token并返回
    public static Map<String,Object> VerifyToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY)).build().verify(token).getClaim("claims").asMap();
    }
    public static String verifyToken(String token) {
        // 验证 Token 并解析
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token);

        // 从载荷中获取账号
        Map<String, Object> claims = jwt.getClaim("claims").asMap();
        return (String) claims.get("account");
    }
}
