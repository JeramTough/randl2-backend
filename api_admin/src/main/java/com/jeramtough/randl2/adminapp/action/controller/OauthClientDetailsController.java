package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthClientDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthClientDetailsParams;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Api(tags = {"OAuth2客户端接口"})
@RestController
@RequestMapping("/oauthClientDetails")
public class OauthClientDetailsController extends MyBaseController {

    private final OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    public OauthClientDetailsController(
            OauthClientDetailsService oauthClientDetailsService) {
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @RegApi
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> add(@RequestBody AddOauthClientDetailsParams params) {
        return getSuccessfulApiResponse(oauthClientDetailsService.add(params));
    }

    @RegApi
    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(oauthClientDetailsService.removeOneById(fid));
    }

    @RegApi
    @ApiOperation(value = "更新", notes = "更新")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public CommonApiResponse<String> update(@RequestBody UpdateOauthClientDetailsParams params) {
        return getSuccessfulApiResponse(oauthClientDetailsService.updateByParams(params));
    }

    @RegApi
    @ApiOperation(value = "查询一个", notes = "查询一个")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<OauthClientDetailsDto> getOne(Long fid) {
        return getSuccessfulApiResponse(oauthClientDetailsService.getBaseDtoById(fid));
    }

    @RegApi
    @ApiOperation(value = "根据AppId查询一个", notes = "根据AppId查询一个")
    @RequestMapping(value = "/oneByAppId", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "AppId", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<OauthClientDetailsDto> getOneByAppId(Long appId) {
        return getSuccessfulApiResponse(oauthClientDetailsService.getOneByAppId(appId));
    }


}

