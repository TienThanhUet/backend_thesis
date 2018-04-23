package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "tab_artists")
public class Artist {
    @Id
    private String id;

    @Field(value = "nconst")
    private String nconst;

    @Field(value = "image")
    private String image;

    @Field(value = "primaryName")
    private String primaryName;

    @Field(value = "birthYear")
    private String birthYear;

    @Field(value = "deathYear")
    private String deathYear;

    @Field(value = "primaryProfession")
    private String primaryProfession;

    @Field(value = "knownForTitles")
    private String knownForTitles;
}
