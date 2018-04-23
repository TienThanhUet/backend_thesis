package com.stadio.model.repository.impl;

import com.stadio.model.documents.User;
import com.stadio.model.repository.custom.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom
{

    @Autowired MongoTemplate mongoTemplate;

    @Override
    public List<User> findUserByPage(Integer page, Integer pageSize)
    {
        Query query = new Query();

        query.limit(pageSize).skip((page - 1)  * pageSize);
        query.with(new Sort(Sort.Direction.DESC, "created_date"));

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> queryWithKeyword(String q)
    {
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingAny(q);

        Query query = TextQuery.queryText(criteria)
                .sortByScore().limit(100);

        return mongoTemplate.find(query, User.class);
    }
}
