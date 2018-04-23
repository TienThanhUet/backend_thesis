package com.stadio.model.repository;

import com.stadio.model.documents.MovieTfIdf;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieTfIdfRepository extends MongoRepository<MovieTfIdf, String> {

}
