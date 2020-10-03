package com.jeramtough.randl2.userapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.params.registereduser.*;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.randl2.common.model.dto.RegisteredUserDto;
import com.jeramtough.randl2.userapp.service.RegisteredUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@Api(tags = {"普通注册用户接口"})
@RequestMapping("/registeredUser")
@ApiResponses(value = {

})
public class RegisteredUserController extends BaseController {

    private final RegisteredUserService registeredUserService;

    @Autowired
    public RegisteredUserController(
            RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    @ApiOperation(value = "查询全部", notes = "得到全部普通注册用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RegisteredUserDto>> getAllAdminUser() {
        return getSuccessfulApiResponse(registeredUserService.getAllBaseDto());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询普通注册用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RegisteredUserDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                registeredUserService.getBaseDtoListByPage(queryByPageParams));
    }

    @LoggingOperation
    @ApiOperation(value = "移除", notes = "移除普通注册用户")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "普通注册用户Id", paramType = "query",
                    required = true)})
    public CommonApiResponse<String> removeAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(registeredUserService.removeRegisteredUser(uid));
    }

    @LoggingOperation
    @ApiOperation(value = "更新", notes = "更新普通注册用户账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateRegisteredUser(
            @RequestBody UpdateRegisteredUserParams params) {
        return getSuccessfulApiResponse(registeredUserService.updateRegisteredUser(params));
    }

    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个普通注册用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RegisteredUserDto>> getRegisteredUsersByKeyword(
            @RequestParam String keyword) {
        return getSuccessfulApiResponse(
                registeredUserService.getRegisteredUsersByKeyword(keyword));
    }


}

