package com.jeramtough.randl2.component.registereduser;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * <pre>
 * Created on 2020/2/16 18:42
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseRegisteredUserPlant implements RegisteredUserPlant {

    private final static String REGISTERED_USER_KEY = BaseRegisteredUserPlant.class.getName() +
            "registeredUser";

    private RegisteredUser registeredUser;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;
    private final RegisteredUserMapper registeredUserMapper;

    public BaseRegisteredUserPlant(HttpSession session,
                                   PasswordEncoder passwordEncoder,
                                   RegisteredUserMapper registeredUserMapper) {
        this.session = session;
        this.passwordEncoder = passwordEncoder;
        this.registeredUserMapper = registeredUserMapper;
        initResources();
    }

    protected void initResources() {
        Object o = session.getAttribute(REGISTERED_USER_KEY);
        if (o == null) {
            registeredUser = new RegisteredUser();
            session.setAttribute(REGISTERED_USER_KEY, registeredUser);
        }
        else {
            registeredUser = (RegisteredUser) o;
        }
    }

    @Override
    public void setPassword(String password, String repeatedPassword,
                            int... errorCodes) throws ApiResponseException {
        if (!password.equals(repeatedPassword)) {
            throw new ApiResponseException(errorCodes[0]);
        }
        registeredUser.setPassword(passwordEncoder.encode(password));
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public RegisteredUserMapper getRegisteredUserMapper() {
        return registeredUserMapper;
    }
}
