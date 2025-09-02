package org.com.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.com.Entity.CustomTokenException;

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
       try{ DecodedJWT jwt = JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token);

        // 从载荷中获取账号
        Map<String, Object> claims = jwt.getClaim("claims").asMap();
        return (String) claims.get("account");}
       catch (TokenExpiredException ex) {
           // 1. 专门处理Token过期情况
           System.err.println("Token expired at: " + ex.getExpiredOn());
           throw new CustomTokenException("TOKEN_EXPIRED", "Token已过期，请重新登录");
       } catch (JWTVerificationException ex) {
           // 2. 处理其他验证错误（无效签名、格式错误等）
           System.err.println("Token verification failed: " + ex.getMessage());
           throw new CustomTokenException("INVALID_TOKEN", "无效的Token");
       }
    }

}
