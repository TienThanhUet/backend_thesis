package com.stadio.model.repository;

import com.stadio.model.documents.Rate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RateRepository extends MongoRepository<Rate,String> {
    Rate findFirstByTconstAndUserId(String tconst,String UserId);

    void deleteByTconstAndUserId(String tconst,String UserId);
}
