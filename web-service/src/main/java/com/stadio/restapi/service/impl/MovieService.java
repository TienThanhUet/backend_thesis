package com.stadio.restapi.service.impl;

import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Movie;
import com.stadio.model.documents.MovieArtist;
import com.stadio.model.dtos.MovieDetailsDTO;
import com.stadio.model.dtos.MovieItemDTO;
import com.stadio.model.redisUtils.MovieRedisRepository;
import com.stadio.model.redisUtils.MovieTopRedisRepository;
import com.stadio.model.repository.MovieArtistRepository;
import com.stadio.model.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MovieService extends BaseService implements IMovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieRedisRepository movieRedisRepository;

    @Autowired
    MovieTopRedisRepository movieTopRedisRepository;


    @Override
    public ResponseResult processGetMovie(String tconst) {
        String movieStr = movieRedisRepository.processGetMovie(tconst);
        if(movieStr!=null){
            MovieDetailsDTO movieDetailsDTO = JsonUtils.parse(movieStr,MovieDetailsDTO.class);
            return ResponseResult.newSuccessInstance(movieDetailsDTO);
        }else{
            List<Movie> movieList = movieRepository.findByTconst(tconst);
            if(!movieList.isEmpty()){
                Movie movie = movieList.get(0);
                movieRedisRepository.processPutMovie(movie);
                MovieDetailsDTO movieDetailsDTO = MovieDetailsDTO.newInstance(movie);
                return ResponseResult.newSuccessInstance(movieDetailsDTO);
            }else {
                return ResponseResult.newErrorInstance("400","not found");
            }
        }
    }

    @Override
    public ResponseResult topMovieType(String type) {
        List<MovieItemDTO> movieItemDTOList = new LinkedList<>();
        Map<String,String> movieTopMap = movieTopRedisRepository.ProcessGetTopMovieType(type);
        if(!movieTopMap.isEmpty()){
            List<String> movieTopList = new ArrayList<>(movieTopMap.values());
            if(movieTopList!=null && movieTopList.size()!=0){
                movieTopList.stream().forEach(s -> {
                    MovieItemDTO movieItemDTO = JsonUtils.parse(s,MovieItemDTO.class);
                    movieItemDTOList.add(movieItemDTO);
                });
            }
        }else{
            List<Movie> movieTopList = movieRepository.topTypeMovie(type);
            movieTopList.forEach(movie -> {
                MovieItemDTO movieItemDTO = MovieItemDTO.with(movie);
                movieItemDTOList.add(movieItemDTO);
            });

            movieTopRedisRepository.ProcessPutTopMovieType(type,movieTopList);

        }
        return ResponseResult.newSuccessInstance(movieItemDTOList);
    }

    @Override
    public ResponseResult listMovieType(Integer page, Integer pageSize,String type) {
        List<Movie> movieList = movieRepository.listMovieType(page,pageSize,type);
        List<MovieItemDTO> movieItemDTOList = new LinkedList<>();
        if(movieList!=null){
            movieList.forEach(movie -> {
                movieItemDTOList.add(MovieItemDTO.with(movie));
            });
        }
        return ResponseResult.newSuccessInstance(movieItemDTOList);
    }
}
