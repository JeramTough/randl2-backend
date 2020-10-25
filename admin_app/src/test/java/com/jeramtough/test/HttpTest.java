package com.jeramtough.test;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.randl2.common.model.params.adminuser.UserCredentials;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

/**
 * <pre>
 * Created on 2020/2/5 17:35
 * by @author JeramTough
 * </pre>
 */
public class HttpTest {


    @Test
    public void test1() {

        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String url = "http://127.0.0.1:8088/randl2/adminUser/login";
        UserCredentials userCredentials = new UserCredentials("123", "2313");
        url =
                url + "?username=" + userCredentials.getUsername() + "&password=" + userCredentials.getPassword();

        RequestBody body = RequestBody.create(
                com.alibaba.fastjson.JSON.toJSONString(userCredentials), JSON);

        Request request = new Request.Builder()
                .post(okhttp3.internal.Util.EMPTY_REQUEST)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            L.debug(response.body().string());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url("http://222.216.111.49:8888/gcis/downLoadApp.html")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.debug(response.body());
            }
        });

    }

    @Test
    public void test3() {
        String url = "https://www.gamersky.com/handbook/201806/1067140.shtml";
    }
}
