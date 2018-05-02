package com.stadio.model.dtos;

import com.stadio.model.documents.Comment;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class CommentDetailsDTO {

    private String id;

    private String tconst;

    private String username;

    private String content;

    private Integer userLike;

    private Integer userDislike;

    private Integer thisUserOpinion;

    public CommentDetailsDTO() {
    }

    public CommentDetailsDTO(Comment comment) {
        this.id = comment.getId();
        this.tconst = comment.getTconst();
        this.username = comment.getUsername();
        this.content = comment.getContent();
        userLike =0;
        userDislike =0;
        thisUserOpinion =0;
    }
}
