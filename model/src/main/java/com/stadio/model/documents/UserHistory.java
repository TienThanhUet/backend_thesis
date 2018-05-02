package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Objects;

@Data
@Document(collection = "tab_user_history")
public class UserHistory {

    @Id
    private String id;

    @Field(value = "user_id_ref")
    private String userId;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "createDate")
    private Date createDate;

    public boolean equalsMovieHistory(UserHistory o) {
        return Objects.equals(tconst, o.tconst);
    }
}
