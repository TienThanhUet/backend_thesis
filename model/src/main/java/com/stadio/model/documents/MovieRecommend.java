package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "tab_movie_recommend")
public class MovieRecommend {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "recommendation")
    private List<String> recommendation;
}
