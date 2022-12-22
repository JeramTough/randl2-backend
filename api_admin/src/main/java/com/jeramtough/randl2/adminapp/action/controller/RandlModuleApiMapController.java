package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.params.module.SetModuleApiMapParams;
import com.jeramtough.randl2.service.randl.RandlModuleApiMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
 * @since 2020-08-06
 */
@Tag(name = "Randl模块与接口映射关系接口")
@RestController
@RequestMapping("/randlModuleApiMap")
public class RandlModuleApiMapController extends MyBaseController {

    private final RandlModuleApiMapService randlModuleApiMapService;

    @Autowired
    public RandlModuleApiMapController(
            RandlModuleApiMapService randlModuleApiMapService) {
        this.randlModuleApiMapService = randlModuleApiMapService;
    }


    @RegApi
    @Operation(summary = "查询关系通过(1)", description = "查询关系通过应用Id和模块Id,返回接口Id列表")
    @Parameters({
            @Parameter(name = "appId", description = "应用Id", required = true, example = "1"),
            @Parameter(name = "moduleId", description = "模块Id", required = true, example = "1")
    })
    @RequestMapping(value = "/getMapBy", method = {RequestMethod.GET})
    public CommonApiResponse<List<Long>> getMapByAppIdAndModuleId(@RequestParam(value = "appId") Long appId,
                                                                  @RequestParam(value = "moduleId")
                                                                          Long moduleId) {
        return getSuccessfulApiResponse(randlModuleApiMapService.getApiListByAppIdAndModuleId(appId, moduleId));
    }

    @RegApi
    @Operation(summary = "设置映射关系", description = "设置模块与接口的映射关系")
    @RequestMapping(value = "/setMap", method = {RequestMethod.POST})
    public CommonApiResponse<String> setMap(@RequestBody SetModuleApiMapParams params) {
        return getSuccessfulApiResponse(randlModuleApiMapService.setModuleApiMap(params));
    }


}

