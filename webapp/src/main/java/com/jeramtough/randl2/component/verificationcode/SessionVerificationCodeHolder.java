package com.jeramtough.randl2.component.verificationcode;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.LinkedList;

/**
 * <pre>
 * Created on 2020/2/16 22:30
 * by @author JeramTough
 * </pre>
 */
@Component
public class SessionVerificationCodeHolder implements VerificationCodeHolder {

    private static final String VERIFICATION_CODES_KEY =
            SessionVerificationCodeHolder.class.getName() + "CODE";
    private static final String IS_PASSED_KEY =
            SessionVerificationCodeHolder.class.getName() + "IS_PASSED";
    private static final String SEND_WAY_VALUE_KEY =
            SessionVerificationCodeHolder.class.getName() + "SEND_WAY";
    private static final int MAX_CAPACITY = 3;

    private final HttpSession session;


    @Autowired
    public SessionVerificationCodeHolder(HttpSession session) {
        this.session = session;
    }

    @Override
    public boolean verifyCode(String verificationCode) {
        LinkedList<String> verificationCodeList = getRightVerificationCodes();
        boolean isPassed = false;
        for (String rightVerificationCode : verificationCodeList) {
            if (rightVerificationCode.equalsIgnoreCase(verificationCode)) {
                isPassed = true;
                break;
            }
        }
        session.setAttribute(IS_PASSED_KEY, isPassed);
        return isPassed;
    }


    @Override
    public String getAndRecordVerificationCode(String phoneOrEmail) {
        String verificationCode = RandomStringUtils.random(6, false, true);
        LinkedList<String> verificationCodeList = getRightVerificationCodes();
        if (verificationCodeList.size() == MAX_CAPACITY) {
            verificationCodeList.removeFirst();
        }
        verificationCodeList.add(verificationCode);
        setRightVerificationCodes(verificationCodeList);
        session.setAttribute(SEND_WAY_VALUE_KEY, phoneOrEmail);
        return verificationCode;
    }

    @Override
    public VerificationResult getVerificationResult() {
        Object o = session.getAttribute(IS_PASSED_KEY);
        Object oo = session.getAttribute(SEND_WAY_VALUE_KEY);

        boolean isPassed;
        String sendWayValue;
        if (o == null) {
            isPassed = false;
        }
        else {
            isPassed = (boolean) o;
        }
        if (oo == null) {
            sendWayValue = "";
        }
        else {
            sendWayValue = (String) oo;
        }
        VerificationResult verificationResult = new VerificationResult();
        verificationResult.setPassed(isPassed);
        verificationResult.setSendWayValue(sendWayValue);
        return verificationResult;
    }

    //***************

    private LinkedList<String> getRightVerificationCodes() {
        Object o = session.getAttribute(VERIFICATION_CODES_KEY);
        if (o == null) {
            return new LinkedList<>();
        }
        else {
            String[] verificationCodes = (String[]) o;
            LinkedList<String> list = new LinkedList<>();
            Collections.addAll(list, verificationCodes);
            return list;
        }
    }

    private void setRightVerificationCodes(LinkedList<String> list) {
        String[] verificationCodes = list.toArray(new String[0]);
        session.setAttribute(VERIFICATION_CODES_KEY, verificationCodes);
    }

    //{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}

    public class VerificationResult {

        private String sendWayValue;

        private boolean isPassed;

        public String getSendWayValue() {
            return sendWayValue;
        }

        public void setSendWayValue(String sendWayValue) {
            this.sendWayValue = sendWayValue;
        }

        public boolean isPassed() {
            return isPassed;
        }

        public void setPassed(boolean passed) {
            isPassed = passed;
        }
    }
}
