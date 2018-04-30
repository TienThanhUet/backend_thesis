package com.stadio.model.repository;

import com.stadio.model.documents.MovieRecommend;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRecommedationRepository extends MongoRepository<MovieRecommend, String> {
    MovieRecommend findFirstByTconst(String tconst);
}
