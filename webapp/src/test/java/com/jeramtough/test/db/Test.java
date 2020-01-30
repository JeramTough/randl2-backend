package com.jeramtough.test.db;

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
    public void test1(){
        L.debug(Pattern.compile("^[a-z0-9A-Z]{8,16}$").matcher("userna").matches());
    }
}
