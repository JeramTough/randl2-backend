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
    public void test1() {
        String.format("【系统公共错误码】[%s]传参违背规则，应为[%s]！", "a", "d");
    }
}
