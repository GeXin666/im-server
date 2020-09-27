package com.dxxt.im.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dxxt.im.config.AppConfig;

public class JwtTokenUtil {

    public static String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(AppConfig.jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userId").asString();
    }
}

