package com.stadio.model.repository;

import com.stadio.model.documents.Movie;
import com.stadio.model.repository.custom.MovieRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie,String> , MovieRepositoryCustom {
        List<Movie> findByTconst(String tconst);
}
