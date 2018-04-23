package com.stadio.restapi.service.impl;

import com.stadio.model.documents.Comment;
import com.stadio.model.dtos.CommentDetailsDTO;
import com.stadio.model.repository.CommentRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService extends BaseService implements ICommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public ResponseResult getListComment(String tconst) {
        List<Comment> commentList =  commentRepository.findByTconst(tconst);
        List<CommentDetailsDTO> commentDetailsDTOS = new LinkedList<>();
        commentList.parallelStream().forEach(comment -> {

        });
        return null;
    }
}
