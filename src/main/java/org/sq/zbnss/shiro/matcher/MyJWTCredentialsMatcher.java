package org.sq.zbnss.shiro.matcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;
import org.sq.zbnss.uitl.JWTUtil;

@Slf4j
@Component
public class MyJWTCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws RuntimeException{
        BearerToken bearerToken = (BearerToken) token;
        String tokenString = bearerToken.getToken();
        return JWTUtil.verifyToken(tokenString);
    }
}
