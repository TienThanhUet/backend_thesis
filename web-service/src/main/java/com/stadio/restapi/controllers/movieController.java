package com.stadio.restapi.controllers;

import com.stadio.restapi.service.ICommentService;
import com.stadio.restapi.service.IMovieArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieService;

@RestController
@RequestMapping(value = "/movie")
public class movieController {

    @Autowired
    IMovieService movieService;

    @Autowired
    IMovieArtistsService movieArtistsService;

    @Autowired
    ICommentService commentService;

    @RequestMapping(value = "/details",method = RequestMethod.GET)
    public ResponseResult details(@RequestParam(value = "tconst") String tconst){
        return movieService.processGetMovie(tconst);
    }

    @RequestMapping(value = "movie-artists")
    public ResponseResult listArtistOfMovie(@RequestParam(value = "tconst") String tconst){
        return movieArtistsService.listArtistOfMovie(tconst);
    }

    @RequestMapping(value = "/movie-type/top",method = RequestMethod.GET)
    public ResponseResult topMovieType(
            @RequestParam(value = "type") String type
    ){
        return movieService.topMovieType(type);
    }

    @RequestMapping(value = "/movie-type/list",method = RequestMethod.GET)
    public ResponseResult  listMovieType (
            @RequestParam(value = "type") String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
    ){
        return movieService.listMovieType(page,pageSize,type);
    }

    //comment
    @RequestMapping(value = "/list-comments",method = RequestMethod.GET)
    public ResponseResult listComments(
        @RequestParam(value = "tconst") String tconst
    ){
        return commentService.getListComment(tconst);
    }
}
