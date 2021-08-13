package com.jeramtough.randl2.component.login.user;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.AccountStatus;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
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
    private final MapperFacade mapperFacade;

    public BaseUserLoginer(PasswordEncoder passwordEncoder,
                           RandlUserMapper randlUserMapper, MapperFacade mapperFacade) {
        this.passwordEncoder = passwordEncoder;
        this.randlUserMapper = randlUserMapper;
        this.mapperFacade = mapperFacade;
    }

    public RandlUser getRandlUserByAcOrPhOrEm(String acOrPhOrEm, String password) {

        //获取用户对象
        RandlUser randlUser = randlUserMapper.selectByCredentials(acOrPhOrEm);

        checkRandlUser(randlUser);
        checkPassword(randlUser, password);

        return randlUser;
    }

    public void checkRandlUser(RandlUser randlUser) {
        if (randlUser == null) {
            //登录失败，不存在该用户
            throw new ApiResponseException(ErrorU.CODE_302.C);
        }

        //判断当前用户的状态
        if (AccountStatus.toAccountStatus(randlUser.getAccountStatus()) != AccountStatus.ABLE) {
            throw new ApiResponseException(ErrorU.CODE_304.C);
        }

    }

    public void checkPassword(RandlUser randlUser, String password) {
        if (randlUser == null) {
            //登录失败，不存在该用户
            throw new ApiResponseException(ErrorU.CODE_302.C);
        }


        //如果密码不正确
        boolean passwordIsRight =
                passwordEncoder.matches(password,
                        randlUser.getPassword());
        if (!passwordIsRight) {
            throw new ApiResponseException(ErrorU.CODE_301.C);
        }

    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public RandlUserMapper getRandlUserMapper() {
        return randlUserMapper;
    }


    public MapperFacade getMapperFacade() {
        return mapperFacade;
    }
}
