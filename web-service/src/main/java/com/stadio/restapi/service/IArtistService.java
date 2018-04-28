package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface IArtistService {
    ResponseResult ProcessGetDetails(String nconst);

    ResponseResult knownForTitles(String knownForTitles);
}
