package com.stadio.restapi.service.impl;

import com.google.common.collect.Lists;
import com.stadio.common.utils.JsonUtils;
import com.stadio.model.documents.Movie;
import com.stadio.model.documents.MovieArtist;
import com.stadio.model.dtos.MovieDetailsDTO;
import com.stadio.model.dtos.MovieItemDTO;
import com.stadio.model.redisUtils.MovieHighlightRedisRepository;
import com.stadio.model.redisUtils.MovieRedisRepository;
import com.stadio.model.redisUtils.MovieTopRedisRepository;
import com.stadio.model.repository.MovieArtistRepository;
import com.stadio.model.repository.MovieRepository;
import com.stadio.restapi.model.PageInfo;
import com.stadio.restapi.response.TableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieService;

import java.util.*;

@Service
public class MovieService extends BaseService implements IMovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieRedisRepository movieRedisRepository;

    @Autowired
    MovieTopRedisRepository movieTopRedisRepository;

    @Autowired
    MovieHighlightRedisRepository movieHighlightRedisRepository;


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
    public ResponseResult topMovie() {
        List<MovieDetailsDTO> movieDetailsDTOList = new LinkedList<>();
        Map<String,String> movieTopMap = movieTopRedisRepository.processGetTopMovie();
        if(!movieTopMap.isEmpty()){
            List<String> movieTopList = new ArrayList<>(movieTopMap.values());
            if(movieTopList!=null && movieTopList.size()!=0){
                movieTopList.stream().forEach(s -> {
                    MovieDetailsDTO movieDetailsDTO = JsonUtils.parse(s,MovieDetailsDTO.class);
                    movieDetailsDTOList.add(movieDetailsDTO);
                });
            }
            movieDetailsDTOList.sort(Comparator.comparing(MovieDetailsDTO::getTotalScore).reversed());
        }else{
            List<Movie> movieTopList = movieRepository.topMovie();
            movieTopList.forEach(movie -> {
                MovieDetailsDTO movieDetailsDTO = MovieDetailsDTO.newInstance(movie);
                movieDetailsDTOList.add(movieDetailsDTO);
            });

            movieTopRedisRepository.processPutTopMovie(movieTopList);

        }
        return ResponseResult.newSuccessInstance(movieDetailsDTOList);
    }

    @Override
    public ResponseResult getMovieTypeHighlight(String type) {
        List<MovieItemDTO> movieItemDTOList = new LinkedList<>();
        Map<String,String> movieTopMap = movieHighlightRedisRepository.processGetMovieTypeHighlight(type);
        if(!movieTopMap.isEmpty()){
            List<String> movieTopList = new ArrayList<>(movieTopMap.values());
            if(movieTopList!=null && movieTopList.size()!=0){
                movieTopList.stream().forEach(s -> {
                    MovieItemDTO movieItemDTO = JsonUtils.parse(s,MovieItemDTO.class);
                    movieItemDTOList.add(movieItemDTO);
                });
            }
        }else{
            List<Movie> movieTopList = movieRepository.highlightTypeMovie(type);
            movieTopList.forEach(movie -> {
                MovieItemDTO movieItemDTO = MovieItemDTO.with(movie);
                movieItemDTOList.add(movieItemDTO);
            });

            movieHighlightRedisRepository.processPutMovieTypeHighlight(type,movieTopList);

        }
        return ResponseResult.newSuccessInstance(movieItemDTOList);
    }

    @Override
    public ResponseResult listMovieType(Integer page, Integer pageSize,String type,String uri) {
        List<Movie> movieList = movieRepository.listMovieType(page,pageSize,type);
        long quantity = movieRepository.countMovieType(type);

        List<MovieItemDTO> movieItemDTOList = new LinkedList<>();
        if(movieList!=null){
            movieList.forEach(movie -> {
                movieItemDTOList.add(MovieItemDTO.with(movie));
            });
        }

        PageInfo pageInfo = new PageInfo(page,quantity, pageSize, uri);

        TableList<MovieItemDTO> tableList = new TableList<>(pageInfo, movieItemDTOList);

        return ResponseResult.newSuccessInstance(tableList);
    }

    @Override
    public ResponseResult listTvShowType(Integer page, Integer pageSize, String type, String uri) {
        List<Movie> movieList = movieRepository.listTvShowType(page,pageSize,type);
        long quantity = movieRepository.countTvShowType(type);

        List<MovieItemDTO> movieItemDTOList = new LinkedList<>();
        if(movieList!=null){
            movieList.forEach(movie -> {
                movieItemDTOList.add(MovieItemDTO.with(movie));
            });
        }

        PageInfo pageInfo = new PageInfo(page,quantity, pageSize, uri);

        TableList<MovieItemDTO> tableList = new TableList<>(pageInfo, movieItemDTOList);

        return ResponseResult.newSuccessInstance(tableList);
    }

    @Override
    public ResponseResult calculateTotalScore() {
        long movieListSize =  movieRepository.count();
        int pageSize = 30;
        long pageQuantity = movieListSize/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<Movie> movieList = movieRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieList.parallelStream().forEach(movie -> {
                long numVotes = movie.getNumVotes();
                double averageRating = movie.getAverageRating();
                movie.setTotalScore(averageRating*numVotes);
                movieRepository.save(movie);
            });
            page+=1;
            System.out.println("log: page "+page);
        }
        return ResponseResult.newSuccessInstance("calculate success");
    }
}
