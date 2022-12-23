package com.jeramtough.randl2.adminapp.component.attestation.request;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Created on 2022/12/23 下午4:14
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class RequestSelector {

    private final List<JtAuthorizationRequest> jtAuthorizationRequestList = new ArrayList<>();


    public RequestSelector() {
        jtAuthorizationRequestList.add(new OpenJtAuthorizationRequest());
        jtAuthorizationRequestList.add(new DefaultJtAuthorizationRequest());
    }

    public JtAuthorizationRequest select(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        requestUri = requestUri.replaceFirst(request.getContextPath(), "");

        JtAuthorizationRequest selectedJtAuthorizationRequest = null;
        for (JtAuthorizationRequest authorizationRequest : jtAuthorizationRequestList) {
            boolean is = authorizationRequest.is(requestUri);
            if (is) {
                selectedJtAuthorizationRequest = authorizationRequest;
                break;
            }
        }

        Objects.requireNonNull(selectedJtAuthorizationRequest);
        return selectedJtAuthorizationRequest;

    }

}
