package com.stadio.model.repository.custom;

import com.stadio.model.documents.SSOAccessToken;

import java.util.List;

public interface SSOAccessTokenRepositoryCustom {

	SSOAccessToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);

    boolean deleteByRefreshTokenId(String refreshTokenId);

    SSOAccessToken findByAuthenticationId(String key);

    List<SSOAccessToken> findByUserIdAndClientId(String username, String clientId);


    List<SSOAccessToken> findByClientId(String clientId);
}
