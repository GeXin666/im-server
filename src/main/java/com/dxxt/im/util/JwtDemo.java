package com.dxxt.im.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtDemo {

    public static void main(String[] main) {
        String token = null;
        try {
            //创建加密算法
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create()
                    //签发者
                    .withIssuer("auth0")
                    //自定义KV
                    .withClaim("userId", "11111111000000")
                    .sign(algorithm);
            System.out.println(token);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsInVzZXJJZCI6IjExMTExMTExMDAwMDAwIn0.1PV7z-bX1JN_cqUospbmQ8IWjvsqQqEn9DaOVrrTfik";
        try {
            //创建加密算法
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)

                    //可以强制判断token当中是否包含此字段
//                    .withIssuer("auth0")
//                    .withClaim("admin", "jack")

                    //单位秒: 可以接受过期的时间长度,
                    //比如过期时间为15:30:00,可以往后延45秒，那么过期时间为15:30:45
                    .acceptExpiresAt(45)

                    //单位秒：可以接受提前使用的时间长度，
                    //比如NotBefore定以为15:30:00，那么在到时间之前正常token都不可用
                    //设置为60，代表提前一分钟可以用  那么token在15:29:00就可以用了
                    .acceptNotBefore(60)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getClaim("admin").asString());
            System.out.println(jwt.getExpiresAt());
            System.out.println(jwt.getIssuedAt());
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }
    }
}
