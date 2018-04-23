package com.stadio.model.repository;

import com.stadio.model.documents.SSORefreshToken;

public interface SSORefreshTokenRepositoryCustom {

	SSORefreshToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);
}
