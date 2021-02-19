package com.jeramtough.randl2.common.util;

import com.jeramtough.jtcomponent.utils.Base64Util;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 * Created on 2021/2/16 1:52
 * by @author WeiBoWen
 * </pre>
 */
public class OauthUtil {

    public synchronized static String createClientSecret(PasswordEncoder passwordEncoder, String clientId) {
        String clientSecret = System.currentTimeMillis() + clientId;

        clientSecret = Base64Util.toBase64Str(clientSecret);

        char[] a = clientSecret.toCharArray();
        List<Character> l = new LinkedList<>();
        for (char c : a) {
            l.add(c);
        }
        Collections.shuffle(l);
        Iterator<Character> i = l.iterator();

        clientSecret = "";
        while (i.hasNext()) {
            clientSecret = clientSecret + i.next();
        }

        clientSecret = passwordEncoder.encode(clientSecret);
        return clientSecret;
    }

}
