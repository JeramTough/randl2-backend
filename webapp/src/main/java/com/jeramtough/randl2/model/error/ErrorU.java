package com.jeramtough.randl2.model.error;

import com.jeramtough.jtweb.model.error.ErrorCodePrefix;
import io.swagger.annotations.ApiResponse;

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


    //上边为公用错误码
    public static final class CODE_1 {
        public static final int C = 20001;
        public static final String M = "【系统公共错误码】用户传参异常! [%s]参数不能为空";
    }

    public static final class CODE_2 {
        public static final int C = 20002;
        public static final String M = "【系统公共错误码】[%s]参数格式不正确! 应为[%s]";
    }

    public static final class CODE_3 {
        public static final int C = 20003;
        public static final String M = "【系统公共错误码】token头不存在或者格式不正确";
    }

    public static final class CODE_4 {
        public static final int C = 20004;
        public static final String M = "【系统公共错误码】JWT token验证失败！";
    }

    public static final class CODE_5 {
        public static final int C = 20005;
        public static final String M = "【系统公共错误码】传递参数范围错误，应为[%s]！";
    }

    public static final class CODE_6 {
        public static final int C = 20006;
        public static final String M = "【系统公共错误码】传递参数违背规则，[%s参数]应为[%s]！";
    }

    //****************************************************
    //下边为特定错误码

    //=================管理员用户接口业务===========================

    public static final class CODE_101 {
        public static final int C = 20101;
        public static final String M = "登录失败，请检查账号或密码！";
    }

    public static final class CODE_102 {
        public static final int C = 20102;
        public static final String M = "添加管理员用户失败！两次密码不一致！";
    }

    public static final class CODE_103 {
        public static final int C = 20103;
        public static final String M = "添加失败！存在同名用户名！";
    }

    public static final class CODE_104 {
        public static final int C = 20104;
        public static final String M = "添加失败！已存在重复的手机号码，请换一个！";
    }

    public static final class CODE_105 {
        public static final int C = 20105;
        public static final String M = "添加失败！已存在重复的邮箱地址，请换一个！";
    }

    //============普通注册用户接口业务============


    public static final class CODE_201 {
        public static final int C = 20201;
        public static final String M = "验证失败，已存在重复的邮箱地址，请换一个！";
    }

    public static final class CODE_202 {
        public static final int C = 20202;
        public static final String M = "验证失败，已存在重复的手机号码，请换一个！";
    }

    public static final class CODE_203 {
        public static final int C = 20203;
        public static final String M = "该手机号或者邮箱地址未注册或绑定过本系统！";
    }

    public static final class CODE_205 {
        public static final int C = 20205;
        public static final String M = "校验失败！两次密码不一致！";
    }

    public static final class CODE_206 {
        public static final int C = 20206;
        public static final String M = "新密码不能和旧密码一致！";
    }

    public static final class CODE_207 {
        public static final int C = 20207;
        public static final String M = "注册未完成或信息以失效，请重新注册！";
    }

    public static final class CODE_208 {
        public static final int C = 20208;
        public static final String M = "验证码校验失败，或验证码未发送或以失效！";
    }

    public static final class CODE_209 {
        public static final int C = 20209;
        public static final String M = "重置未完成或信息以失效，请重新开始重置流程！";
    }

    public static final class CODE_210 {
        public static final int C = 20210;
        public static final String M = "重置失败！账户信息未做过任何修改！";
    }

}
