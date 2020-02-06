package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <pre>
 * Created on 2020/2/6 14:56
 * by @author JeramTough
 * </pre>
 */
@ApiResponses({
        @ApiResponse(code = 667, message = "[%]失败! [%s]参数不能为空"),
        @ApiResponse(code = 668, message = "[%]参数格式不正确! 应为[%s]")
})
public abstract class BaseController extends BaseSwaggerController {


}
