package com.stadio.model.repository.impl;

import com.stadio.model.documents.Movie;
import com.stadio.model.repository.custom.MovieRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public List<Movie> topTypeMovie(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is("movie"));
        query.addCriteria(Criteria.where("genres").regex(type).orOperator(
                Criteria.where("region").is("US"),Criteria.where("region").is("GB")
        ));
        query.with(new Sort(Sort.Direction.DESC,"numVotes"));
        query.limit(25);

        List<Movie> movieList = mongoTemplate.find(query,Movie.class);

        return movieList;
    }

    @Override
    public List<Movie> listMovieType(Integer page, Integer pageSize,String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genres").regex(type));
        query.with(new Sort(Sort.Direction.DESC,"startYear","averageRating"));
        final Pageable pageableRequest = new PageRequest(page - 1, pageSize);
        query.with(pageableRequest);

        List<Movie> movieList = mongoTemplate.find(query,Movie.class);

        return movieList;
    }

    @Override
    public List<Movie> listMovie(int page, int pageSize) {
        return null;
    }
}
