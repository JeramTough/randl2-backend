package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.adminapp.service.RandlModuleService;
import com.jeramtough.randl2.common.model.params.module.AddRandlModuleParams;
import com.jeramtough.randl2.common.model.params.module.ConditionModuleParams;
import com.jeramtough.randl2.common.model.params.module.TreeModuleParams;
import com.jeramtough.randl2.common.model.params.module.UpdateModuleParams;
import io.swagger.annotations.*;
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
 * @since 2020-08-06
 */
@Api(tags = {"模块接口"})
@RestController
@RequestMapping("/randlModule")
public class RandlModuleController extends BaseController {

    private final RandlModuleService randlModuleService;

    @Autowired
    public RandlModuleController(RandlModuleService randlModuleService) {
        this.randlModuleService = randlModuleService;
    }

    @ApiOperation(value = "新增", notes = "新增Randl模块")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> add(@RequestBody AddRandlModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.add(params));
    }

    @ApiOperation(value = "删除", notes = "删除Randl模块")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlModuleService.removeOneById(fid));
    }

    @ApiOperation(value = "删除链", notes = "删除Randl模块其下的子节点")
    @RequestMapping(value = "/removeChain", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> removeChainById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlModuleService.removeChainById(fid));
    }

    @ApiOperation(value = "更新", notes = "更新Randl模块")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> update(@RequestBody UpdateModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.update(params));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统菜单")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlModuleDto>> getAll() {
        return getSuccessfulApiResponse(randlModuleService.getAllBaseDto());
    }

    @ApiOperation(value = "条件查询", notes = "根据关键字等条件查询得到一个Randl应用信息")
    @ApiResponses(value = {})
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlModuleDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.pageByCondition(queryByPageParams, params));
    }


    @ApiOperation(value = "查询树形列表", notes = "通过条件查询系统模块列表")
    @RequestMapping(value = "/tree", method = {RequestMethod.GET})
    public CommonApiResponse<Map<String, Object>> getTreeModuleList(TreeModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.getTreeModuleList(params));
    }

}

