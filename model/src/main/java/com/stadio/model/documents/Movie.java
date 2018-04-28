package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "tab_movies")
public class Movie {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "titleType")
    private String titleType;

    @Field(value = "primaryTitle")
    private String primaryTitle;

    @Field(value = "image")
    private String image;

    @Field(value = "trailer")
    private String trailer;

    @Field(value = "imageTrailer")
    private String imageTrailer;

    @Field(value = "storyline")
    private String storyline;

    @Field(value = "region")
    private String region;

    @Field(value = "isAdult")
    private Integer isAdult;

    @Field(value = "startYear")
    private Integer startYear;

    @Field(value = "runtimeMinutes")
    private String runtimeMinutes;

    @Field(value = "genres")
    private String genres;

    @Field(value = "averageRating")
    private double averageRating;

    @Field(value = "numVotes")
    private Long numVotes;
}
