package com.jeramtough.randl2.resource.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.location.bean.JtLocation;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.resource.service.ResourceLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/14 23:11
 * by @author WeiBoWen
 * </pre>
 */
@Api(tags = {"位置资源接口"})
@RestController
@RequestMapping("/location")
public class LocationController extends MyBaseController {

    private final ResourceLocationService resourceLocationService;

    @Autowired
    public LocationController(
            ResourceLocationService resourceLocationService) {
        this.resourceLocationService = resourceLocationService;
    }


    @ApiOperation(value = "查询客户端位置信息", notes = "查询客户端位置信息,传了经纬度就用经纬度，不然用客户端ip")
    @RequestMapping(value = "/getByAuto", method = {RequestMethod.GET})
    @ApiResponses(value = {
    })
    public CommonApiResponse<JtLocation> getLocation(HttpServletRequest request,
                                                     String lon,
                                                     String lat) {
        return getSuccessfulApiResponse(
                resourceLocationService.getLocationByAuto(request, lon, lat));
    }


}
