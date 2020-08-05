package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.model.error.ErrorU;
import com.jeramtough.randl2.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.service.PersonalInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@Api(tags = {"普通用户个人信息接口"})
@RequestMapping("/personalInfo")
public class PersonalInfoController extends BaseController {

    private final PersonalInfoService personalInfoService;

    @Autowired
    public PersonalInfoController(
            PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @ApiOperation(value = "查询一个", notes = "查询一个普通用户个人信息")
    @RequestMapping(value = "/oneByUid", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<PersonalInfoDto> getPersonalInfoByUid(Long uid) {
        return getSuccessfulApiResponse(personalInfoService.getPersonalInfoByUid(uid));
    }

    @ApiOperation(value = "更新", notes = "更新普通用户个人信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_102.C, message = ErrorU.CODE_102.M),
    })
    public CommonApiResponse<String> updateAdminUser(
            @RequestBody UpdatePersonalInfoParams params) {
        return getSuccessfulApiResponse(personalInfoService.updatePersonalInfo(params));
    }

}

