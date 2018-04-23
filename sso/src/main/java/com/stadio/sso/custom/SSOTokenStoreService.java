package com.stadio.sso.custom;

import com.stadio.model.documents.SSOAccessToken;
import com.stadio.model.documents.SSORefreshToken;
import com.stadio.model.repository.SSORefreshTokenRepository;
import com.stadio.model.repository.custom.SSOAccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SSOTokenStoreService {

    @Autowired
    private SSOAccessTokenRepository mongoSSOAccessTokenRepository;

    @Autowired
    private SSORefreshTokenRepository mongoSSORefreshTokenRepository;


    public void setExpiredAccessTokenForUserIdAndUserTypeAndClientId(String userId, String clientId) {
        List<SSOAccessToken> ssoAccessTokens = mongoSSOAccessTokenRepository.
                findByUserIdAndClientIdAndIsExpired(userId , clientId, false);
        for (SSOAccessToken accessToken: ssoAccessTokens) {
            mongoSSOAccessTokenRepository.delete(accessToken);
        }
    }

    public void removeAccessToken(String tokenKey) {
        SSOAccessToken ssoAccessToken = mongoSSOAccessTokenRepository.findByTokenId(tokenKey);
        if (ssoAccessToken != null) {
            mongoSSOAccessTokenRepository.delete(ssoAccessToken);
        }

    }

    public void removeRefreshToken(String tokenKey) {
        SSORefreshToken ssoRefreshToken = mongoSSORefreshTokenRepository.findByTokenId(tokenKey);
        if (ssoRefreshToken != null) {
            mongoSSORefreshTokenRepository.delete(ssoRefreshToken);
        }
    }
}
