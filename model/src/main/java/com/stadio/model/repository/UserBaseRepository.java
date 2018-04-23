package com.stadio.model.repository;

import com.stadio.model.model.UserBase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "UserBaseRepository")
public interface UserBaseRepository extends MongoRepository<UserBase, String>
{

    UserBase findOneByUsername(String username);

}