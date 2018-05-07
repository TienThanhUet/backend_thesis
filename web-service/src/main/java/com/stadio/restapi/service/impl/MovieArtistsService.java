package com.stadio.restapi.service.impl;

import com.stadio.model.documents.Artist;
import com.stadio.model.documents.MovieArtist;
import com.stadio.model.dtos.MovieArtistItemDTO;
import com.stadio.model.repository.ArtistRepository;
import com.stadio.model.repository.MovieArtistRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MovieArtistsService implements IMovieArtistsService {

    @Autowired
    MovieArtistRepository movieArtistRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Override
    public ResponseResult listArtistOfMovie(String tconst) {
        List<String> categoryList = new LinkedList<>();
        categoryList.add("actor");
        categoryList.add("actress");
        List<MovieArtist> movieArtistList = movieArtistRepository.findByTconstAndCategoryIn(tconst,categoryList);
        List<MovieArtistItemDTO> movieArtistItemDTOS = new LinkedList<>();
        if(!movieArtistList.isEmpty()){
            movieArtistList.parallelStream().forEach(movieArtist -> {
                Artist artist = artistRepository.findFirstByNconst(movieArtist.getNconst());
                if(artist!=null){
                    MovieArtistItemDTO movieArtistItemDTO = new MovieArtistItemDTO();
                    movieArtistItemDTO.setNconst(artist.getNconst());
                    movieArtistItemDTO.setName(artist.getPrimaryName());
                    movieArtistItemDTO.setCategory(movieArtist.getCategory());
                    movieArtistItemDTO.setCharacters(movieArtist.getCharacters().substring(2,movieArtist.getCharacters().length()-3));
                    movieArtistItemDTO.setImage(artist.getImage());
                    movieArtistItemDTOS.add(movieArtistItemDTO);
                }
            });
        }
        return ResponseResult.newSuccessInstance(movieArtistItemDTOS);
    }
}
