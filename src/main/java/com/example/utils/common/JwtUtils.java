package com.example.utils.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author lusir
 * @date 2021/10/3 - 19:42
 **/
public class JwtUtils {

//    拿出其中载荷的信息
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Constant.TOKEN_CLAIM).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

//    对头部和载荷进行签名返回jwt
    public  static String sign(String userId,String secret){
        Date date=new  Date(System.currentTimeMillis()+Constant.TOKEN_EXPIRE_TIME);
        Algorithm algorithm=Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(Constant.USER_ID,userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

//    验证token
public static boolean verify(String token, String secret) {
    try {
        //根据密码生成JWT效验器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim(Constant.TOKEN_CLAIM, getUserId(token))
                .build();
        // 效验TOKEN
        verifier.verify(token);
        return true;
    } catch (JWTVerificationException exception) {
        return false;
    }
}
}
