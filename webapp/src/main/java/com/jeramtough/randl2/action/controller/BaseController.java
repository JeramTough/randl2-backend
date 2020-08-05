package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.randl2.model.error.ErrorS;
import com.jeramtough.randl2.model.error.ErrorU;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <pre>
 * Created on 2020/2/6 14:56
 * by @author JeramTough
 * </pre>
 */
@ApiResponses({
        @ApiResponse(code = ErrorU.CODE_1.C, message = ErrorU.CODE_1.M),
        @ApiResponse(code = ErrorU.CODE_2.C, message = ErrorU.CODE_2.M),
        @ApiResponse(code = ErrorU.CODE_3.C, message = ErrorU.CODE_3.M),
        @ApiResponse(code = ErrorU.CODE_4.C, message = ErrorU.CODE_4.M),
        @ApiResponse(code = ErrorU.CODE_5.C, message = ErrorU.CODE_5.M),
        @ApiResponse(code = ErrorU.CODE_6.C, message = ErrorU.CODE_6.M),
        @ApiResponse(code = ErrorU.CODE_7.C, message = ErrorU.CODE_7.M),
        @ApiResponse(code = ErrorU.CODE_8.C, message = ErrorU.CODE_8.M),
        @ApiResponse(code = ErrorU.CODE_9.C, message = ErrorU.CODE_9.M),

        @ApiResponse(code = ErrorS.CODE_1.C, message = ErrorS.CODE_1.M),
})
public abstract class BaseController extends BaseSwaggerController {


}
