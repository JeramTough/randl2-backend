package com.jeramtough.randl2.resource.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.location.bean.JtLocation;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.resource.service.ResourceLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/11/14 23:11
 * by @author WeiBoWen
 * </pre>
 */
@Tag(name = "位置资源接口")
@RestController
@RequestMapping("/location")
public class LocationController extends MyBaseController {

    private final ResourceLocationService resourceLocationService;

    @Autowired
    public LocationController(
            ResourceLocationService resourceLocationService) {
        this.resourceLocationService = resourceLocationService;
    }


    @Operation(summary = "查询客户端位置信息", description = "查询客户端位置信息,传了经纬度就用经纬度，不然用客户端ip")
    @RequestMapping(value = "/getByAuto", method = {RequestMethod.GET})
    
    public CommonApiResponse<JtLocation> getLocation(HttpServletRequest request,
                                                     String lon,
                                                     String lat) {
        return getSuccessfulApiResponse(
                resourceLocationService.getLocationByAuto(request, lon, lat));
    }


}
