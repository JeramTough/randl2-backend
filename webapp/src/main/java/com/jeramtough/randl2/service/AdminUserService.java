package com.jeramtough.randl2.service;

import com.jeramtough.jtcomponent.task.response.ReturnResponse;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.bean.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.bean.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.dao.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.dto.AdminUserDto;
import com.jeramtough.randl2.dto.SystemUserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface AdminUserService extends IService<AdminUser> {

    SystemUserDto adminLogin(AdminUserCredentials adminUserCredentials);

    String adminLogout();

    String addAdminUser(RegisterAdminUserParams registerAdminUserParams);

    String removeAdminUser(Long userId);

    List<AdminUserDto> getAllAdminUser();

    String updateAdminUser(UpdateAdminUserParams params);

    AdminUserDto getOneAdminUser(Long uid);
}
