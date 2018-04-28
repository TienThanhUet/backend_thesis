package com.stadio.model.documents;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "tab_movie_tfidf")
public class MovieTfIdf {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "tfVector")
    private Map<Long,Double> tfVector;

    @Field(value = "tfidfVector")
    private Map<Long,Double> tfidfVector;


}
