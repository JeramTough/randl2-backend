package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.bean.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.bean.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.component.userdetail.MyUserFactory;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.login.AdminUserLoginer;
import com.jeramtough.randl2.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.dao.entity.AdminUser;
import com.jeramtough.randl2.dao.mapper.AdminUserMapper;
import com.jeramtough.randl2.dao.mapper.RoleMapper;
import com.jeramtough.randl2.dto.AdminUserDto;
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
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUserMapper, AdminUser>
        implements AdminUserService {

    private final MyUserFactory myUserFactory;
    private final MapperFacade mapperFacade;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserServiceImpl(WebApplicationContext wc,
                                MyUserFactory myUserFactory,
                                MapperFacade mapperFacade,
                                RoleMapper roleMapper,
                                PasswordEncoder passwordEncoder) {
        super(wc, mapperFacade);
        this.myUserFactory = myUserFactory;
        this.mapperFacade = mapperFacade;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String adminLogin(AdminUserCredentials adminUserCredentials) {

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
        securityContext.setAuthentication(
                new UsernamePasswordAuthenticationToken(systemUser.getUsername(),
                        systemUser.getPassword(), grantedAuthorityList));
        return "用户成功登录";
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

        AdminUser adminUser = myUserFactory.getAdminUser(params);
        save(adminUser);
        return "添加管理员用户【" + adminUser.getUsername() + "】成功!";
    }

    @Override
    public String removeAdminUser(Long userId) {
        boolean isOk = removeById(userId);
        if (!isOk) {
            throw new ApiResponseException(1020);
        }
        return "移除管理员用户成功";
    }

    @Override
    public List<AdminUserDto> getAllAdminUser() {
        List<AdminUserDto> adminUserDtoList = new ArrayList<>();
        List<AdminUser> adminUserList = getBaseMapper().selectList(null);
        for (AdminUser adminUser : adminUserList) {
            AdminUserDto adminUserDto = mapperFacade.map(adminUser, AdminUserDto.class);
            adminUserDto.setPassword("");
            adminUserDto.setRole(roleMapper.selectById(adminUser.getRoleId()));
            adminUserDtoList.add(adminUserDto);
        }
        return adminUserDtoList;
    }

    @Override
    public String updateAdminUser(UpdateAdminUserParams params) {
        BeanValidator.verifyDto(params);

        if (getById(params.getUid()) == null) {
            throw new ApiResponseException(1031);
        }

        if (getBaseMapper().selectOne(new QueryWrapper<AdminUser>().eq("username",
                params.getUsername())) != null) {
            //存在同名用户
            throw new ApiResponseException(1033);
        }

        if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                new QueryWrapper<AdminUser>().eq("phone_number",
                        params.getPhoneNumber())) > 0)) {
            //存在重复手机号码
            throw new ApiResponseException(1037);
        }

        if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                new QueryWrapper<AdminUser>().eq(
                        "email_address",
                        params.getEmailAddress())) > 0)) {
            //存在重复邮箱地址
            throw new ApiResponseException(1038);
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
        adminUserDto.setPassword("");
        adminUserDto.setRole(roleMapper.selectById(adminUser.getRoleId()));
        return adminUserDto;
    }

}
