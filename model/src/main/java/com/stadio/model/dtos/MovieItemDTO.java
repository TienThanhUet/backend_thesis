package com.stadio.model.dtos;

import com.stadio.model.documents.Movie;
import lombok.Data;

@Data
public class MovieItemDTO {

    private String id;

    private String tconst;

    private String titleType;

    private String primaryTitle;

    private String image;

    private String runtimeMinutes;

    private String genres;

    public static  MovieItemDTO with(Movie movie){
        MovieItemDTO movieItemDTO = new MovieItemDTO();
        movieItemDTO.setId(movie.getId());
        movieItemDTO.setTconst(movie.getTconst());
        movieItemDTO.setTitleType(movie.getTitleType());
        movieItemDTO.setPrimaryTitle(movie.getPrimaryTitle());
        movieItemDTO.setImage(movie.getImage());
        movieItemDTO.setRuntimeMinutes(movie.getRuntimeMinutes());
        movieItemDTO.setGenres(movie.getGenres());
        return movieItemDTO;
    }
}
