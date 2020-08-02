package com.jeramtough.randl2.model.error;

import com.jeramtough.jtweb.model.error.ErrorCodePrefix;

/**
 * <pre>
 * api接口错误响应码配置类
 * 系统异常或者错误响应码前缀
 * 前缀必须符合{@link ErrorCodePrefix}要求
 * </pre>
 *
 * <pre>
 * Created on 2020/6/11 2:19
 * by @author JeramTough
 * </pre>
 */
public class ErrorS {

    public static final class CODE_1 {
        public static final int C = 10001;
        public static final String M = "系统没有发现该ID对应的目标资源";
    }



}
