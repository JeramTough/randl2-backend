package com.jeramtough.randl2.common.component.login;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.userdetail.AccountStatus;
import com.jeramtough.randl2.common.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.UserCredentials;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * Created on 2020/11/15 2:33
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final RandlUserMapper randlUserMapper;

    public BaseUserLoginer(PasswordEncoder passwordEncoder,
                           RandlUserMapper randlUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.randlUserMapper = randlUserMapper;
    }

    public RandlUser getRandlUserByAcOrPhOrEm(String acOrPhOrEm, String password) {

        //获取用户对象
        RandlUser randlUser = randlUserMapper.selectByCredentials(acOrPhOrEm);
        if (randlUser == null) {
            //登录失败，不存在该用户
            throw new ApiResponseException(ErrorU.CODE_302.C);
        }

        //判断当前用户的状态
        if (AccountStatus.toAccountStatus(randlUser.getAccountStatus()) != AccountStatus.ABLE) {
            throw new ApiResponseException(ErrorU.CODE_304.C);
        }

        //如果密码不正确
        boolean passwordIsRight =
                passwordEncoder.matches(password,
                        randlUser.getPassword());
        if (!passwordIsRight) {
            throw new ApiResponseException(ErrorU.CODE_301.C);
        }

        return randlUser;
    }
}
