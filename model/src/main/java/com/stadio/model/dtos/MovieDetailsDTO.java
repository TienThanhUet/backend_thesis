package com.stadio.model.dtos;

import com.stadio.model.documents.Movie;
import lombok.Data;

@Data
public class MovieDetailsDTO {
    private String id;

    private String tconst;

    private String titleType;

    private String primaryTitle;

    private String image;

    private String trailer;

    private String imageTrailer;

    private String storyline;

    private String region;

    private Integer isAdult;

    private Integer startYear;

    private String runtimeMinutes;

    private String genres;

    private double averageRating;

    private Long numVotes;

    public static MovieDetailsDTO newInstance(Movie movie) {
        MovieDetailsDTO movieDetailsDTO = new MovieDetailsDTO();

        movieDetailsDTO.setId(movie.getId());
        movieDetailsDTO.setTconst(movie.getTconst());
        movieDetailsDTO.setTitleType(movie.getTitleType());
        movieDetailsDTO.setPrimaryTitle(movie.getPrimaryTitle());
        movieDetailsDTO.setImage(movie.getImage());
        movieDetailsDTO.setTrailer(movie.getTrailer());
        movieDetailsDTO.setImageTrailer(movie.getImageTrailer());
        movieDetailsDTO.setStoryline(movie.getStoryline());
        movieDetailsDTO.setRegion(movie.getRegion());
        movieDetailsDTO.setIsAdult(movie.getIsAdult());
        movieDetailsDTO.setStartYear(movie.getStartYear());
        movieDetailsDTO.setRuntimeMinutes(movie.getRuntimeMinutes());
        movieDetailsDTO.setGenres(movie.getGenres());
        movieDetailsDTO.setAverageRating(movie.getAverageRating());
        movieDetailsDTO.setNumVotes(movie.getNumVotes());

        return movieDetailsDTO;

    }
}
