package com.codewen.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author codewen77
 * @date 2022-08-30
 */
public class JwtUtil {

    /**
     * 签名
     */
    private static String SECRET = "token!Q@W#E$R";

    /**
     * 生成token
     * 结构：header.payload.sign
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        Calendar instance = Calendar.getInstance();
        //指定令牌的过期时间 3分钟过期
        instance.add(Calendar.MINUTE,  3);
        builder.withExpiresAt(instance.getTime());
        //设置签发时间
        builder.withIssuedAt(new Date());
        //payload
        map.forEach(builder::withClaim);
        //sign签名
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
}
