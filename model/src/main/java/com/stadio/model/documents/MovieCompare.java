package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "tab_movie_compare")
public class MovieCompare {

    @Id
    private String id;

    @Field(value = "tconst1")
    private String tconst1;

    @Field(value = "tconst2")
    private String tconst2;

    @Field(value = "similarity")
    private Double similarity;
}
