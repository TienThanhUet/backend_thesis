package com.stadio.model.redisUtils.impl;

import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Movie;
import com.stadio.model.dtos.MovieDetailsDTO;
import com.stadio.model.redisUtils.MovieTopRedisRepository;
import com.stadio.model.redisUtils.RedisConst;
import com.stadio.model.redisUtils.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MovieTopRedisRepositoryImpl implements MovieTopRedisRepository {

    @Autowired
    RedisRepository redisRepository;

    @Override
    public void processPutTopMovie( List<Movie> movieList) {
        redisRepository.select(RedisConst.DB_MOVIE);
        String key = RedisConst.MOVIE_TOP;
        movieList.forEach(movie -> {
            redisRepository.hput(key,movie.getTconst(), JsonUtils.pretty(MovieDetailsDTO.newInstance(movie)));
        });

        redisRepository.expire(key,RedisConst.TIME_TO_LIVE_SHORT);
    }

    @Override
    public Map<String, String> processGetTopMovie() {
        redisRepository.select(RedisConst.DB_MOVIE);
        String key = RedisConst.MOVIE_TOP;
        Map<String, String > movieTopMap = redisRepository.hgetAll(key);
        return movieTopMap;
    }
}
