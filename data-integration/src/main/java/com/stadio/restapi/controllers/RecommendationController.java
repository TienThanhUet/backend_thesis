package com.stadio.restapi.controllers;

import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {

    @Autowired
    IMovieRecommendation movieRecommendationService;

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public ResponseResult recommendMovie(
            @RequestParam(value = "tconst") String tconst
    ){
        return movieRecommendationService.recommendMovie(tconst);
    }


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResponseResult recommendUser(
            @RequestHeader(value = "Authorization") String token
    ){
        return movieRecommendationService.recommendUser(token);
    }
}
