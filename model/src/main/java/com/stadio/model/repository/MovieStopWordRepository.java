package com.stadio.model.repository;

import com.stadio.model.documents.MovieStopWord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieStopWordRepository extends MongoRepository<MovieStopWord, String> {
    MovieStopWord findFirstByTconst(String tconst);
}
