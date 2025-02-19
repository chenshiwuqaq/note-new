package org.ash.demos.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "CHENSHIWU";
    //接收业务数据，生成token
    public static String GenToken(Map<String,Object> claims){
        return JWT.create().withClaim("claims",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))//过期时间
                .sign(Algorithm.HMAC256(KEY));//指定算法
    }
    //解析验证token并返回
    public static Map<String,Object> VerifyToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY)).build().verify(token).getClaim("claims").asMap();
    }
}
