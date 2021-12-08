package com.jeramtough.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * <pre>
 * Created on 2020/1/27 19:36
 * by @author JeramTough
 * </pre>
 */
public class PasswordTest {

    @Test
    public void test1() {
        Algorithm algorithm = Algorithm.HMAC256("a");
        String token = JWT.create()
                          .withIssuer("JeramTough")
                          .withClaim("uid", 2123)
                          .withExpiresAt(new Date(System.currentTimeMillis()+2000))
                          .sign(algorithm);
        L.debug(token);

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                                      .withIssuer("JeramTough")
                                      .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            L.debug(jwt.getClaim("uid").asLong());
        }
        catch (JWTVerificationException exception) {
            L.debug(exception.getMessage());
        }
    }

    @Test
    public void test2(){
        L.debug(IdUtil.getUUID());
    }
}
