package com.jeramtough.randl2.component.registereduser;

/**
 * <pre>
 * Created on 2020/2/17 1:33
 * by @author JeramTough
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

    private int way;

    RegisterUserWay(int way) {
        this.way = way;
    }

    public int getWay() {
        return way;
    }

    public static RegisterUserWay toRegisterUserWay(int way){
        for (RegisterUserWay registerUserWay : RegisterUserWay.values()) {
            if (registerUserWay.getWay()==way){
                return registerUserWay;
            }
        }
        return null;
    }

}
