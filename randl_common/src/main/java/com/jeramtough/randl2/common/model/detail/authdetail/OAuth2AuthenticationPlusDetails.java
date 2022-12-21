package com.jeramtough.randl2.common.model.detail.authdetail;

import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2021/8/12 下午3:04
 * by @author WeiBoWen
 * </pre>
 */public class OAuth2AuthenticationPlusDetails extends OAuth2AuthenticationDetails {

     private final MyUserDetails myUserDetails;
     private final MyClientDetails myClientDetails;

     /**
     * Records the access token value and remote address and will also set the session Id if a session already exists
     * (it won't create one).
     *
      * @param request that the authentication request was received from
      * @param myUserDetails
      * @param myClientDetails
      */
    public OAuth2AuthenticationPlusDetails(HttpServletRequest request,
                                           MyUserDetails myUserDetails,
                                           MyClientDetails myClientDetails) {
        super(request);
        this.myUserDetails = myUserDetails;
        this.myClientDetails = myClientDetails;
    }

    public MyUserDetails getMyUserDetails() {
        return myUserDetails;
    }

    public MyClientDetails getMyClientDetails() {
        return myClientDetails;
    }
}
