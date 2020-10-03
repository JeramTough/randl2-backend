package com.jeramtough.randl2.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtcomponent.task.response.TaskResponse;

import java.util.Date;

/**
 * <pre>
 * Created on 2020/3/23 0:03
 * by @author JeramTough
 * </pre>
 */
public class JwtTokenUtil {

    public static String createToken(String userId, String roleName, String signingKey, String issuer,
                                     long tokenValidity) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(signingKey);


            token = JWT.create()
                       .withIssuer(issuer)
                       .withClaim("uid", userId)
                       .withClaim("role", roleName)
                       .withExpiresAt(new Date(
                               System.currentTimeMillis() + tokenValidity))
                       .sign(algorithm);
        }
        catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return token;
    }

    public static TaskResponse verifyToken(String token, String signingKey, String issuer) {
        return ResponseFactory.doing(preTaskResult -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256(signingKey);
                JWTVerifier verifier = JWT.require(algorithm)
                                          .withIssuer(issuer)
                                          .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(token);
                String uid = jwt.getClaim("uid").asString();
                String roleId = jwt.getClaim("roleId").asString();
                if (uid != null) {
                    preTaskResult.putPayload("uid", uid);
                    preTaskResult.putPayload("roleId", roleId);
                    return true;
                }
            }
            catch (JWTVerificationException exception) {
                preTaskResult.setMessage(exception.getMessage());
                return false;
            }
            return false;
        });
    }

}
