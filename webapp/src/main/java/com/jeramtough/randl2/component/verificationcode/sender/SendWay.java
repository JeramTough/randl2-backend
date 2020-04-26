package com.jeramtough.randl2.component.verificationcode.sender;

/**
 * Created on 2018-09-15 11:41
 * by @author JeramTough
 */
public enum SendWay {
    /**
     * PHONE
     */
    PHONE(1, "手机号码"),

    /**
     * EMAIL
     */
    EMAIL(2, "邮箱地址");

    private int way;

    private String name;

    SendWay(int way, String name) {
        this.way = way;
        this.name = name;
    }

    public int getWay() {
        return way;
    }

    public static SendWay getSendWay(int way) {
        for (SendWay sendWay : SendWay.values()) {
            if (sendWay.getWay() == way) {
                return sendWay;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
