package com.jeramtough.springtest;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.randl2.adminapp.Randl2AdminApplication;
import com.jeramtough.randl2.common.mapper.PermissionMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre>
 * Created on 2020/1/27 23:24
 * by @author JeramTough
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Randl2AdminApplication.class})// 指定启动类
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本
public class DaoTest {

    @Autowired
    PermissionMapper permissionMapper;

    @Test
    public void testOne(){
        L.debug(permissionMapper.selectListPermissionDto().size());
    }


    @Before
    public void testBefore(){
        System.out.println("before");
    }

    @After
    public void testAfter(){
        System.out.println("after");
    }
}
