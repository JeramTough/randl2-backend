package com.jeramtough.test;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtcomponent.key.util.KeyUtil;
import com.jeramtough.jtlog.facade.L;

import java.util.ArrayList;
import java.util.List;
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
        JSON.parseArray("fasfsa");
    }


    @org.junit.Test
    public void test3() {
        L.debugs(KeyUtil.encryptByMD5("12345678".getBytes()));
    }
}
