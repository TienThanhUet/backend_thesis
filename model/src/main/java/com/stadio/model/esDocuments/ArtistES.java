package com.stadio.model.esDocuments;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "imdb",type = "artist")
public class ArtistES {
    @Id
    private String id;

    @Field(type = FieldType.String)
    private String nconst;

    @Field(type = FieldType.String)
    private String primaryName;
}
