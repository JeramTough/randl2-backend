package com.jeramtough.randl2.common.component.attestation;

import java.io.Serializable;
import java.security.Principal;

/**
 * <pre>
 * Created on 2020/11/22 19:53
 * by @author WeiBoWen
 * </pre>
 */
public class SimplePrincipal implements Principal, Serializable {

    private static final long serialVersionUID = -7045345117465584121L;

    private String name;

    public SimplePrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }
}
