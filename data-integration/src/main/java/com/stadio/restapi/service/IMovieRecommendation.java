package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface IMovieRecommendation {

    ResponseResult recommendMovie(String tconst);

    ResponseResult recommendUser(String token);
}
