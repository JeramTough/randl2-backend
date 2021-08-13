package com.jeramtough.randl2.common.model.detail.userdetail;

/**
 * <pre>
 * Created on 2021/1/14 1:21
 * by @author WeiBoWen
 * </pre>
 */
public enum RegisterUserWay {
    /**
     *
     */
    PHONE_USER_WAY(1),

    /**
     *
     */
    EMAIL_USER_WAY(2);

    private final int way;

    RegisterUserWay(int way) {
        this.way = way;
    }

    public int getWay() {
        return way;
    }

    public static RegisterUserWay toRegisterUserWay(int way) {
        for (RegisterUserWay registerUserWay : RegisterUserWay.values()) {
            if (registerUserWay.getWay() == way) {
                return registerUserWay;
            }
        }
        return null;
    }
}
