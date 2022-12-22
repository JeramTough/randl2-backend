package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.user.ConditionUserParams;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import com.jeramtough.randl2.common.model.params.user.UpdateRandlUserParams;
import com.jeramtough.randl2.service.randl.RandlUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Randl用户接口")
@RestController
@RequestMapping("/randlUser")
public class RandlUserController extends MyBaseController {

    private final RandlUserService randlUserService;

    @Autowired
    public RandlUserController(RandlUserService randlUserService) {
        this.randlUserService = randlUserService;
    }

    @RegApi
    @Operation(summary = "增加", description = "添加一个Randl用户")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_101.C, description = ErrorU.CODE_101.M),
    })
    public CommonApiResponse<RandlUserDto> addRandlUser(
            @RequestBody RegisterRandlUserParams params) {
        return getSuccessfulApiResponse(randlUserService.add(params));
    }

    @RegApi
    @Operation(summary = "查询全部", description = "得到全部Randl用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlUserDto>> getAllRandlUser() {
        return getSuccessfulApiResponse(randlUserService.getAllBaseDto());
    }

    @RegApi
    @Operation(summary = "分页查询", description = "分页查询Randl用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlUserDto>> getRandlUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlUserService.getBaseDtoListByPage(queryByPageParams));
    }

    @RegApi
    @Operation(summary = "查询一个", description = "得到一个Randl用户信息")
    @Parameters({
            @Parameter(name = "uid", description = "Randl用户Id",
                    required = true, example = "1")})
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    public CommonApiResponse<RandlUserDto> getOneAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(randlUserService.getBaseDtoById(uid));
    }

    @RegApi
    @Operation(summary = "条件查询", description = "根据关键字等条件查询得到一个Randl用户信息")
    @ApiResponses(value = {})
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlUserDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionUserParams params) {
        return getSuccessfulApiResponse(randlUserService.pageByCondition(queryByPageParams,params));
    }

    @RegApi
    @Operation(summary = "移除", description = "移除Randl用户")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @Parameters({
            @Parameter(name = "uid", description = "Randl用户Id",
                    required = true)})
    public CommonApiResponse<String> removeRandlUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(randlUserService.removeRandUserById(uid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新系统管理员账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    
    public CommonApiResponse<String> updateRandlUser(
            @RequestBody UpdateRandlUserParams params) {
        return getSuccessfulApiResponse(randlUserService.updateRandlUser(params));
    }


}

