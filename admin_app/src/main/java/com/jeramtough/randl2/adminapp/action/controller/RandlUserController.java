package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.adminuser.RegisterRandlUserParams;
import com.jeramtough.randl2.adminapp.service.RandlUserService;
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
 * @since 2020-10-03
 */
@Api(tags = {"Randl用户接口"})
@RestController
@RequestMapping("/randlUser")
public class RandlUserController extends BaseController {

    private final RandlUserService randlUserService;

    @Autowired
    public RandlUserController(RandlUserService randlUserService) {
        this.randlUserService = randlUserService;
    }

    @LoggingOperation
    @ApiOperation(value = "增加", notes = "添加一个Randl用户")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_101.C, message = ErrorU.CODE_101.M),
    })
    public CommonApiResponse<RandlUserDto> addRandlUser(
            @RequestBody RegisterRandlUserParams params) {
        return getSuccessfulApiResponse(randlUserService.add(params));
    }

    @ApiOperation(value = "查询全部", notes = "得到全部Randl用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlUserDto>> getAllRandlUser() {
        return getSuccessfulApiResponse(randlUserService.getAllBaseDto());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询Randl用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlUserDto>> getRandlUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlUserService.getBaseDtoListByPage(queryByPageParams));
    }

    @ApiOperation(value = "查询一个", notes = "得到一个Randl用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "Randl用户Id", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {})
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    public CommonApiResponse<RandlUserDto> getOneAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(randlUserService.getBaseDtoById(uid));
    }

    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个Randl用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @ApiResponses(value = {})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<RandlUserDto> getOneRandlUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(randlUserService.getRandlUserByKeyword(keyword));
    }

    @LoggingOperation
    @ApiOperation(value = "移除", notes = "移除Randl用户")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "Randl用户Id", paramType = "query",
                    required = true)})
    public CommonApiResponse<String> removeRandlUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(randlUserService.removeRandUserById(uid));
    }

    /*@LoggingOperation
    @ApiOperation(value = "更新", notes = "更新系统管理员账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 1030, message = "更新失败! [%s]参数不能为空"),
            @ApiResponse(code = 1031, message = "更新失败！该Randl用户不存在！"),
            @ApiResponse(code = 1032, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 1033, message = "更新失败！存在同名用户名"),
            @ApiResponse(code = 1034, message = "用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符"),
            @ApiResponse(code = 1035, message = "手机号码格式错误"),
            @ApiResponse(code = 1036, message = "邮箱地址格式错误"),
            @ApiResponse(code = 1037, message = "已存在重复的手机号码，请换一个"),
            @ApiResponse(code = 1038, message = "已存在重复的邮箱地址，请换一个"),
            @ApiResponse(code = 1039, message = "该角色Id不存在")
    })
    public CommonApiResponse<String> updateAdminUser(
            @RequestBody UpdateAdminUserParams params) {
        return getSuccessfulApiResponse(adminUserService.updateAdminUser(params));
    }*/


}

