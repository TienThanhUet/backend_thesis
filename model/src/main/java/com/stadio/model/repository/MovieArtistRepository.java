package com.stadio.model.repository;

import com.stadio.model.documents.MovieArtist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface MovieArtistRepository extends MongoRepository<MovieArtist, String> {

    List<MovieArtist> findByTconst(String tconst);

    List<MovieArtist> findByTconstAndCategoryIn(String tconst,Collection<String> categorys);
}
