package com.jeramtough.randl2.service.randl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.component.userdetail.MyUserFactory;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import com.jeramtough.randl2.service.randl.RandlUserService;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.RandlUserRoleMapMapper;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.entity.RandlUserRoleMap;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.user.ConditionUserParams;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import com.jeramtough.randl2.common.model.params.user.UpdateRandlUserParams;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final RandlRoleService randlRoleService;

    public RandlUserServiceImpl(WebApplicationContext wc,
                                AppSetting appSetting,
                                RandlRoleMapper roleMapper,
                                RandlUserRoleMapMapper userRoleMapMapper,
                                MyUserFactory myUserFactory,
                                PasswordEncoder passwordEncoder,
                                RandlRoleService randlRoleService) {
        super(wc);
        this.appSetting = appSetting;
        this.roleMapper = roleMapper;
        this.userRoleMapMapper = userRoleMapMapper;
        this.myUserFactory = myUserFactory;
        this.passwordEncoder = passwordEncoder;
        this.randlRoleService = randlRoleService;
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
        //2020.11.06想想还是在代码层面增加固定角色信息好了
       /* RandlUserRoleMap randlUserRoleMap = new RandlUserRoleMap();
        randlUserRoleMap.setUid(randlUser.getUid());
        randlUserRoleMap.setRoleId(appSetting.getUserDefaultRoleId());
        userRoleMapMapper.insert(randlUserRoleMap);*/

        return toDto(randlUser);
    }


    @Override
    public String removeRandUserById(Long uid) {
        //移除角色信息和用户一起
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

        PageDto<RandlUserDto> pageDto = toPageDto(randlUserQueryPage);

        //是否要查询用户角色列表
        if (params.getHasRoleIds() != null && params.getHasRoleIds()) {
            List<RandlUserDto> randlUserDtoList = pageDto.getList();

            //查询角色范围，主要是在该app下的角色
            QueryWrapper<RandlRole> roleQueryWrapper = new QueryWrapper<>();
            if (params.getAppId() != null) {
                roleQueryWrapper.eq("app_id", params.getAppId());
            }
            List<RandlRole> randlRoleList = roleMapper.selectList(roleQueryWrapper);
            //这个应用下所有的角色Id
            List<Long> roleIdList = randlRoleList.parallelStream().map(RandlRole::getFid).collect(
                    Collectors.toList());

            //遍历每一个用户，查询他们的角色信息
            randlUserDtoList.parallelStream().forEach(randlUserDto -> {
                QueryWrapper<RandlUserRoleMap> queryWrapper = new QueryWrapper<>();
                List<Long> roleIds;
                if (roleIdList.size() > 0) {
                    queryWrapper.in("role_id", roleIdList);
                    queryWrapper.eq("uid", randlUserDto.getUid());
                    List<RandlUserRoleMap> randlUserRoleMapList = userRoleMapMapper.selectList(queryWrapper);
                    roleIds = randlUserRoleMapList.parallelStream().map(
                            RandlUserRoleMap::getRoleId).collect(
                            Collectors.toList());
                }
                else {
                    roleIds = new ArrayList<>();
                }

                //每个用户都有Rand客户端角色
                roleIds.add(appSetting.getDefaultUserRoleId());

                randlUserDto.setRoleIds(roleIds);
            });
        }

        return pageDto;
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
                throw new ApiResponseException(ErrorU.CODE_11.C, "帐号名");
            }
        }


        if (currentRandlUser.getPhoneNumber() != null) {
            if (!currentRandlUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<RandlUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(ErrorU.CODE_11.C, "手机号码");
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
                    throw new ApiResponseException(ErrorU.CODE_11.C, "手机邮箱");
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

    @Override
    public RandlUserDto getWithRoleByCredentialsAndAppId(String credentials, Long appId) {
        RandlUser randlUser = getBaseMapper().selectByCredentials(credentials);
        RandlUserDto dto = toDto(randlUser);
        List<RandlRole> roleList = randlRoleService.getRoleListByAppIdAndUid(appId, randlUser.getUid());
        dto.setRoles(roleList.parallelStream()
                             .map(randlRole -> getMapperFacade().map(randlRole, RandlRoleDto.class))
                             .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public RandlUser getByCredentials(String credentials) {
        RandlUser randlUser = getBaseMapper().selectByCredentials(credentials);
        return randlUser;
    }
}
