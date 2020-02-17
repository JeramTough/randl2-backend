package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.component.registereduser.RegisterUserWay;
import com.jeramtough.randl2.component.registereduser.RegisteredUserPlant;
import com.jeramtough.randl2.component.registereduser.RegisteredUserPlantGetter;
import com.jeramtough.randl2.component.registereduser.builder.RegisteredUserBuilder;
import com.jeramtough.randl2.component.registereduser.builder.SessionRegisteredUserBuilder;
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

    @Autowired
    public RegisteredUserServiceImpl(WebApplicationContext wc, MapperFacade mapperFacade) {
        super(wc, mapperFacade);
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
        RegisteredUserBuilder registeredUserBuilder = getWC().getBean(
                SessionRegisteredUserBuilder.class);

        return "该账号可以注册";
    }

    @Override
    public String verifyPassword(VerifyPasswordParams params) {
        BeanValidator.verifyDto(params);
        return "密码校验通过";
    }


    //****************************

}
