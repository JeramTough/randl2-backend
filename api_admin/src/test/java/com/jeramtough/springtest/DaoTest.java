package com.jeramtough.springtest;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.randl2.adminapp.Randl2AdminApplication;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.personalinfo.AddPersonalInfoParams;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import com.jeramtough.randl2.service.randl.RandlPersonalInfoService;
import com.jeramtough.randl2.service.randl.RandlUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/1/27 23:24
 * by @author JeramTough
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Randl2AdminApplication.class})// 指定启动类
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本
public class DaoTest {

    @Autowired
    private RandlUserService randlUserService;

    @Autowired
    private RandlPersonalInfoService randlPersonalInfoService;

    @Test
    public void testOne() throws FileNotFoundException {
        File file = new File("/home/jeramtough/Temp/names.txt");
        List<RegisterRandlUserParams> params = new ArrayList<>();
        Map<String, String> nameMap = new HashMap<>();
        IoUtil.readLines(new FileInputStream(file), StandardCharsets.UTF_8, new LineHandler() {
            @Override
            public void handle(String line) {
                String[] texts = line.split("\\s+");
                RegisterRandlUserParams param = new RegisterRandlUserParams();
                param.setPhoneNumber(texts[1]);
                param.setAccount(texts[1]);
                param.setPassword("zgys1234");
                param.setRepeatedPassword("zgys1234");
                params.add(param);
                nameMap.put(texts[1], texts[0]);
            }
        });

        params.parallelStream()
                .forEach(registerRandlUserParams -> {
                    if (randlUserService.isExistByAccount(registerRandlUserParams.getPhoneNumber())){
                        randlUserService.deleteByAccount(registerRandlUserParams.getAccount());
                    }
                });

        for (RegisterRandlUserParams param : params) {
            RandlUserDto dto = randlUserService.add(param);
            AddPersonalInfoParams upParams = new AddPersonalInfoParams();
            upParams.setUid(dto.getUid());
            String realName = nameMap.get(param.getPhoneNumber());
            upParams.setRealname(realName);
            upParams.setNickname(realName);
            String result=randlPersonalInfoService.addPersonalInfo(upParams);
            L.debug(result);
        }

        L.arrive();
    }


    @Before
    public void testBefore() {
        System.out.println("before");
    }

    @After
    public void testAfter() {
        System.out.println("after");
    }
}
