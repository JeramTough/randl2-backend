package com.jeramtough.randl2.common.component.attestation.userdetail;

/**
 * <pre>
 * Created on 2020/10/3 23:03
 * by @author WeiBoWen
 * </pre>
 */
public enum AccountStatus {
    /**
     * 禁用
     */
    DISABLE(0),

    /**
     * 可用
     */
    ABLE(1)

    /**
     *标记删除
     */
    , DELETE(2);

    private int number;

    AccountStatus(int number) {
        this.number = number;
    }

    public static AccountStatus toAccountStatus(int number) {
        for (AccountStatus status : AccountStatus.values()) {
            if (number == status.getNumber()) {
                return status;
            }
        }
        return null;
    }

    public int getNumber() {
        return number;
    }
}
