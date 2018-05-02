package com.stadio.model.redisUtils;

import com.stadio.model.documents.Movie;

import java.util.List;
import java.util.Map;

public interface MovieTopRedisRepository {

    void processPutTopMovie(List<Movie> movieList);

    Map<String,String> processGetTopMovie();
}
