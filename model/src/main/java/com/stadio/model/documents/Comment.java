package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "tab_comment")
public class Comment {

    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "username")
    private String username;

    @Field(value = "content")
    private String content;

    @Field(value = "createDate")
    private Date createDate;

    @Field(value = "users_reply")
    private Map<String,Integer> usersReply;
}
