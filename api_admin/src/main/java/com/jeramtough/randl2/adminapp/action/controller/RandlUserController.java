package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.user.ConditionUserParams;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import com.jeramtough.randl2.service.randl.RandlUserService;
import com.jeramtough.randl2.common.model.params.user.UpdateRandlUserParams;
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
public class RandlUserController extends MyBaseController {

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

    @ApiOperation(value = "条件查询", notes = "根据关键字等条件查询得到一个Randl用户信息")
    @ApiResponses(value = {})
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlUserDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionUserParams params) {
        return getSuccessfulApiResponse(randlUserService.pageByCondition(queryByPageParams,params));
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

    @LoggingOperation
    @ApiOperation(value = "更新", notes = "更新系统管理员账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateRandlUser(
            @RequestBody UpdateRandlUserParams params) {
        return getSuccessfulApiResponse(randlUserService.updateRandlUser(params));
    }


}

