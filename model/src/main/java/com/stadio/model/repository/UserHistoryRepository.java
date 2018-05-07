package com.stadio.model.repository;

import com.stadio.model.documents.UserHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface UserHistoryRepository extends MongoRepository<UserHistory,String>{
    List<UserHistory> findByUserIdOrderByCreateDateDesc(String userId);

    List<UserHistory> findByUserIdOrderByCreateDateDesc(String userId, Pageable pageable);
}
