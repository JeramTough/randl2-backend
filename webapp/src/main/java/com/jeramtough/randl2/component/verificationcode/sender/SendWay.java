package com.jeramtough.randl2.component.verificationcode.sender;

/**
 * Created on 2018-09-15 11:41
 * by @author JeramTough
 */
public enum SendWay {
    /**
     * PHONE
     */
    PHONE(1),

    /**
     * EMAIL
     */
    EMAIL(2);

    private int way;

    SendWay(int way) {
        this.way = way;
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


}
