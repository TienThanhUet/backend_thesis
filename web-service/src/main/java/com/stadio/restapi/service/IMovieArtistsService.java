package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface IMovieArtistsService {
    ResponseResult listArtistOfMovie(String tconst);
}
