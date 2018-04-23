package com.stadio.model.repository.custom;

import com.stadio.model.documents.User;

import java.util.List;

public interface UserRepositoryCustom
{
    List<User> findUserByPage(Integer page, Integer pageSize);

    List<User> queryWithKeyword(String q);
}
