package com.stadio.sso.custom;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class SSOAuth2Exception extends OAuth2Exception {

    public SSOAuth2Exception(String msg) {
        super(msg);
    }

}
