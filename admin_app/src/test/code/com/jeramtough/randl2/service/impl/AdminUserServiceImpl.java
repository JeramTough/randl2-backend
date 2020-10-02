package com.jeramtough.randl2.userapp.service.impl;

import com.jeramtough.randl2.common.model.entity.AdminUser;
import com.jeramtough.randl2.common.mapper.AdminUserMapper;
import com.jeramtough.randl2.userapp.service.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

}
