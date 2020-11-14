package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.service.randl.RandlModuleApiMapService;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.model.params.module.*;
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
 * @since 2020-08-06
 */
@Api(tags = {"模块与接口映射关系接口"})
@RestController
@RequestMapping("/randlModuleApiMap")
public class RandlModuleApiMapController extends BaseController {

    private final RandlModuleApiMapService randlModuleApiMapService;

    @Autowired
    public RandlModuleApiMapController(
            RandlModuleApiMapService randlModuleApiMapService) {
        this.randlModuleApiMapService = randlModuleApiMapService;
    }


    @ApiOperation(value = "查询关系通过(1)", notes = "查询关系通过应用Id和模块Id,返回接口Id列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "moduleId", value = "模块Id", required = true, defaultValue = "1")
    })
    @RequestMapping(value = "/getMapBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<Long>> getMapByAppIdAndModuleId(@RequestParam(value = "appId") Long appId,
                                                                  @RequestParam(value = "moduleId")
                                                                          Long moduleId) {
        return getSuccessfulApiResponse(randlModuleApiMapService.getApiListByAppIdAndModuleId(appId, moduleId));
    }

    @ApiOperation(value = "设置映射关系", notes = "设置模块与接口的映射关系")
    @RequestMapping(value = "/setMap", method = {RequestMethod.POST})
    public CommonApiResponse<String> setMap(@RequestBody SetModuleApiMapParams params) {
        return getSuccessfulApiResponse(randlModuleApiMapService.setModuleApiMap(params));
    }


}

