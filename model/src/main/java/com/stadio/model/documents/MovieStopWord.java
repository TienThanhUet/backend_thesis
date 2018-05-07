package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "tab_movie_stopword")
public class MovieStopWord {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "type")
    private String type;

    @Field(value = "title")
    private String title;

    @Field(value = "genres")
    private String genres;

    @Field(value = "artists")
    private String artists;

    @Field(value = "story_standard")
    private String storyStandard;
}
