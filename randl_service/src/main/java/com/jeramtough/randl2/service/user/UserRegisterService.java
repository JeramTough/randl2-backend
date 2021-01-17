package com.jeramtough.randl2.service.user;

import com.jeramtough.randl2.common.model.params.registereduser.DoRegisterOrResetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailByForgetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailForNewParams;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface UserRegisterService {

    Map<String, Object> verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params);

    Map<String, Object> verifyPhoneOrEmailByForget(VerifyPhoneOrEmailByForgetParams params);

    String verifyPassword(VerifyPasswordParams params);

    Object verifyPasswordByForget(VerifyPasswordParams params);

    Object register(DoRegisterOrResetParams params);

    Object reset(DoRegisterOrResetParams params);

}
