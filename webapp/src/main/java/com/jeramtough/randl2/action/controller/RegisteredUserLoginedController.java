package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/3/22 23:00
 * by @author JeramTough
 * </pre>
 */
@RestController
@Api(tags = {"普通注册用户登录后接口"})
@RequestMapping("/registeredUserLogined")
public class RegisteredUserLoginedController extends BaseController{

    @ApiOperation(value = "绑定", notes = "绑定账号的手机号码或者邮箱")
    @RequestMapping(value = "/bindingPhoneOrEmail", method = {RequestMethod.POST})
    public RestfulApiResponse bindingPhoneOrEmail(){
        L.arrive();
        return getSuccessfulApiResponse("tttt");
    }

}
