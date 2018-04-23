package com.stadio.sso.custom;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SSOWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    public SSOWebResponseExceptionTranslator() {
        super();
    }

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception)this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return this.handleOAuth2Exception((OAuth2Exception)ase);
        } else {
            return super.translate(e);
        }
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || e instanceof InsufficientScopeException) {
            headers.set("WWW-Authenticate", String.format("%s %s", "Bearer", e.getSummary()));
        }
        SSOAuth2Exception ssoAuth2Exception = new SSOAuth2Exception(e.getMessage());
        ssoAuth2Exception.addAdditionalInformation("message",e.getMessage());
        ResponseEntity<OAuth2Exception> response = new ResponseEntity(ssoAuth2Exception, headers, HttpStatus.valueOf(status));
        return response;
    }

    @Override
    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        super.setThrowableAnalyzer(throwableAnalyzer);
    }
}
