package com.stadio.model.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "tab_comment")
public class Comment {

    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "user_id_ref")
    private String userId;

    @Field(value = "content")
    private String content;

    @Field(value = "users_reply")
    private Map<String,Integer> usersReply;
}
