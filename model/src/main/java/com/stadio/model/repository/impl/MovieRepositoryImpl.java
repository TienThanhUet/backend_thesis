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
    public List<Movie> topMovie() {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is("movie"));
        query.with(new Sort(Sort.Direction.DESC,"totalScore"));
        query.limit(25);

        List<Movie> movieList = mongoTemplate.find(query,Movie.class);

        return movieList;
    }

    @Override
    public List<Movie> highlightTypeMovie(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is("movie"));
        query.addCriteria(Criteria.where("genres").regex(type));
        query.with(new Sort(Sort.Direction.DESC,"startYear","totalScore"));
        query.limit(24);

        List<Movie> movieList = mongoTemplate.find(query,Movie.class);

        return movieList;
    }

    @Override
    public List<Movie> listMovieType(Integer page, Integer pageSize,String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is("movie"));
        query.addCriteria(Criteria.where("genres").regex(type));
        query.with(new Sort(Sort.Direction.DESC,"startYear","numVotes","averageRating"));
        final Pageable pageableRequest = new PageRequest(page - 1, pageSize);
        query.with(pageableRequest);

        List<Movie> movieList = mongoTemplate.find(query,Movie.class);

        return movieList;
    }

    @Override
    public long countMovieType(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is("movie"));
        query.addCriteria(Criteria.where("genres").regex(type));

        return mongoTemplate.count(query,Movie.class);
    }

    @Override
    public List<Movie> listTvShowType(Integer page, Integer pageSize, String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is(type));
        query.with(new Sort(Sort.Direction.DESC,"startYear","numVotes","averageRating"));
        final Pageable pageableRequest = new PageRequest(page - 1, pageSize);
        query.with(pageableRequest);

        List<Movie> movieList = mongoTemplate.find(query,Movie.class);

        return movieList;
    }

    @Override
    public long countTvShowType(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("titleType").is(type));

        return mongoTemplate.count(query,Movie.class);
    }
}
