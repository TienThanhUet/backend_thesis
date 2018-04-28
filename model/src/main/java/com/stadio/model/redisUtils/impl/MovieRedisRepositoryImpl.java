package com.stadio.model.redisUtils.impl;

import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Movie;
import com.stadio.model.dtos.MovieDetailsDTO;
import com.stadio.model.dtos.MovieItemDTO;
import com.stadio.model.redisUtils.MovieRedisRepository;
import com.stadio.model.redisUtils.RedisConst;
import com.stadio.model.redisUtils.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MovieRedisRepositoryImpl implements MovieRedisRepository {

    @Autowired
    RedisRepository redisRepository;

    @Override
    public void processPutMovie(Movie movie) {
        redisRepository.select(RedisConst.DB_MOVIE);
        String key = RedisConst.MOVIE_DETAILS;
        redisRepository.hput(key, movie.getTconst(), JsonUtils.pretty(MovieDetailsDTO.newInstance(movie)));
        redisRepository.expire(key,RedisConst.TIME_TO_LIVE_SHORT);
    }

    @Override
    public void processPutAllMovie(List<Movie> movieList) {
        redisRepository.select(RedisConst.DB_MOVIE);
        String key = RedisConst.MOVIE_DETAILS;
        movieList.forEach(movie -> {
            redisRepository.hput(key, movie.getTconst(), JsonUtils.pretty(MovieDetailsDTO.newInstance(movie)));
        });
        redisRepository.expire(key,RedisConst.TIME_TO_LIVE_SHORT);
    }

    @Override
    public void processDeleteMovie(String tconst) {
        redisRepository.select(RedisConst.DB_MOVIE);
        redisRepository.hdelete(RedisConst.MOVIE_DETAILS, tconst);
    }

    @Override
    public Map<String, String> processGetAllMovie() {
        redisRepository.select(RedisConst.DB_MOVIE);
        Map<String, String> movieMap = redisRepository.hgetAll(RedisConst.MOVIE_DETAILS);
        return movieMap;
    }

    @Override
    public String processGetMovie(String tconst) {
        redisRepository.select(RedisConst.DB_MOVIE);
        String movieStr = redisRepository.hget(RedisConst.MOVIE_DETAILS, tconst);
        return movieStr;
    }
}
