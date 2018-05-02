package com.stadio.restapi.service.impl;

import com.stadio.model.documents.Comment;
import com.stadio.model.documents.User;
import com.stadio.model.dtos.CommentDetailsDTO;
import com.stadio.model.repository.CommentRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService extends BaseService implements ICommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Override
    public ResponseResult getListComment(String tconst, String token) {
        List<Comment> commentList =  commentRepository.findByTconstOrderByCreateDate(tconst);
        List<CommentDetailsDTO> commentDetailsDTOList = new LinkedList<>();
        commentList.stream().forEach(comment -> {
            CommentDetailsDTO commentDetailsDTO = new CommentDetailsDTO(comment);
            commentDetailsDTOList.add(commentDetailsDTO);
        });
        return ResponseResult.newSuccessInstance(commentDetailsDTOList);
    }

    @Override
    public ResponseResult addComment(String tconst, String content, String token) {
        User user = userService.getCurrentUser(token);
        if(user!=null){
            Comment comment = new Comment();
            comment.setTconst(tconst);
            comment.setUsername(user.getUsername());
            comment.setContent(content);
            comment.setCreateDate(new Date());
            commentRepository.save(comment);
        }
        return ResponseResult.newSuccessInstance("save comment success");
    }
}
