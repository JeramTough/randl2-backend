package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.service.randl.RandlModuleRoleMapService;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.params.module.SetModuleRoleMapParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Api(tags = {"Randl模块角色映射接口"})
@RestController
@RequestMapping("/randlModuleRoleMap")
public class RandlModuleRoleMapController extends MyBaseController {

    private final RandlModuleRoleMapService randlModuleRoleMapService;

    @Autowired
    public RandlModuleRoleMapController(
            RandlModuleRoleMapService randlModuleRoleMapService) {
        this.randlModuleRoleMapService = randlModuleRoleMapService;
    }

    @ApiOperation(value = "查询角色与模块映射(1)", notes = "查询角色与模块映射授权关系根据应用Id和角色Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, defaultValue = "1")
    })
    @RequestMapping(value = "/getMapBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlModuleAuthDto>> getMapByAppIdAndModuleId(
            @RequestParam(value = "appId") Long appId,
            @RequestParam(value = "roleId") Long roleId) {
        return getSuccessfulApiResponse(
                randlModuleRoleMapService.getRandlModuleAuthDtosByAppIdAndRoleId(appId, roleId));
    }

    @ApiOperation(value = "查询角色与模块映射-树形结构(1)", notes = "查询角色与模块映射授权关系的树形结构根据应用Id和角色Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, defaultValue = "1")
    })
    @RequestMapping(value = "/getTreeBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<Map<String, Object>>> getTreeByAppIdAndModuleId(
            @RequestParam(value = "appId") Long appId,
            @RequestParam(value = "roleId") Long roleId) {
        return getSuccessfulApiResponse(randlModuleRoleMapService.getRandlModuleAuthTreeByAppIdAndRoleId(appId,
                roleId));
    }


    @ApiOperation(value = "设置映射关系", notes = "设置模块与角色的映射关系")
    @RequestMapping(value = "/setMap", method = {RequestMethod.POST})
    public CommonApiResponse<String> setMap(@RequestBody SetModuleRoleMapParams params) {
        return getSuccessfulApiResponse(randlModuleRoleMapService.setModuleRoleMap(params));
    }


}

