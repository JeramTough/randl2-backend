package com.jeramtough.ssoserver.component.attestation.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * <pre>
 * Created on 2020/11/20 0:02
 * by @author WeiBoWen
 * </pre>
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String jwtToken;

    public JwtAuthenticationToken(String jwtToken) {
        this(null, jwtToken);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     * @param jwtToken
     */
    public JwtAuthenticationToken(
            Collection<? extends GrantedAuthority> authorities, String jwtToken) {
        super(authorities);
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getCredentials() {
        //没有密码
        return "";
    }

    @Override
    public Object getPrincipal() {
        return new JwtPrincipal(jwtToken);
    }

    public static class JwtPrincipal implements Principal, Serializable {

        private static final long serialVersionUID = 1341724760719310341L;

        private final String jwtToken;

        public JwtPrincipal(String jwtToken) {
            this.jwtToken = jwtToken;
        }

        @Override
        public String getName() {
            return jwtToken;
        }
    }
}
