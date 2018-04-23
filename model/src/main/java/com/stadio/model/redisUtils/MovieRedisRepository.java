package com.stadio.model.redisUtils;

import com.stadio.model.documents.Movie;

import java.util.List;
import java.util.Map;

public interface MovieRedisRepository {
    void processPutMovie(Movie movie);

    void processPutAllMovie(List<Movie> movieList);

    void processDeleteMovie(String tconst);

    Map<String, String> processGetAllMovie();

    String processGetMovie(String tconst);
}
