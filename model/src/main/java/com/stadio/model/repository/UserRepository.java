package com.stadio.model.repository;

import com.stadio.model.documents.User;
import com.stadio.model.repository.custom.UserRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom
{
    User findOneByUsername(String username);

    @Query("{ 'email': ?0 }")
    public List<User> findUserByEmail(String email);

    @Query("{ 'phone': ?0 }")
    public List<User> findUserByPhone(String phone);

    @Query("{ 'username': ?0 }")
    public List<User> findUserByUsername(String username);

    User findOneByFacebookId(String facebookId);

    User findOneByGoogleId(String googleId);

    User findFirstById(String id);

    User findFirstByEmail(String email);

}
