package com.jeramtough.randl2.component.verificationcode.sender;

import com.jeramtough.randl2.component.verificationcode.sender.SendWay;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/2/16 21:18
 * by @author JeramTough
 * </pre>
 */
public class VerificationCodeSenderGetter {

    public static VerificationCodeSender getSender(WebApplicationContext webApplicationContext,
                                                   int way) {
        VerificationCodeSender sender = null;
        switch (SendWay.getSendWay(way)) {
            case PHONE:
                sender = webApplicationContext.getBean(SmsVerificationCodeSender.class);
                break;
            case EMAIL:
                sender = webApplicationContext.getBean(EmailVerificationCodeSender.class);
                break;
            default:
        }
        return sender;
    }
}

