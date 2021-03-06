package com.stadio.model.dtos;

import com.stadio.model.documents.Movie;
import com.stadio.model.esDocuments.MovieES;
import lombok.Data;

@Data
public class MovieItemDTO {

    private String id;

    private String tconst;

    private String primaryTitle;

    private String image;

    private String runtimeMinutes;

    private double averageRating;

    private long numVotes;

    private Integer startYear;

    public static  MovieItemDTO with(Movie movie){
        MovieItemDTO movieItemDTO = new MovieItemDTO();

        movieItemDTO.setId(movie.getId());
        movieItemDTO.setTconst(movie.getTconst());
        movieItemDTO.setPrimaryTitle(movie.getPrimaryTitle());
        movieItemDTO.setImage(movie.getImage());
        movieItemDTO.setRuntimeMinutes(movie.getRuntimeMinutes());
        movieItemDTO.setAverageRating(movie.getAverageRating());
        movie.setNumVotes(movie.getNumVotes());
        movieItemDTO.setStartYear(movie.getStartYear());
        return movieItemDTO;
    }
}
