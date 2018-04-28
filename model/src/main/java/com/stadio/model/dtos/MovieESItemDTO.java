package com.stadio.model.dtos;

import com.stadio.model.documents.Movie;
import com.stadio.model.esDocuments.MovieES;
import lombok.Data;

@Data
public class MovieESItemDTO {

    private String id;

    private String tconst;

    private String primaryTitle;

    private String image;

    private Integer startYear;

    private String runtimeMinutes;

    private Long numVotes;

    public static MovieESItemDTO with(MovieES movie){
        MovieESItemDTO movieItemDTO = new MovieESItemDTO();
        movieItemDTO.setId(movie.getId());
        movieItemDTO.setTconst(movie.getTconst());
        movieItemDTO.setPrimaryTitle(movie.getPrimaryTitle());
        movieItemDTO.setImage(movie.getImage());
        movieItemDTO.setRuntimeMinutes(movie.getRuntimeMinutes());
        movieItemDTO.setStartYear(movie.getStartYear());
        movieItemDTO.setNumVotes(movie.getNumVotes());
        return movieItemDTO;
    }
}
