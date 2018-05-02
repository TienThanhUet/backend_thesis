package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "tab_rate")
public class Rate {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "user_id_ref")
    private String userId;

    @Field(value = "score")
    private Integer score;
}
