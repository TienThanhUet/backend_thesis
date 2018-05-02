package com.stadio.restapi.service;

import com.stadio.model.documents.User;
import com.stadio.model.dtos.UserDTO;
import com.stadio.restapi.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;


public interface IUserService
{
    ResponseResult processCreateNewUser( UserDTO userDTO);

    User getCurrentUser(String accessToken);

    ResponseResult processChangePassword(String accessToken, String oldPass, String newPass);

    ResponseResult processGetProfileUser(String id);

    ResponseResult processUpdateUser(UserDTO userDTO);

    ResponseResult getMovieHistory(String token);

    ResponseResult addMovieHistory(String tconst, String token);
}
