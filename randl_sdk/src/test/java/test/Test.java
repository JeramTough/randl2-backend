package test;

import com.jeramtough.randl2.sdk.http.Oauth2AuthorizationCodeGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.http.Oauth2ClientCredentialsGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.http.Oauth2ImplicitGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.http.Oauth2PasswordGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.model.http.ApiResponse;
import com.jeramtough.randl2.sdk.model.http.TokenBody;
import com.jeramtough.randl2.sdk.model.oauth.Oauth2ClientConfig;

import java.io.IOException;

/**
 * <pre>
 * Created on 2020/11/24 0:55
 * by @author WeiBoWen
 * </pre>
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void test1() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("f46090e59cb5441691261db050f05415");
        oauth2ClientConfig.setClientSecret("YW1QTj1YMT1MiE5W5NjMYAxwN0zM5NN0GMAUMOTIiN2DRN0cjEwNMDTMZj2U");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/ssoServer/oauthV2/token");
        oauth2ClientConfig.setUserAuthorizationUri("http://127.0.0.1:9085/randl2/ssoServer/oauth/authorize");
//        oauth2ClientConfig.setSsoLoginUri("http://127.0.0.1:9085/randl2/ssoServer/access/goLoginPage");
//        oauth2ClientConfig.setRedirectUri("https://www.baidu.com");

        Oauth2PasswordGrantTypeHttpClient oauth2PasswordGrantTypeHttpClient =
                new Oauth2PasswordGrantTypeHttpClient(oauth2ClientConfig);
        try {
            TokenBody tokenBody = oauth2PasswordGrantTypeHttpClient.obtainTokenByPassword(
                    "15289678163",
                    "12345678");
            String refreshToken=tokenBody.getRefreshToken().getValue();

            //刷新模式
            //token=oauth2PasswordGrantTypeHttpClient.obtainTokenByRefreshToken(refreshToken);

            System.out.println(tokenBody.getTokenType()+" "+tokenBody.getToken());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    public void test2() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("f46090e59cb5441691261db050f05415");
        oauth2ClientConfig.setClientSecret("YW1QTj1YMT1MiE5W5NjMYAxwN0zM5NN0GMAUMOTIiN2DRN0cjEwNMDTMZj2U");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        oauth2ClientConfig.setUserAuthorizationUri("http://127.0.0.1:9085/randl2/ssoServer/oauth/authorize");
        oauth2ClientConfig.setSsoLoginUri("http://127.0.0.1:9085/randl2/ssoServer/access/goLoginPage");
        oauth2ClientConfig.setRedirectUri("https://www.baidu.com");

        Oauth2AuthorizationCodeGrantTypeHttpClient client =
                new Oauth2AuthorizationCodeGrantTypeHttpClient(oauth2ClientConfig);

        String loginUrl=client.getSsoLoginUrl("aaaaaaaaaaaalllllll");
        System.out.println("重定向到：" + loginUrl);

       /* //直接引导用户到登录页，登录页有token缓存的话直接访问Authorization页，不然先进行ssologin获取token
        String ssoLoginUrl =client.getSsoLoginUrl("state");
        System.out.println("请求转发到sso登录页：" + ssoLoginUrl);*/

        /*ApiResponse apiResponse = client.ssoLoginByPassword("15289678163", "12345678");
        String token = apiResponse.getResponseBody().getJSONObject("tokenBody").getString("value");*/



    }

    @org.junit.jupiter.api.Test
    public void test2_2() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("authorization-code-client");
        oauth2ClientConfig.setClientSecret("12345678");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        oauth2ClientConfig.setUserAuthorizationUri("http://127.0.0.1:9085/randl2/authserver/oauth/authorize");
        oauth2ClientConfig.setSsoLoginUri("http://127.0.0.1:9085/randl2/authserver/sso/login");
        oauth2ClientConfig.setRedirectUri("http://127.0.0.1:9070/randl2/client/authorized");

        Oauth2AuthorizationCodeGrantTypeHttpClient client =
                new Oauth2AuthorizationCodeGrantTypeHttpClient(oauth2ClientConfig);
        try {
            ApiResponse tokenBodyApiResponse = client.obtainTokenByAuthorizationCode("FG2TRg");
            System.out.println(tokenBodyApiResponse);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    public void test3() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("authorization-code-client");
        oauth2ClientConfig.setClientSecret("12345678");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        Oauth2ClientCredentialsGrantTypeHttpClient oauth2ClientCredentialsGrantTypeHttpClient=
                new Oauth2ClientCredentialsGrantTypeHttpClient(oauth2ClientConfig);

        try {
            ApiResponse token = oauth2ClientCredentialsGrantTypeHttpClient.ssoLoginByClientSecret();
            System.out.println(token);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    public void test4() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("authorization-code-client");
        oauth2ClientConfig.setClientSecret("12345678");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        oauth2ClientConfig.setUserAuthorizationUri("http://127.0.0.1:9085/randl2/authserver/oauth/authorize");
        oauth2ClientConfig.setSsoLoginUri("http://127.0.0.1:9085/randl2/authserver/sso/login");
        oauth2ClientConfig.setRedirectUri("http://127.0.0.1:9070/randl2/client/authorized");

        Oauth2ImplicitGrantTypeHttpClient client =
                new Oauth2ImplicitGrantTypeHttpClient(oauth2ClientConfig);
        try {
            ApiResponse apiResponse = client.ssoLoginByPassword("15289678163", "12345678");
            String token = apiResponse.getResponseBody().getJSONObject("tokenBody").getString("value");

            String url = client.getAuthorizeUrl(token, "abcd");

            System.out.println("重定向到：" + url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
