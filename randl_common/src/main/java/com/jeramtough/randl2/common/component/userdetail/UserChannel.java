package com.jeramtough.randl2.common.component.userdetail;

/**
 * <pre>
 *
 *     用户来源渠道， 0:管理员添加 | 1:用户注册 | 2:数据库直接添加
 *
 * Created on 2020/10/3 12:48
 * by @author WeiBoWen
 * </pre>
 */
public enum UserChannel {

    /**
     * 管理员添加
     */
    ADMIN_ADDED(0),

    /**
     * 用户注册
     */
    REGISTERED(1),

    /**
     * 数据库直接添加
     */
    DATABASE_ADDED(2);


    private int code;

    UserChannel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
