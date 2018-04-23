package com.stadio.sso.typedef;

public enum  AuthRequestKey {

    loginFacebook("login_facebook"),
    loginGoogle("login_google"),

    userType("user_type");

    private final String codeString;

    private AuthRequestKey(String codeString) {
        this.codeString = codeString;
    }

    public String toString() {
        //only override toString, if the returned value has a meaning for the
        //human viewing this value
        return String.valueOf(codeString);
    }
}
