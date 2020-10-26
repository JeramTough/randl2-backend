package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.RandlUserRoleMapMapper;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.entity.RandlUserRoleMap;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.user.ConditionUserParams;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.service.RandlUserService;
import com.jeramtough.randl2.common.model.params.user.UpdateRandlUserParams;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import com.jeramtough.randl2.adminapp.component.userdetail.MyUserFactory;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Service
public class RandlUserServiceImpl extends BaseDtoServiceImpl<RandlUserMapper, RandlUser, RandlUserDto>
        implements RandlUserService {

    private final AppSetting appSetting;
    private final RandlRoleMapper roleMapper;
    private final RandlUserRoleMapMapper userRoleMapMapper;
    private final MyUserFactory myUserFactory;
    private final PasswordEncoder passwordEncoder;

    public RandlUserServiceImpl(WebApplicationContext wc,
                                AppSetting appSetting,
                                RandlRoleMapper roleMapper,
                                RandlUserRoleMapMapper userRoleMapMapper,
                                MyUserFactory myUserFactory,
                                PasswordEncoder passwordEncoder) {
        super(wc);
        this.appSetting = appSetting;
        this.roleMapper = roleMapper;
        this.userRoleMapMapper = userRoleMapMapper;
        this.myUserFactory = myUserFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected RandlUserDto toDto(RandlUser randlUser) {
        return toDto1(randlUser, RandlUserDto.class);
    }

    @Override
    public RandlUserDto add(RegisterRandlUserParams params) {
        BeanValidator.verifyParams(params);

        if (!params.getPassword().equals(
                params.getRepeatedPassword())) {
            //重复密码不一致
            throw new ApiResponseException(ErrorU.CODE_101.C);
        }

        if (getBaseMapper().selectOne(new QueryWrapper<RandlUser>().eq("account",
                params.getAccount())) != null) {
            //存在同名账号
            throw new ApiResponseException(ErrorU.CODE_11.C, "帐号名");
        }

        if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                new QueryWrapper<RandlUser>().eq("phone_number",
                        params.getPhoneNumber())) > 0)) {
            //存在重复手机号码
            throw new ApiResponseException(ErrorU.CODE_11.C, "手机号码");
        }

        if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                new QueryWrapper<RandlUser>().eq(
                        "email_address",
                        params.getEmailAddress())) > 0)) {
            //存在重复邮箱地址
            throw new ApiResponseException(ErrorU.CODE_11.C, "手机号码");
        }

        //生成RandlUser对象
        RandlUser randlUser = myUserFactory.getAdminUser(params);
        getBaseMapper().insert(randlUser);

        //注册的用户必定有Randl用户中心APP的角色
        RandlUserRoleMap randlUserRoleMap = new RandlUserRoleMap();
        randlUserRoleMap.setIsAble(1);
        randlUserRoleMap.setUid(randlUser.getUid());
        randlUserRoleMap.setRoleId(appSetting.getUserDefaultRoleId());
        userRoleMapMapper.insert(randlUserRoleMap);

        return toDto(randlUser);
    }


    @Override
    public String removeRandUserById(Long uid) {
        userRoleMapMapper.delete(new QueryWrapper<RandlUserRoleMap>().eq("uid", uid));
        getBaseMapper().deleteById(uid);
        return "删除成功!";
    }

    @Override
    public PageDto<RandlUserDto> pageByCondition(
            QueryByPageParams queryByPageParams, ConditionUserParams params) {
        BeanValidator.verifyParams(params);

        QueryPage<RandlUser> randlUserQueryPage = getBaseMapper().selectByCondition(
                new QueryPage<>(queryByPageParams), params);

        return toPageDto(randlUserQueryPage);
    }

    @Override
    public String updateRandlUser(UpdateRandlUserParams params) {
        BeanValidator.verifyParams(params);

        RandlUser currentRandlUser = getById(params.getUid());

        if (currentRandlUser == null) {
            throw new ApiResponseException(ErrorU.CODE_12.C);
        }

        if (!currentRandlUser.getAccount().equals(params.getAccount())) {
            if (getBaseMapper().selectOne(new QueryWrapper<RandlUser>().eq("account",
                    params.getAccount())) != null) {
                //存在同名用户
                throw new ApiResponseException(ErrorU.CODE_11.C,"帐号名");
            }
        }


        if (currentRandlUser.getPhoneNumber() != null) {
            if (!currentRandlUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RandlUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(ErrorU.CODE_11.C,"手机号码");
                }
            }
        }

        if (currentRandlUser.getEmailAddress() != null) {
            if (!currentRandlUser.getEmailAddress().equals(params.getEmailAddress())) {
                if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RandlUser>().eq(
                                "email_address",
                                params.getEmailAddress())) > 0)) {
                    //存在重复邮箱地址
                    throw new ApiResponseException(ErrorU.CODE_11.C,"手机邮箱");
                }
            }
        }

        RandlUser randlUser = getMapperFacade().map(params, RandlUser.class);
        if (params.getPassword() != null) {
            randlUser.setPassword(passwordEncoder.encode(params.getPassword()));
        }

        updateById(randlUser);
        return "更新用户信息成功！";
    }
}
