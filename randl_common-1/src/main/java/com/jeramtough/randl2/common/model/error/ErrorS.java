package com.jeramtough.randl2.common.model.error;

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
        public static final String M = "【系统公共错误码】系统执行超时。。。";
    }

    public static final class CODE_2 {
        public static final int C = 10002;
        public static final String M = "【系统公共错误码】系统读取资源失败！";
    }

    public static final class CODE_3 {
        public static final int C = 10003;
        public static final String M = "【系统公共错误码】系统添加[%s]资源失败！";
    }

    //=========================


}
