package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.params.module.SetModuleRoleMapParams;
import com.jeramtough.randl2.service.randl.RandlModuleRoleMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "RandlRandl模块角色映射接口")
@RestController
@RequestMapping("/randlModuleRoleMap")
public class RandlModuleRoleMapController extends MyBaseController {

    private final RandlModuleRoleMapService randlModuleRoleMapService;

    @Autowired
    public RandlModuleRoleMapController(
            RandlModuleRoleMapService randlModuleRoleMapService) {
        this.randlModuleRoleMapService = randlModuleRoleMapService;
    }

    @RegApi
    @Operation(summary = "查询角色与模块映射(1)", description = "查询角色与模块映射授权关系根据应用Id和角色Id")
    @Parameters({
            @Parameter(name = "appId", description = "应用Id", required = true, example = "1"),
            @Parameter(name = "roleId", description = "角色Id", required = true, example = "1")
    })
    @RequestMapping(value = "/getMapBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlModuleAuthDto>> getMapByAppIdAndModuleId(
            @RequestParam(value = "appId") Long appId,
            @RequestParam(value = "roleId") Long roleId) {
        return getSuccessfulApiResponse(
                randlModuleRoleMapService.getRandlModuleAuthDtosByAppIdAndRoleId(appId,
                        roleId));
    }

    @RegApi
    @Operation(summary = "查询角色与模块映射-树形结构(1)", description = "查询角色与模块映射授权关系的树形结构根据应用Id和角色Id")
    @Parameters({
            @Parameter(name = "appId", description= "应用Id", required = true, example = "1"),
            @Parameter(name = "roleId", description = "角色Id", required = true, example = "1")
    })
    @RequestMapping(value = "/getTreeBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<Map<String, Object>>> getTreeByAppIdAndModuleId(
            @RequestParam(value = "appId") Long appId,
            @RequestParam(value = "roleId") Long roleId) {
        return getSuccessfulApiResponse(
                randlModuleRoleMapService.getRandlModuleAuthTreeByAppIdAndRoleId(appId,
                        roleId));
    }


    @RegApi
    @Operation(summary = "设置映射关系", description = "设置模块与角色的映射关系")
    @RequestMapping(value = "/setMap", method = {RequestMethod.POST})
    public CommonApiResponse<String> setMap(@RequestBody SetModuleRoleMapParams params) {
        return getSuccessfulApiResponse(randlModuleRoleMapService.setModuleRoleMap(params));
    }


}

