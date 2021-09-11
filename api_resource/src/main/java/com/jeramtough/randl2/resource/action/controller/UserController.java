package com.jeramtough.randl2.resource.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.resource.service.ResourceUserService;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/11/14 23:11
 * by @author WeiBoWen
 * </pre>
 */
@Api(tags = {"用户资源接口"})
@RestController
@RequestMapping("/user")
public class UserController extends MyBaseController {

    private final ResourceUserService resourceUserService;

    @Autowired
    public UserController(
            ResourceUserService resourceUserService) {
        this.resourceUserService = resourceUserService;
    }

    @ApiOperation(value = "查询登录用户信息", notes = "根据令牌查询登录用户信息")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    @ApiResponses(value = {
    })
    public CommonApiResponse<SystemUserDto> getRandlUserByToken() {
        return getSuccessfulApiResponse(resourceUserService.getRandlUserByToken());
    }

    @ApiOperation(value = "查询用户个人资料信息", notes = "查询用户个人资料信息")
    @RequestMapping(value = "/randlPersonalInfo", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> getPersonalInfoByUid(@Param("uid") Long uid,
                                                          HttpServletRequest request) {
        return getSuccessfulApiResponse("aaaaa");
    }

    @ApiOperation(value = "测试资源", notes = "测试资源")
    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public CommonApiResponse<String> test(HttpServletRequest request) {
        return getSuccessfulApiResponse(resourceUserService.test());
    }

    @ApiOperation(value = "测试资源2", notes = "测试资源2")
    @RequestMapping(value = "/test2", method = {RequestMethod.GET})
    public CommonApiResponse<String> test2(HttpServletRequest request) {
        return getSuccessfulApiResponse(resourceUserService.test());
    }

}
