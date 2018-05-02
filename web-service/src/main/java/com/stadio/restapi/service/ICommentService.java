package com.stadio.restapi.service;

import com.stadio.restapi.response.ResponseResult;

public interface ICommentService {
    ResponseResult getListComment(String tconst, String token);

    ResponseResult addComment(String tconst,String content, String token);
}
