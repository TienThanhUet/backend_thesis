package com.stadio.model.redisUtils.impl;

import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Movie;
import com.stadio.model.dtos.MovieItemDTO;
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
    public void ProcessPutTopMovieType(String type, List<Movie> movieList) {
        redisRepository.select(RedisConst.DB_MOVIE);
        String key = RedisConst.MOVIE_TOP+type;
        movieList.forEach(movie -> {
            redisRepository.hput(key,movie.getTconst(), JsonUtils.pretty(MovieItemDTO.with(movie)));
        });

        redisRepository.expire(key,RedisConst.TIME_TO_LIVE_SHORT);
    }

    @Override
    public Map<String, String> ProcessGetTopMovieType(String type) {
        redisRepository.select(RedisConst.DB_MOVIE);
        String key = RedisConst.MOVIE_TOP+type;
        Map<String, String > movieTopMap = redisRepository.hgetAll(key);
        return movieTopMap;
    }
}
