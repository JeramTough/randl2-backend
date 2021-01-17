package com.jeramtough.randl2.common.model.error;

import com.jeramtough.jtweb.model.error.ErrorCodePrefix;

import javax.validation.Payload;

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

    public static final class CODE_1 implements Payload {
        public static final int C = 20001;
        public static final String M = "【系统公共错误码】[%t]传参异常! 参数不能为空null";
    }

    public static final class CODE_2 implements Payload {
        public static final int C = 20002;
        public static final String M = "【系统公共错误码】[%t]传参异常! 参数不能为空白字符串‘’";
    }

    public static final class CODE_3 implements Payload {
        public static final int C = 20003;
        public static final String M = "【系统公共错误码】[%t]传参异常! 参数不能为空null或者空白字符串‘’";
    }

    /**
     * 手机、邮箱等格式
     */
    public static final class CODE_4 implements Payload {
        public static final int C = 20004;
        public static final String M = "【系统公共错误码】[%t]传参格式不正确! 应为[%s]";
    }

    public static final class CODE_5 implements Payload {
        public static final int C = 20005;
        public static final String M = "【系统公共错误码】token头不存在或者格式不正确";
    }

    public static final class CODE_6 implements Payload {
        public static final int C = 20006;
        public static final String M = "【系统公共错误码】token验证失败！[%s]";
    }

    public static final class CODE_7 implements Payload {
        public static final int C = 20007;
        public static final String M = "【系统公共错误码】[%t]传参范围错误，应为[%s]！";
    }

    /**
     * 类似于密码规则违背，验证码1-6未数字规则违背
     */
    public static final class CODE_8 implements Payload {
        public static final int C = 20008;
        public static final String M = "【系统公共错误码】[%t]传参违背规则，应为[%s]！";
    }

    public static final class CODE_9 implements Payload {
        public static final int C = 20009;
        public static final String M = "【系统公共错误码】执行失败，用户请求的 [%s]目标资源不存在！";
    }

    public static final class CODE_10 implements Payload {
        public static final int C = 20010;
        public static final String M = "【系统公共错误码】执行失败，用户请求的[%t]对应的[%s]目标资源不存在！";
    }

    public static final class CODE_11 implements Payload {
        public static final int C = 20011;
        public static final String M = "【系统公共错误码】执行失败，该 [%s]目标资源以存在，请换一个！";
    }

    public static final class CODE_12 implements Payload {
        public static final int C = 20012;
        public static final String M = "【系统公共错误码】执行失败，用户请求[%t]对应的[%s]目标资源以存在，请换一个！";
    }

    public static final class CODE_13 implements Payload {
        public static final int C = 20013;
        public static final String M = "【系统公共错误码】执行失败，系统不存在该id对应的用户！";
    }

    //****************************************************
    //下边为特定错误码

    //=================传参校验业务===========================

    public static final class CODE_101 implements Payload {
        public static final int C = 20101;
        public static final String M = "校验失败！密码与重复密码不一致！";
    }

    public static final class CODE_102 implements Payload {
        public static final int C = 20102;
        public static final String M = "FID参数或者UID参数必须填其中一个";
    }

    //=================注册和重置和绑定相关业务===========================

    public static final class CODE_201 implements Payload {
        public static final int C = 20201;
        public static final String M = "事务已失效，或信息已失效，请重新开始[注册/重置]！";
    }

    public static final class CODE_202 implements Payload {
        public static final int C = 20202;
        public static final String M = "未设置密码，注册流程未完成！";
    }

    public static final class CODE_203 implements Payload {
        public static final int C = 20203;
        public static final String M = "重置失败！账户信息未做过任何修改！";
    }


    /*public static final class CODE_203 implements Payload {
        public static final int C = 20203;
        public static final String M = "重置未完成或信息以失效，请重新开始重置流程！";
    }

    public static final class CODE_204 implements Payload {
        public static final int C = 20204;
        public static final String M = "重置失败！账户信息未做过任何修改！";
    }

    public static final class CODE_205 implements Payload {
        public static final int C = 20205;
        public static final String M = "该手机号或者邮箱地址未注册或绑定过本系统！";
    }*/

    public static final class CODE_206 implements Payload {
        public static final int C = 20206;
        public static final String M = "重置失败，旧密码不正确！";
    }

    public static final class CODE_207 implements Payload {
        public static final int C = 20207;
        public static final String M = "绑定的号码或者地址不能与当前的重复！";
    }

    public static final class CODE_208 implements Payload {
        public static final int C = 20208;
        public static final String M = "绑定的号码或者地址已被其他账号所绑定！";
    }

    public static final class CODE_209 implements Payload {
        public static final int C = 20209;
        public static final String M = "新密码不能和旧密码一致！";
    }

    //=================登录业务===========================

    public static final class CODE_301 implements Payload {
        public static final int C = 20301;
        public static final String M = "登录失败，请检查账号或密码！";
    }

    public static final class CODE_302 implements Payload {
        public static final int C = 20302;
        public static final String M = "不存在该账号！";
    }

    public static final class CODE_303 implements Payload {
        public static final int C = 20303;
        public static final String M = "登录失败，该手机号码或者邮箱并没有注册过本系统";
    }

    public static final class CODE_304 implements Payload {
        public static final int C = 20304;
        public static final String M = "用户账号状态当前不可用！";
    }

    //=================验证码业务===========================

    public static final class CODE_401 implements Payload {
        public static final int C = 20401;
        public static final String M = "验证码校验结果获取失败！[%s]！";
    }

    public static final class CODE_402 implements Payload {
        public static final int C = 20402;
        public static final String M = "发送频率限制，距离下次可发送验证码还有%s秒！";
    }

    public static final class CODE_403 implements Payload {
        public static final int C = 20403;
        public static final String M = "发送失败，因为%s！";
    }

    public static final class CODE_404 implements Payload {
        public static final int C = 20404;
        public static final String M = "验证码消费失败！[%s]！";
    }


    //==============角色业务==============================

    public static final class CODE_501 implements Payload {
        public static final int C = 20501;
        public static final String M = " 删除系统角色失败！因为还有账号被赋予该角色，请修改后重试！";
    }

    /*public static final class CODE_502 implements Payload {
        public static final int C = 20502;
        public static final String M = "这个用户uid在这个appId应用中没有对应的角色！";
    }*/

    public static final class CODE_503 implements Payload {
        public static final int C = 20503;
        public static final String M = "[t]参数不能使用系统预设属性值！";
    }

    public static final class CODE_504 implements Payload {
        public static final int C = 20504;
        public static final String M = "操作失败！不能对Randl预设角色进行操作！";
    }
    //=================上传业务===========================

    public static final class CODE_601 implements Payload {
        public static final int C = 20601;
        public static final String M = "上传失败，上传数据为空！";
    }

    public static final class CODE_602 implements Payload {
        public static final int C = 20602;
        public static final String M = "上传失败，头像图片大小不允许超过%s！";
    }

    public static final class CODE_603 implements Payload {
        public static final int C = 20603;
        public static final String M = "上传失败，图片格式只能为%s！";
    }

    public static final class CODE_604 implements Payload {
        public static final int C = 20604;
        public static final String M = "管理员账户头像不允许被修改！";
    }

    //=================Randl应用业务===========================

    public static final class CODE_701 implements Payload {
        public static final int C = 20701;
        public static final String M = "操作失败！不能对Randl管理应用和Randl客户端应用进行操作！";
    }

    //=================Oauth2认证业务===========================

    public static final class CODE_801 implements Payload {
        public static final int C = 20801;
        public static final String M = "oauth2授权认证错误！[%s]";
    }

    public static final class CODE_802 implements Payload {
        public static final int C = 20802;
        public static final String M = "密码认证模式缺失参数[用户名密码]或者[手机邮箱验证码]！";
    }

}
