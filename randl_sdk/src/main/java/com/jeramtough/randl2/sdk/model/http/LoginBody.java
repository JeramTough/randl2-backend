/**
  * Copyright 2020 bejson.com 
  */
package com.jeramtough.randl2.sdk.model.http;

/**
 * Auto-generated: 2020-11-25 23:36:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class LoginBody {

    private SystemUser systemUser;
    private TokenBody tokenBody;
    public void setSystemUser(SystemUser systemUser) {
         this.systemUser = systemUser;
     }
     public SystemUser getSystemUser() {
         return systemUser;
     }

    public void setTokenBody(TokenBody tokenBody) {
         this.tokenBody = tokenBody;
     }
     public TokenBody getTokenBody() {
         return tokenBody;
     }

}