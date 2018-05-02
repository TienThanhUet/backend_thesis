package com.stadio.restapi.service.impl;

import com.google.common.collect.Lists;
import com.stadio.model.documents.Movie;
import com.stadio.model.dtos.MovieESItemDTO;
import com.stadio.model.dtos.MovieItemDTO;
import com.stadio.model.esDocuments.MovieES;
import com.stadio.model.esRepository.ArtistESRepository;
import com.stadio.model.esRepository.MovieESRepository;
import com.stadio.model.repository.MovieRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    MovieESRepository movieESRepository;

    @Autowired
    ArtistESRepository artistESRepository;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public ResponseResult processSearchMovie(String text) {
        PageRequest request = new PageRequest(0, 10);
        List<MovieES> movieESList = movieESRepository.findByPrimaryTitleIsLikeOrderByNumVotesDesc(text,request).getContent();
        List<MovieESItemDTO> movieESItemDTOList = new LinkedList<>();
        List<MovieESItemDTO> result = new ArrayList<>();
        if(movieESList!=null && movieESList.size()>0){
            movieESList.stream().forEach(movieES -> {
                movieESItemDTOList.add(MovieESItemDTO.with(movieES));
            });

//            if(movieESItemDTOList.size()>0){
//                try{
//                    movieESItemDTOList.sort(Comparator.comparing(MovieESItemDTO::getNumVotes));
//                    result = Lists.reverse(movieESItemDTOList);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }

        }

        return ResponseResult.newSuccessInstance(movieESItemDTOList);
    }

    @Override
    public String resetData() {
        movieESRepository.deleteAll();
        long movieListSize =  movieRepository.count();
        int pageSize = 30;
        long pageQuantity = movieListSize/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<Movie> movieList = movieRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieList.parallelStream().forEach(movie -> {
                MovieES movieES = new MovieES(movie);
                movieESRepository.save(movieES);
            });
            page+=1;
            System.out.println("log: page "+page);
        }
        return "reset data done";
    }
}
