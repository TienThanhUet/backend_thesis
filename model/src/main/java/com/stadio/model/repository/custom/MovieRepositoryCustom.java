package com.stadio.model.repository.custom;

import com.stadio.model.documents.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> topMovie();

    List<Movie> highlightTypeMovie(String type);

    List<Movie> listMovieType(Integer page, Integer pageSize,String type);

    long countMovieType(String type);

    List<Movie> listTvShowType(Integer page, Integer pageSize,String type);

    long countTvShowType(String type);
}
