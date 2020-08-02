package com.jeramtough.randl2.model.error;

import com.jeramtough.jtweb.model.error.ErrorCodePrefix;

/**
 * <pre>
 * api接口错误响应码配置类
 * 用户行为错误响应码前缀
 * 前缀必须符合{@link ErrorCodePrefix}要求
 * </pre>
 *
 * <pre>
 * Created on 2020/6/11 2:19
 * by @author JeramTough
 * </pre>
 */
public class ErrorU {

    public static final class CODE_1 {
        public static final int C = 20001;
        public static final String M = "用户传参异常! [%s]参数不能为空";
    }

    public static final class CODE_2 {
        public static final int C = 20002;
        public static final String M = "[%s]参数格式不正确! 应为[%s]";
    }

    public static final class CODE_3 {
        public static final int C = 20003;
        public static final String M = "token头不存在或者格式不正确";
    }

    public static final class CODE_4 {
        public static final int C = 20004;
        public static final String M = "JWT token验证失败！";
    }

    public static final class CODE_5 {
        public static final int C = 20005;
        public static final String M = "传递参数范围错误，应为[%s]！";
    }


}
