package test;

import com.jeramtough.randl2.sdk.http.Oauth2AuthorizationCodeGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.http.Oauth2ClientCredentialsGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.http.Oauth2ImplicitGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.http.Oauth2PasswordGrantTypeHttpClient;
import com.jeramtough.randl2.sdk.model.http.ApiResponse;
import com.jeramtough.randl2.sdk.model.http.LoginBody;
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
        oauth2ClientConfig.setClientId("authorization-code-client");
        oauth2ClientConfig.setClientSecret("12345678");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        Oauth2PasswordGrantTypeHttpClient oauth2PasswordGrantTypeHttpClient =
                new Oauth2PasswordGrantTypeHttpClient(oauth2ClientConfig);
        try {
            ApiResponse token = oauth2PasswordGrantTypeHttpClient.obtainTokenByPassword("15289678163", "12345678");
            String refreshToken=token.getResponseBody().getJSONObject("refreshToken").getString("value");

            token=oauth2PasswordGrantTypeHttpClient.obtainTokenByRefreshToken(refreshToken);
            System.out.println(token);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    public void test2_1() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("authorization-code-client");
        oauth2ClientConfig.setClientSecret("12345678");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        oauth2ClientConfig.setUserAuthorizationUri("http://127.0.0.1:9085/randl2/authserver/oauth/authorize");
        oauth2ClientConfig.setSsoLoginUrl("http://127.0.0.1:9085/randl2/authserver/sso/login");
        oauth2ClientConfig.setRedirectUrisUrl("http://127.0.0.1:9070/randl2/client/authorized");

        Oauth2AuthorizationCodeGrantTypeHttpClient client =
                new Oauth2AuthorizationCodeGrantTypeHttpClient(oauth2ClientConfig);
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

    @org.junit.jupiter.api.Test
    public void test2_2() {
        Oauth2ClientConfig oauth2ClientConfig = new Oauth2ClientConfig();
        oauth2ClientConfig.setClientId("authorization-code-client");
        oauth2ClientConfig.setClientSecret("12345678");
        oauth2ClientConfig.setAccessTokenUri("http://127.0.0.1:9085/randl2/authserver/oauthV2/token");
        oauth2ClientConfig.setUserAuthorizationUri("http://127.0.0.1:9085/randl2/authserver/oauth/authorize");
        oauth2ClientConfig.setSsoLoginUrl("http://127.0.0.1:9085/randl2/authserver/sso/login");
        oauth2ClientConfig.setRedirectUrisUrl("http://127.0.0.1:9070/randl2/client/authorized");

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
        oauth2ClientConfig.setSsoLoginUrl("http://127.0.0.1:9085/randl2/authserver/sso/login");
        oauth2ClientConfig.setRedirectUrisUrl("http://127.0.0.1:9070/randl2/client/authorized");

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
