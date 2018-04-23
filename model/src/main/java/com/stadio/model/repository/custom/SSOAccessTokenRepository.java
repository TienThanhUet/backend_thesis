package com.stadio.model.repository.custom;

import com.stadio.model.documents.SSOAccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//@Repository
public interface SSOAccessTokenRepository extends MongoRepository<SSOAccessToken, String>, SSOAccessTokenRepositoryCustom {

    List<SSOAccessToken> findByUserIdAndClientIdAndIsExpired(String userId, String clientId, Boolean isExpired);

    SSOAccessToken findFirstByRefreshToken(String refreshTokenId);
}
