package com.jeramtough.randl2.component.registereduser.builder;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/16 18:42
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseRegisteredUserBuilder implements RegisteredUserBuilder {

    private final static String REGISTERED_USER_KEY = BaseRegisteredUserBuilder.class.getSimpleName() +
            "registeredUser";

    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;
    private final RegisteredUserMapper registeredUserMapper;

    public BaseRegisteredUserBuilder(HttpSession session,
                                     PasswordEncoder passwordEncoder,
                                     RegisteredUserMapper registeredUserMapper) {
        this.session = session;
        this.passwordEncoder = passwordEncoder;
        this.registeredUserMapper = registeredUserMapper;
    }


    @Override
    public void rebuildRegisteredUser(@NotNull RegisteredUser registeredUser) {
        setRegisteredUser(registeredUser);
    }

    @Override
    public RegisteredUser resetRegisteredUser(int... errorCodes) {
        if (getRegisteredUser().getPassword() == null) {
            throw new ApiResponseException(errorCodes[0]);
        }
        return getRegisteredUser();
    }

    @Override
    public void setPassword(String password, String repeatedPassword,
                            int... errorCodes) throws ApiResponseException {
        if (!password.equals(repeatedPassword)) {
            throw new ApiResponseException(errorCodes[0]);
        }
        RegisteredUser registeredUser = getRegisteredUser();
        registeredUser.setPassword(passwordEncoder.encode(password));
        setRegisteredUser(registeredUser);
    }

    @Override
    public void clear() {
        setRegisteredUser(null);
    }

    RegisteredUser getRegisteredUser() {
        Object o = session.getAttribute(REGISTERED_USER_KEY);
        RegisteredUser registeredUser;
        if (o == null) {
            registeredUser = new RegisteredUser();
            session.setAttribute(REGISTERED_USER_KEY, registeredUser);
        }
        else {
            registeredUser = (RegisteredUser) o;
        }
        return registeredUser;
    }

    void setRegisteredUser(RegisteredUser registeredUser) {
        session.setAttribute(REGISTERED_USER_KEY, registeredUser);
    }

    public RegisteredUserMapper getRegisteredUserMapper() {
        return registeredUserMapper;
    }
}
