package com.stadio.restapi.controllers;

import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    ISearchService searchService;

    @RequestMapping(value = "/movie",method = RequestMethod.GET)
    ResponseResult searchMovie(@RequestParam(value = "text") String text){
        return searchService.processSearchMovie(text);
    }
}
