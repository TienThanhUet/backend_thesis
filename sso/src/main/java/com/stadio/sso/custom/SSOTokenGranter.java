package com.stadio.sso.custom;

import com.stadio.common.utils.JsonMapperValidate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class SSOTokenGranter extends ResourceOwnerPasswordTokenGranter {

    private static final String GRANT_TYPE = "password";

    private final AuthenticationManager authenticationManager;

    private final JsonMapperValidate jsonMapperValidate;


    public SSOTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory);
        this.authenticationManager = authenticationManager;
        this.jsonMapperValidate = new JsonMapperValidate();
    }

    protected SSOTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
        this.jsonMapperValidate = new JsonMapperValidate();
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = tokenRequest.getRequestParameters();

        //Parse request
        Authentication ssoUserAuth;

        try {
            ssoUserAuth = this.jsonMapperValidate.read(parameters, SSOUsernamePasswordAuthenticationToken.class);
        } catch (Exception e) {
            throw new InvalidGrantException("Sai param truyen len");
        }

        Authentication userAuth;
        try {
            userAuth = authenticationManager.authenticate(ssoUserAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/bad grant
            throw new InvalidGrantException(e.getMessage());
        }

        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Không thể đăng nhập");
        }

        if (userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Không thể đăng nhập");
        }
    }
}