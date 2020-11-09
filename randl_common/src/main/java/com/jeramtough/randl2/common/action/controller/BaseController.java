package com.jeramtough.randl2.common.action.controller;

import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
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
        @ApiResponse(code = ErrorU.CODE_10.C, message = ErrorU.CODE_10.M),
        @ApiResponse(code = ErrorU.CODE_11.C, message = ErrorU.CODE_11.M),
        @ApiResponse(code = ErrorU.CODE_12.C, message = ErrorU.CODE_12.M),

        @ApiResponse(code = ErrorS.CODE_1.C, message = ErrorS.CODE_1.M),
        @ApiResponse(code = ErrorS.CODE_2.C, message = ErrorS.CODE_2.M),
        @ApiResponse(code = ErrorS.CODE_3.C, message = ErrorS.CODE_3.M),
        @ApiResponse(code = ErrorS.CODE_4.C, message = ErrorS.CODE_4.M),
        @ApiResponse(code = ErrorS.CODE_5.C, message = ErrorS.CODE_5.M),
        @ApiResponse(code = ErrorS.CODE_6.C, message = ErrorS.CODE_6.M),
})
public abstract class BaseController extends BaseSwaggerController {


}
