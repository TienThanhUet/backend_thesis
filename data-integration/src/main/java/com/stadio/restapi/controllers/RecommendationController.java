package com.stadio.restapi.controllers;

import com.stadio.restapi.service.IMovieRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {

    @Autowired
    IMovieRecommendation movieRecommendation;

    @RequestMapping(value = "/calcutate-movie")
    public String calcutateMovieRecommendation(){
        movieRecommendation.calcutateMovieRecommendation();
        return "analyze done";
    }
}
