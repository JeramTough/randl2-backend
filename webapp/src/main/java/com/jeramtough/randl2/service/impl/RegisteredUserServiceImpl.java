package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.component.registereduser.builder.RegisteredUserBuilder;
import com.jeramtough.randl2.component.registereduser.builder.RegisteredUserBuilderGetter;
import com.jeramtough.randl2.component.verificationcode.VerificationCodeHolder;
import com.jeramtough.randl2.dao.entity.Api;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import com.jeramtough.randl2.service.RegisteredUserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class RegisteredUserServiceImpl extends BaseServiceImpl<RegisteredUserMapper,
        RegisteredUser, RegisteredUserDto> implements
        RegisteredUserService {

    private RegisteredUserBuilderGetter registeredUserPlantGetter;
    private VerificationCodeHolder verificationCodeHolder;

    @Autowired
    public RegisteredUserServiceImpl(WebApplicationContext wc,
                                     MapperFacade mapperFacade,
                                     RegisteredUserBuilderGetter registeredUserPlantGetter,
                                     VerificationCodeHolder verificationCodeHolder) {
        super(wc, mapperFacade);
        this.registeredUserPlantGetter = registeredUserPlantGetter;
        this.verificationCodeHolder = verificationCodeHolder;
    }

    @Override
    protected RegisteredUserDto toDto(RegisteredUser registeredUser) {
        RegisteredUserDto registeredUserDto = getMapperFacade().map(registeredUser,
                RegisteredUserDto.class);
        return registeredUserDto;
    }

    @Override
    public String verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params) {
        BeanValidator.verifyDto(params);

        //初始化注册方式
        registeredUserPlantGetter.initRegisterUserWay(params.getWay(), 7005);

        RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        builder.setAccount(params.getPhoneOrEmail(), 7001, 7002, 7003, 7004);
        return "该账号可以注册";
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyDto(params);
        RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        builder.setPassword(params.getPassword(), params.getRepeatedPassword(), 7020);
        return "密码校验通过";
    }

    @Override
    public RegisteredUserDto register() {
        RegisteredUserBuilder builder = registeredUserPlantGetter.getRegisteredUserBuilder(
                7000);
        RegisteredUser registeredUser = builder.build(7030);

        if (!verificationCodeHolder.getVerificationResult().isPassed()) {
            throw new ApiResponseException(7031);
        }

        boolean isTheSamePhoneNumber =
                verificationCodeHolder.getVerificationResult().getSendWayValue().equals(
                        registeredUser.getPhoneNumber());
        boolean isTheSameEmailAddress =
                verificationCodeHolder.getVerificationResult().getSendWayValue().equals(
                        registeredUser.getEmailAddress());
        if (!(isTheSamePhoneNumber || isTheSameEmailAddress)) {
            throw new ApiResponseException(7032);
        }

        getBaseMapper().insert(registeredUser);
        builder.resetRegisteredUser();
        return getBaseDto(registeredUser);
    }


    //****************************

}
