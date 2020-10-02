package com.jeramtough.randl2.userapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.structure.TreeStructure;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.model.QueryPage;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.userapp.service.SystemMenuService;
import com.jeramtough.randl2.common.component.userdetail.MyUserFactory;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.UserHolder;
import com.jeramtough.randl2.common.component.userdetail.login.AdminUserLoginer;
import com.jeramtough.randl2.common.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.common.mapper.AdminUserMapper;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import com.jeramtough.randl2.common.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.AdminUserDto;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.entity.AdminUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.common.model.params.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.common.model.params.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.common.model.params.adminuser.UpdateCurrentAdminUserParams;
import com.jeramtough.randl2.userapp.service.AdminUserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

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
    private final SystemMenuService systemMenuService;

    @Autowired
    public AdminUserServiceImpl(WebApplicationContext wc,
                                MyUserFactory myUserFactory,
                                MapperFacade mapperFacade,
                                RoleMapper roleMapper,
                                SurfaceImageMapper surfaceImageMapper,
                                PasswordEncoder passwordEncoder,
                                SystemMenuService systemMenuService) {
        super(wc, mapperFacade);
        this.myUserFactory = myUserFactory;
        this.mapperFacade = mapperFacade;
        this.roleMapper = roleMapper;
        this.surfaceImageMapper = surfaceImageMapper;
        this.passwordEncoder = passwordEncoder;
        this.systemMenuService = systemMenuService;
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
            throw new ApiResponseException(ErrorU.CODE_301.C);
        }

        UserHolder.afterLogin(systemUser);

        //processing SystemUserDto
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        String surfaceImage = surfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        systemUserDto.setMenuTree(systemMenuService.getCurrentAdminUserSystemMenuTreeMap());

        return systemUserDto;
    }


    @Override
    public String adminLogout() {
        UserHolder.clear();
        return "管理员退出登录成功！";
    }

    @Override
    public String addAdminUser(RegisterAdminUserParams params) {
        BeanValidator.verifyDto(params);
        if (!params.getPassword().equals(
                params.getRepeatedPassword())) {
            //重复密码不一致
            throw new ApiResponseException(ErrorU.CODE_101.C);
        }

        if (getBaseMapper().selectOne(new QueryWrapper<AdminUser>().eq("username",
                params.getUsername())) != null) {
            //存在同名用户
            throw new ApiResponseException(ErrorU.CODE_9.C, "用户名");
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
            throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
        }

        AdminUser adminUser = myUserFactory.getAdminUser(params);
        save(adminUser);
        return "添加管理员用户【" + adminUser.getUsername() + "】成功!";
    }

    @Override
    public String removeAdminUser(Long uid) {
        boolean isOk = removeById(uid);
        if (!isOk) {
            throw new ApiResponseException(1020);
        }
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


        if (currentAdminUser.getPhoneNumber() != null) {
            if (!currentAdminUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<AdminUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(1037);
                }
            }
        }

        if (currentAdminUser.getEmailAddress() != null) {
            if (!currentAdminUser.getEmailAddress().equals(params.getEmailAddress())) {
                if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<AdminUser>().eq(
                                "email_address",
                                params.getEmailAddress())) > 0)) {
                    //存在重复邮箱地址
                    throw new ApiResponseException(1038);
                }
            }
        }

        if (!currentAdminUser.getRoleId().equals(params.getRoleId())) {
            if (roleMapper.selectById(params.getRoleId()) == null) {
                //该角色不存在
                throw new ApiResponseException(1039);
            }
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
            throw new ApiResponseException(ErrorU.CODE_7.C, "管理员用户");
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
            throw new ApiResponseException(ErrorU.CODE_8.C, "管理员用户");
        }
        return getBaseDto(adminUser);
    }

    @Override
    public String updateCurrentAdminUser(UpdateCurrentAdminUserParams params) {
        BeanValidator.verifyDto(params);

        if (UserHolder.isSuperAdmin()) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "管理员用户");
        }

        AdminUser currentAdminUser = getMapperFacade().map(UserHolder.getSystemUser(),
                AdminUser.class);

        if (currentAdminUser.getPhoneNumber() != null) {
            if (!currentAdminUser.getPhoneNumber().equals(params.getPhoneNumber())) {
                if (params.getPhoneNumber() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<AdminUser>().eq("phone_number",
                                params.getPhoneNumber())) > 0)) {
                    //存在重复手机号码
                    throw new ApiResponseException(1037);
                }
            }
        }

        if (currentAdminUser.getEmailAddress() != null) {
            if (!currentAdminUser.getEmailAddress().equals(params.getEmailAddress())) {
                if (params.getEmailAddress() != null && (getBaseMapper().selectCount(
                        new QueryWrapper<AdminUser>().eq(
                                "email_address",
                                params.getEmailAddress())) > 0)) {
                    //存在重复邮箱地址
                    throw new ApiResponseException(1038);
                }
            }
        }

        AdminUser adminUser = mapperFacade.map(params, AdminUser.class);
        adminUser.setUid(currentAdminUser.getUid());
        if (params.getPassword() != null) {
            adminUser.setPassword(passwordEncoder.encode(params.getPassword()));
        }
        updateById(adminUser);

        UserHolder.update(params.getPhoneNumber(), params.getEmailAddress());

        return "您的账户信息成功！";
    }


    //********************
}
