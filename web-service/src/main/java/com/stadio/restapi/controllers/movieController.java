package com.stadio.restapi.controllers;

import com.stadio.restapi.service.ICommentService;
import com.stadio.restapi.service.IMovieArtistsService;
import com.stadio.restapi.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/movie")
public class movieController {

    @Autowired
    IMovieService movieService;

    @Autowired
    IMovieArtistsService movieArtistsService;

    @Autowired
    ICommentService commentService;

    @Autowired
    IRateService rateService;

    @RequestMapping(value = "/details",method = RequestMethod.GET)
    public ResponseResult details(@RequestParam(value = "tconst") String tconst){
        return movieService.processGetMovie(tconst);
    }

    @RequestMapping(value = "movie-artists")
    public ResponseResult listArtistOfMovie(@RequestParam(value = "tconst") String tconst){
        return movieArtistsService.listArtistOfMovie(tconst);
    }

    @RequestMapping(value = "/top",method = RequestMethod.GET)
    public ResponseResult getTopMovie(
    ){
        return movieService.topMovie();
    }

    @RequestMapping(value = "/movie-type/highlight",method = RequestMethod.GET)
    public ResponseResult getMovieTypeHighlight(
            @RequestParam(value = "type") String type
    ){
        return movieService.getMovieTypeHighlight(type);
    }

    @RequestMapping(value = "/movie-type/list",method = RequestMethod.GET)
    public ResponseResult  getListMovieType (
            HttpServletRequest request,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
    ){
        return movieService.listMovieType(page,pageSize,type,this.requestURI(request));
    }

    @RequestMapping(value = "/tv-show/list",method = RequestMethod.GET)
    public ResponseResult  getListTvShowType (
            HttpServletRequest request,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
    ){
        return movieService.listTvShowType(page,pageSize,type,this.requestURI(request));
    }

    //comment
    @RequestMapping(value = "/comments/list",method = RequestMethod.GET)
    public ResponseResult getListComments(
        @RequestParam(value = "tconst") String tconst,
        @RequestHeader(value = "Authorization") String token
    ){
        return commentService.getListComment(tconst,token);
    }

    @RequestMapping(value = "/comments/add",method = RequestMethod.POST)
    public ResponseResult addComment(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "tconst") String tconst,
            @RequestParam(value = "content") String content
    ){
        return commentService.addComment(tconst,content,token);
    }


    //rating

    @RequestMapping(value = "/rate/get",method = RequestMethod.GET)
    public ResponseResult getRate(
            @RequestParam(value = "tconst") String tconst,
            @RequestHeader(value = "Authorization") String token
    ){
        return rateService.getRate(tconst,token);
    }

    @RequestMapping(value = "/rate/add",method = RequestMethod.POST)
    public ResponseResult addRate(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "tconst") String tconst,
            @RequestParam(value = "score") Integer score
    ){
        return rateService.addRate(tconst,score,token);
    }

    @RequestMapping(value = "/rate/delete",method = RequestMethod.GET)
    public ResponseResult deleteRate(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "tconst") String tconst
    ){
        return rateService.deleteRate(tconst,token);
    }



    //totalScore
    @RequestMapping(value = "/calculate-totalScore",method = RequestMethod.GET)
    public ResponseResult calculateTotalScore(){
        return movieService.calculateTotalScore();
    }



    protected String requestURI(HttpServletRequest request)
    {
        return request.getRequestURI();
    }
}
