package com.jeramtough.test.db;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        AdminUserCredentials adminUserCredentials = new AdminUserCredentials("123", "2313");
        url =
                url + "?username=" + adminUserCredentials.getUsername() + "&password=" + adminUserCredentials.getPassword();

        RequestBody body = RequestBody.create(
                com.alibaba.fastjson.JSON.toJSONString(adminUserCredentials), JSON);

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

        String url = "https://www.gamersky.com/handbook/201806/1067140.shtml";

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Document document = Jsoup.parse(response.body().string());
//            L.debug(document.body());tring
            String title =
                    document.body().getElementsByClass("Mid2L_title").first().child(0).text();

            Element element = document.body().getElementsByClass("Mid2L_con").first();
            element.getElementsByClass("post_ding_top").remove();
//            L.debug(element);
            L.debug(document.head());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        String url = "https://www.gamersky.com/handbook/201806/1067140.shtml";
        Document document = Jsoup.parse(url);
        L.debug(document.body());
    }
}
