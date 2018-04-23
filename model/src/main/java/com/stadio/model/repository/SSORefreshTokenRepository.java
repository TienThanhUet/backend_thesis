package com.stadio.model.repository;

import com.stadio.model.documents.SSORefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SSORefreshTokenRepository extends MongoRepository<SSORefreshToken, String>, SSORefreshTokenRepositoryCustom  {

}
