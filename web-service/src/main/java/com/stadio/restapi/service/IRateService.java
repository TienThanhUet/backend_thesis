package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface IRateService {
    ResponseResult getRate(String tconst, String token);

    ResponseResult addRate(String tconst,Integer score, String token);

    ResponseResult deleteRate(String tconst, String token);
}
