package com.stadio.model.esDocuments;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "imdb",type = "movie")
public class MovieES {
    @Id
    private String id;

    @Field(type = FieldType.String)
    private String tconst;

    @Field(type = FieldType.String)
    private String primaryTitle;

    @Field(type = FieldType.String)
    private String image;

    @Field(type = FieldType.Integer)
    private Integer startYear;

    @Field(type = FieldType.String)
    private String runtimeMinutes;

    @Field(type = FieldType.Long)
    private Long numVotes;
}
