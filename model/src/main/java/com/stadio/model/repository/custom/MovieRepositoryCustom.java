package com.stadio.model.repository.custom;

import com.stadio.model.documents.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> topTypeMovie(String type);

    List<Movie> listMovieType(Integer page, Integer pageSize,String type);

    List<Movie> listMovie(int page,int pageSize);
}
