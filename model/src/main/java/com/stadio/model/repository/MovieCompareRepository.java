package com.stadio.model.repository;

import com.stadio.model.documents.MovieCompare;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieCompareRepository extends MongoRepository<MovieCompare, String> {
    MovieCompare findFirstByTconst1AndTconst2(String tconst1,String tconst2);
}
