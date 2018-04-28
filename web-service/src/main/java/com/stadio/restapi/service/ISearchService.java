package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface ISearchService {
    ResponseResult processSearchMovie(String text);
}
