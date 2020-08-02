package com.jeramtough.randl2.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.jeramtough.jtcomponent.task.bean.PreTaskResult;
import com.jeramtough.jtcomponent.task.response.ResponseFactory;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtcomponent.task.runner.SimpleRunner;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.config.security.AuthTokenConfig;

import java.util.Date;

/**
 * <pre>
 * Created on 2020/3/23 0:03
 * by @author JeramTough
 * </pre>
 */
public class JwtTokenUtil {

    public static String createToken(SystemUser systemUser, AuthTokenConfig authTokenConfig) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(authTokenConfig.getSigningKey());

            String roleName = systemUser.getRole().getName();

            token = JWT.create()
                       .withIssuer(AuthTokenConfig.ISSUER)
                       .withClaim("uid", systemUser.getUid())
                       .withClaim("role", roleName)
                       .withExpiresAt(new Date(
                               System.currentTimeMillis() + authTokenConfig.getJwtTokenValidity()))
                       .sign(algorithm);
        }
        catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return token;
    }

    public static TaskResponse verifyToken(String token, AuthTokenConfig authTokenConfig) {
        return ResponseFactory.doing(preTaskResult -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256(authTokenConfig.getSigningKey());
                JWTVerifier verifier = JWT.require(algorithm)
                                          .withIssuer(AuthTokenConfig.ISSUER)
                                          .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(token);
                Long uid = jwt.getClaim("uid").asLong();
                if (uid != null) {
                    preTaskResult.putPayload("uid", uid);
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
