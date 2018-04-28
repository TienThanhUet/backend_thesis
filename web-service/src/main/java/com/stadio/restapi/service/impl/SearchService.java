package com.stadio.restapi.service.impl;

import com.stadio.model.dtos.MovieESItemDTO;
import com.stadio.model.dtos.MovieItemDTO;
import com.stadio.model.esDocuments.MovieES;
import com.stadio.model.esRepository.ArtistESRepository;
import com.stadio.model.esRepository.MovieESRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    MovieESRepository movieESRepository;

    @Autowired
    ArtistESRepository artistESRepository;

    @Override
    public ResponseResult processSearchMovie(String text) {
        PageRequest request = new PageRequest(0, 10);
        List<MovieES> movieESList = movieESRepository.findByPrimaryTitleIsLikeOrderByNumVotesDesc(text,request).getContent();
        List<MovieESItemDTO> movieESItemDTOList = new LinkedList<>();
        if(!movieESList.isEmpty()){
            movieESList.parallelStream().forEach(movieES -> {
                movieESItemDTOList.add(MovieESItemDTO.with(movieES));
            });
        }

        if(movieESItemDTOList!=null && movieESItemDTOList.size()>0){
            movieESItemDTOList.sort(Comparator.comparing(MovieESItemDTO::getNumVotes).reversed());
        }

        return ResponseResult.newSuccessInstance(movieESItemDTOList);
    }
}
