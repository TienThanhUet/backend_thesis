package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "tab_movie_artists")
public class MovieArtist {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "ordering")
    private Integer ordering;

    @Field(value = "nconst")
    private String nconst;

    @Field(value = "category")
    private String category;

    @Field(value = "job")
    private String job;

    @Field(value = "characters")
    private String characters;








}
