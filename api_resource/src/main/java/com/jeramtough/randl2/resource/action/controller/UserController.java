package com.jeramtough.randl2.resource.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.resource.service.ResourceUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/11/14 23:11
 * by @author WeiBoWen
 * </pre>
 */
@Tag(name = "用户资源接口")
@RestController
@RequestMapping("/user")
public class UserController extends MyBaseController {

    private final ResourceUserService resourceUserService;

    @Autowired
    public UserController(
            ResourceUserService resourceUserService) {
        this.resourceUserService = resourceUserService;
    }

    @Operation(summary = "查询登录用户信息", description = "根据令牌查询登录用户信息")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    
    public CommonApiResponse<SystemUserDto> getRandlUserByToken() {
        return getSuccessfulApiResponse(resourceUserService.getRandlUserByToken());
    }

    @Operation(summary = "查询用户个人资料信息", description = "查询用户个人资料信息")
    @RequestMapping(value = "/randlPersonalInfo", method = {RequestMethod.GET})
    
    public CommonApiResponse<RandlPersonalInfoDto> getPersonalInfo() {
        return getSuccessfulApiResponse(resourceUserService.getPersonalInfoByToken());
    }

    @Operation(summary = "测试资源", description = "测试资源")
    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public CommonApiResponse<String> test(HttpServletRequest request) {
        return getSuccessfulApiResponse(resourceUserService.test());
    }

    @Operation(summary = "测试资源2", description = "测试资源2")
    @RequestMapping(value = "/test2", method = {RequestMethod.GET})
    public CommonApiResponse<String> test2(HttpServletRequest request) {
        return getSuccessfulApiResponse(resourceUserService.test());
    }

}
