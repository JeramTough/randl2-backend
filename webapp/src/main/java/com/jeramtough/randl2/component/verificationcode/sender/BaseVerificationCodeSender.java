package com.jeramtough.randl2.component.verificationcode.sender;

import com.jeramtough.jtcomponent.utils.DateTimeUtil;
import com.jeramtough.jtlog.with.WithLogger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpSession;

/**
 * Created on 2018-09-17 13:02
 * by @author JeramTough
 */
public abstract class BaseVerificationCodeSender implements VerificationCodeSender,
        WithLogger {

    private static final String LAST_SENT_VERIFICATION_CODE_TIME_SESSION_KEY =
            "lastSentVerificationCodeTime";

    private HttpSession httpSession;
    private RedisTemplate<String,Object> redisTemplate;

    protected BaseVerificationCodeSender(HttpSession httpSession,
                                         RedisTemplate redisTemplate) {
        this.httpSession = this.httpSession;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean send(String verificationCode, boolean isTest) {
        boolean isSuccessful;
        if (isTest) {
            isSuccessful = true;
        }
        else {
            //sending option.
            isSuccessful = doSending(verificationCode);
        }

        //however true or false, saving the last sent verification code time to session
        httpSession.setAttribute(LAST_SENT_VERIFICATION_CODE_TIME_SESSION_KEY,
                System.currentTimeMillis());

        if (isSuccessful) {
            if (isTest) {
                getLogger().verbose(
                        "Sending the verification code【" + verificationCode + "】 successfully at " + DateTimeUtil.getCurrentDateTime());
            }
            else{
                getLogger().verbose(
                        "Sending the verification code successfully at " + DateTimeUtil.getCurrentDateTime());
            }
        }
        else {
            getLogger().warn("Sending the verification code unsuccessfully");
        }
        return isSuccessful;
    }

    @Override
    public int getLastSentVerificationCodeInterval() {
        Long lastSentVerificationCodeTime = (Long) httpSession.getAttribute(
                LAST_SENT_VERIFICATION_CODE_TIME_SESSION_KEY);
        if (lastSentVerificationCodeTime == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return Math.toIntExact(
                    (System.currentTimeMillis() - lastSentVerificationCodeTime) / 1000);
        }
    }

    public abstract boolean doSending(String verificationCode);

}
