package com.stadio.sso.custom;


import com.stadio.model.documents.SSOAccessToken;
import com.stadio.model.documents.SSORefreshToken;
import com.stadio.model.repository.custom.SSOAccessTokenRepository;
import com.stadio.model.repository.SSORefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.common.util.SerializationUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

@Component
public class SSOTokenStore implements TokenStore {

    private final SSOAccessTokenRepository mongoSSOAccessTokenRepository;

    private final SSORefreshTokenRepository mongoSSORefreshTokenRepository;

    private final AuthenticationKeyGenerator authenticationKeyGenerator;

    @Autowired
    private SSOTokenStoreService ssoTokenStoreService;

    public SSOTokenStore(final SSOAccessTokenRepository mongoSSOAccessTokenRepository,
                           final SSORefreshTokenRepository mongoSSORefreshTokenRepository,
                           final AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.mongoSSOAccessTokenRepository = mongoSSOAccessTokenRepository;
        this.mongoSSORefreshTokenRepository = mongoSSORefreshTokenRepository;
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<SSOAccessToken> oAuth2AccessTokens = mongoSSOAccessTokenRepository.findByClientId(clientId);
        Collection<SSOAccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNullsAndUnExpired());
        return transform(noNullTokens, toOAuth2AccessToken());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        //userName is userId from SSOAuthenticationManger, to
        List<SSOAccessToken> oAuth2AccessTokens = mongoSSOAccessTokenRepository.findByUserIdAndClientId(userName, clientId);
        Collection<SSOAccessToken> noNullsTokens = filter(oAuth2AccessTokens, byNotNullsAndUnExpired());
        return transform(noNullsTokens, toOAuth2AccessToken());
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        //create new token each time login
        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken( String tokenValue) {
        String tokenKey = extractTokenKey(tokenValue);
        SSOAccessToken mongoOAuth2AccessToken = mongoSSOAccessTokenRepository.findByTokenId(tokenKey);
        if (mongoOAuth2AccessToken != null && mongoOAuth2AccessToken.getIsExpired() == false) {
            try {
                return deserializeAccessToken(mongoOAuth2AccessToken.getTokenSerialized());
            } catch (IllegalArgumentException e) {
                removeAccessToken(tokenValue);
            }
        }

        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication( OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication( String token) {
        String tokenId = extractTokenKey(token);
        SSOAccessToken mongoOAuth2AccessToken = mongoSSOAccessTokenRepository.findByTokenId(tokenId);
        if (mongoOAuth2AccessToken != null && mongoOAuth2AccessToken.getIsExpired() == false) {
            try {
                return deserializeAuthentication(mongoOAuth2AccessToken.getAuthentication());
            } catch (IllegalArgumentException e) {
                removeAccessToken(token);
            }
        }

        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        String tokenKey = extractTokenKey(tokenValue);
        SSORefreshToken mongoOAuth2RefreshToken = mongoSSORefreshTokenRepository.findByTokenId(tokenKey);
        if (mongoOAuth2RefreshToken != null) {
            try {
                return deserializeRefreshToken(mongoOAuth2RefreshToken.getToken());
            } catch (IllegalArgumentException e) {
                removeRefreshToken(tokenValue);
            }
        }

        return null;
    }

    @Override
    public void removeAccessToken( OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken( OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    @Override
    public void removeRefreshToken( OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    @Override
    public void storeAccessToken( OAuth2AccessToken token,  OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        SSOUsernamePasswordAuthenticationToken authRequuest = (SSOUsernamePasswordAuthenticationToken )authentication.getUserAuthentication();
//        String userType = authRequuest.getUserType();
//        String userType = authentication.getOAuth2Request().getRequestParameters().get(AuthRequestKey.userType.toString());

        ssoTokenStoreService.setExpiredAccessTokenForUserIdAndUserTypeAndClientId(authRequuest.getUserId(),authentication.getOAuth2Request().getClientId());

        String tokenKey = extractTokenKey(token.getValue());

        SSOAccessToken oAuth2AccessToken = new SSOAccessToken(tokenKey,
                serializeAccessToken(token),
                token.getValue(),
                authenticationKeyGenerator.extractKey(authentication),
                authentication.isClientOnly() ? null : authRequuest.getUserId(),
                authentication.getOAuth2Request().getClientId(),
                serializeAuthentication(authentication),
                extractTokenKey(refreshToken));

        mongoSSOAccessTokenRepository.save(oAuth2AccessToken);
    }

    @Override
    public void storeRefreshToken( OAuth2RefreshToken refreshToken, OAuth2Authentication oAuth2Authentication) {
        String tokenKey = extractTokenKey(refreshToken.getValue());
        byte[] token = serializeRefreshToken(refreshToken);
        byte[] authentication = serializeAuthentication(oAuth2Authentication);

        SSORefreshToken oAuth2RefreshToken = new SSORefreshToken(tokenKey, token, authentication);

        mongoSSORefreshTokenRepository.save(oAuth2RefreshToken);

    }

    public void removeAccessToken( String tokenValue) {
        String tokenKey = extractTokenKey(tokenValue);
        ssoTokenStoreService.removeAccessToken(tokenKey);
    }

    private Predicate<SSOAccessToken> byNotNulls() {
        return new Predicate<SSOAccessToken>() {
            @Override
            public boolean apply( SSOAccessToken token) {
                return token != null;
            }
        };
    }

    private Predicate<SSOAccessToken> byNotNullsAndUnExpired() {
        return new Predicate<SSOAccessToken>() {
            @Override
            public boolean apply( SSOAccessToken token) {
                return token != null && token.getIsExpired() == false;
            }
        };
    }

    private Function<SSOAccessToken, OAuth2AccessToken> toOAuth2AccessToken() {
        return new Function<SSOAccessToken, OAuth2AccessToken>() {
            @Override
            public OAuth2AccessToken apply(SSOAccessToken token) {
                return SerializationUtils.deserialize(token.getTokenSerialized());
            }
        };
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }


    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return SerializationUtils.deserialize(authentication);
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        String tokenId = extractTokenKey(value);

        SSORefreshToken mongoOAuth2RefreshToken = mongoSSORefreshTokenRepository.findByTokenId(tokenId);

        if (mongoOAuth2RefreshToken != null) {
            try {
                return deserializeAuthentication(mongoOAuth2RefreshToken.getAuthentication());
            } catch (IllegalArgumentException e) {
                removeRefreshToken(value);
            }
        }
        return null;
    }

    public void removeRefreshToken(String token) {
        String tokenId = extractTokenKey(token);
        ssoTokenStoreService.removeRefreshToken(tokenId);
    }

    public void removeAccessTokenUsingRefreshToken( String refreshToken) {
        String tokenId = extractTokenKey(refreshToken);
        SSOAccessToken ssoAccessToken = mongoSSOAccessTokenRepository.findByTokenId(tokenId);
        ssoAccessToken.setIsExpired(true);
        mongoSSOAccessTokenRepository.save(ssoAccessToken);

    }
}
