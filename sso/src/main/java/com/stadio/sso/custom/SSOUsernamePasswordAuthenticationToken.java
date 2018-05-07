package com.stadio.sso.custom;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;


public class SSOUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {


    private String grant_type;

    private String userId;

    private Boolean loginFacebook;

    private Boolean loginGoogle;

    @NotNull
    private String username;

    private String fullname;

    @NotNull
    private String password;

    private String email;

    private String facebookId;

    private String googleId;

    private String accessToken;

    public boolean isAuthenticated() {
        return true;
    }

    public SSOUsernamePasswordAuthenticationToken() {
        super(null, null);
    }

    public SSOUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SSOUsernamePasswordAuthenticationToken(Object principal, Object credentials, String grant_type, String userId, Boolean loginFacebook, Boolean loginGoogle, String username, String password, String phone, String email, String facebookId, String googleId, String accessToken) {
        super(principal, credentials);
        this.grant_type = grant_type;
        this.userId = userId;
        this.loginFacebook = loginFacebook;
        this.loginGoogle = loginGoogle;
        this.username = username;
        this.password = password;
        this.email = email;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.accessToken = accessToken;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    @Override
    public Object getCredentials() {
        return this.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return this.getUsername();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean getLoginFacebook() {
        return loginFacebook;
    }

    public void setLoginFacebook(String loginFacebook) {
        this.loginFacebook = "1".equals(loginFacebook);
    }

    public Boolean getLoginGoogle() {
        return loginGoogle;
    }

    public void setLoginGoogle(String loginGoogle) {
        this.loginGoogle = "1".equals(loginGoogle);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
