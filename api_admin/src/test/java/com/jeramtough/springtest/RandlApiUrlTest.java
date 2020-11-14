package com.jeramtough.springtest;

import com.jeramtough.randl2.adminapp.Randl2AdminApplication;
import com.jeramtough.randl2.common.mapper.RandlModuleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre>
 * Created on 2020/1/27 23:24
 * by @author JeramTough
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Randl2AdminApplication.class})// 指定启动类
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本
public class RandlApiUrlTest {

    @Autowired
    RandlModuleMapper moduleMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void insertSystemApi() {
//        apiMapper.delete();
        /*List<SystemApi> apiList = ApiUrlUtil.getAll();
        for (SystemApi api : apiList) {
            SystemApi hasSystemApi = moduleMapper.selectOne(new QueryWrapper<SystemApi>().eq("path",
                    api.getPath()));
            if (hasSystemApi != null) {
                api.setFid(hasSystemApi.getFid());
                moduleMapper.updateById(api);
            }
            else {
                moduleMapper.insert(api);
            }
        }*/
    }


}
