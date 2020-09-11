package com.jeramtough.test.db;

import com.jeramtough.jtcomponent.key.util.KeyUtil;
import com.jeramtough.jtlog.facade.L;

import java.util.regex.Pattern;

/**
 * <pre>
 * Created on 2020/1/31 0:30
 * by @author JeramTough
 * </pre>
 */
public class Test {

    @org.junit.Test
    public void test1() {
        String returnString = "dfsfsdf";
        String a = "a";
        String b = "a";
        String c = new String("a");
        L.debugs(a == c, a.equals(c));
    }

    @org.junit.Test
    public void test3() {
        L.debugs(KeyUtil.encryptByMD5("12345678".getBytes()));
    }
}
