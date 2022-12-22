package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlModuleDto;
import com.jeramtough.randl2.common.model.params.module.AddRandlModuleParams;
import com.jeramtough.randl2.common.model.params.module.ConditionModuleParams;
import com.jeramtough.randl2.common.model.params.module.TreeModuleParams;
import com.jeramtough.randl2.common.model.params.module.UpdateModuleParams;
import com.jeramtough.randl2.service.randl.RandlModuleService;
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
 * @since 2020-08-06
 */
@Tag(name = "Randl模块接口")
@RestController
@RequestMapping("/randlModule")
public class RandlModuleController extends MyBaseController {

    private final RandlModuleService randlModuleService;

    @Autowired
    public RandlModuleController(RandlModuleService randlModuleService) {
        this.randlModuleService = randlModuleService;
    }

    @RegApi
    @Operation(summary = "新增", description = "新增Randl模块")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    
    public CommonApiResponse<String> add(@RequestBody AddRandlModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.add(params));
    }

    @RegApi
    @Operation(summary = "删除", description = "删除Randl模块")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlModuleService.removeOneById(fid));
    }

    @RegApi
    @Operation(summary = "删除链", description = "删除Randl模块其下的子节点")
    @RequestMapping(value = "/removeChain", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    
    public CommonApiResponse<String> removeChainById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlModuleService.removeChainById(fid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新Randl模块")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    
    public CommonApiResponse<String> update(@RequestBody UpdateModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.update(params));
    }

    @RegApi
    @Operation(summary = "查询全部", description = "查询全部系统菜单")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlModuleDto>> getAll() {
        return getSuccessfulApiResponse(randlModuleService.getAllBaseDto());
    }

    @RegApi
    @Operation(summary = "条件查询", description = "根据关键字等条件查询得到一个Randl应用信息")
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlModuleDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.pageByCondition(queryByPageParams, params));
    }

    @RegApi
    @Operation(summary = "查询树形列表", description = "通过条件查询系统模块列表")
    @RequestMapping(value = "/tree", method = {RequestMethod.GET})
    public CommonApiResponse<Map<String, Object>> getTreeModuleList(TreeModuleParams params) {
        return getSuccessfulApiResponse(randlModuleService.getTreeModuleList(params));
    }

}

