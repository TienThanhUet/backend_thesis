package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface IMovieService {

    ResponseResult processGetMovie(String tconst);

    ResponseResult topMovie();

    ResponseResult getMovieTypeHighlight(String type);

    ResponseResult listMovieType(Integer page, Integer pageSize,String type);

    ResponseResult calculateTotalScore();

}
