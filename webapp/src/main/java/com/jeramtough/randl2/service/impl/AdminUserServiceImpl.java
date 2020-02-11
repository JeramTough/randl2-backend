package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.bean.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.bean.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.component.db.QueryPage;
import com.jeramtough.randl2.component.userdetail.MyUserFactory;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.login.AdminUserLoginer;
import com.jeramtough.randl2.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.dao.entity.AdminUser;
import com.jeramtough.randl2.dao.mapper.AdminUserMapper;
import com.jeramtough.randl2.dao.mapper.RoleMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.dto.AdminUserDto;
import com.jeramtough.randl2.dto.PageDto;
import com.jeramtough.randl2.dto.SystemUserDto;
import com.jeramtough.randl2.service.AdminUserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class AdminUserServiceImpl
        extends BaseServiceImpl<AdminUserMapper, AdminUser, AdminUserDto>
        implements AdminUserService, WithLogger {

    private final MyUserFactory myUserFactory;
    private final MapperFacade mapperFacade;
    private final RoleMapper roleMapper;
    private final SurfaceImageMapper surfaceImageMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserServiceImpl(WebApplicationContext wc,
                                MyUserFactory myUserFactory,
                                MapperFacade mapperFacade,
                                RoleMapper roleMapper,
                                SurfaceImageMapper surfaceImageMapper,
                                PasswordEncoder passwordEncoder) {
        super(wc, mapperFacade);
        this.myUserFactory = myUserFactory;
        this.mapperFacade = mapperFacade;
        this.roleMapper = roleMapper;
        this.surfaceImageMapper = surfaceImageMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected AdminUserDto toDto(AdminUser adminUser) {
        AdminUserDto adminUserDto = mapperFacade.map(adminUser, AdminUserDto.class);
        adminUserDto.setRole(roleMapper.selectById(adminUser.getRoleId()));
        return adminUserDto;
    }

    @Override
    public SystemUserDto adminLogin(AdminUserCredentials adminUserCredentials) {
        BeanValidator.verifyDto(adminUserCredentials);

        UserLoginer userLoginer = super.getWC().getBean(AdminUserLoginer.class);
        SystemUser systemUser = userLoginer.login(adminUserCredentials);

        if (systemUser == null) {
            throw new ApiResponseException(1001);
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(
                new JaasGrantedAuthority("ROLE_" + systemUser.getRole().getName(),
                        systemUser));
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(systemUser.getUsername(),
                        systemUser.getPassword(), grantedAuthorityList);
        token.setDetails(systemUser);
        securityContext.setAuthentication(token);

        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        String surfaceImage = surfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        return systemUserDto;
    }


    @Override
    public String adminLogout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        return "管理员退出登录成功！";
    }

    @Override
    public String addAdminUser(RegisterAdminUserParams params) {
        BeanValidator.verifyDto(params);
        if (!params.getPassword().equals(
                params.getRepeatedPassword())) {
            //重复密码不一致
            throw new ApiResponseException(1011);
        }

        if (getBaseMapper().selectOne(new QueryWrapper<AdminUser>().eq("username",
                params.getUsername())) != null) {
            //存在同名用户
            throw new ApiResponseException(1013);
        }

        if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                new QueryWrapper<AdminUser>().eq("phone_number",
                        params.getPhoneNumber())) > 0)) {
            //存在重复手机号码
            throw new ApiResponseException(1017);
        }

        if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                new QueryWrapper<AdminUser>().eq(
                        "email_address",
                        params.getEmailAddress())) > 0)) {
            //存在重复邮箱地址
            throw new ApiResponseException(1018);
        }

        if (params.getRoleId() != null && roleMapper.selectById(params.getRoleId()) == null) {
            //该角色不存在
            throw new ApiResponseException(1019);
        }

        AdminUser adminUser = myUserFactory.getAdminUser(params);
        save(adminUser);
        return "添加管理员用户【" + adminUser.getUsername() + "】成功!";
    }

    @Override
    public String removeAdminUser(Long uid) {
       /* boolean isOk = removeById(uid);
        if (!isOk) {
            throw new ApiResponseException(1020);
        }*/
        return "移除管理员用户成功";
    }

    @Override
    public List<AdminUserDto> getAllAdminUser() {
        List<AdminUser> adminUserList = getBaseMapper().selectList(null);
        return getDtoList(adminUserList);
    }

    @Override
    public String updateAdminUser(UpdateAdminUserParams params) {
        BeanValidator.verifyDto(params);

        AdminUser currentAdminUser = getById(params.getUid());

        if (currentAdminUser == null) {
            throw new ApiResponseException(1031);
        }


        if (!currentAdminUser.getUsername().equals(params.getUsername())) {
            if (getBaseMapper().selectOne(new QueryWrapper<AdminUser>().eq("username",
                    params.getUsername())) != null) {
                //存在同名用户
                throw new ApiResponseException(1033);
            }
        }


        if (!currentAdminUser.getPhoneNumber().equals(params.getPhoneNumber())) {
            if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                    new QueryWrapper<AdminUser>().eq("phone_number",
                            params.getPhoneNumber())) > 0)) {
                //存在重复手机号码
                throw new ApiResponseException(1037);
            }
        }

        if (!currentAdminUser.getEmailAddress().equals(params.getEmailAddress())) {
            if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                    new QueryWrapper<AdminUser>().eq(
                            "email_address",
                            params.getEmailAddress())) > 0)) {
                //存在重复邮箱地址
                throw new ApiResponseException(1038);
            }
        }

        if (roleMapper.selectById(params.getRoleId()) == null) {
            //该角色不存在
            throw new ApiResponseException(1039);
        }

        AdminUser adminUser = mapperFacade.map(params, AdminUser.class);
        if (params.getPassword() != null) {
            adminUser.setPassword(passwordEncoder.encode(params.getPassword()));
        }

        updateById(adminUser);
        return "更新管理员用户信息成功！";
    }

    @Override
    public AdminUserDto getOneAdminUser(Long uid) {
        AdminUser adminUser = getById(uid);
        if (adminUser == null) {
            throw new ApiResponseException(1040);
        }
        AdminUserDto adminUserDto = mapperFacade.map(adminUser, AdminUserDto.class);
        adminUserDto.setRole(roleMapper.selectById(adminUser.getRoleId()));
        return adminUserDto;
    }

    @Override
    public PageDto<AdminUserDto> getAdminUserListByPage(QueryByPageParams queryByPageParams) {
        BeanValidator.verifyDto(queryByPageParams);
        QueryPage<AdminUser> queryPage = getBaseMapper().selectPage(
                new QueryPage<>(queryByPageParams), null);
        return getPageDto(queryPage);
    }

    @Override
    public AdminUserDto getAdminUserByKeyword(String keyword) {
        AdminUser adminUser = getBaseMapper().selectByKeyword(keyword);
        if (adminUser == null) {
            throw new ApiResponseException(1040);
        }
        return getBaseDto(adminUser);
    }


    //********************
}
