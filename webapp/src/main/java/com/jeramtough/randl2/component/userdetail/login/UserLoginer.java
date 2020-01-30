package com.jeramtough.randl2.component.userdetail.login;

import com.jeramtough.randl2.component.userdetail.SystemUser;

/**
 * <pre>
 * Created on 2020/1/30 10:28
 * by @author JeramTough
 * </pre>
 */
public interface UserLoginer {

    SystemUser login(Object credentials);
}
