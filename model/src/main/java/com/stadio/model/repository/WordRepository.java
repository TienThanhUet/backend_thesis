package com.stadio.model.repository;

import com.stadio.model.documents.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WordRepository extends MongoRepository<Word, String> {

    Word findFirstByWordStr(String wordStr);

    List<Word> findByDocAppearStrRegex(String docAppearStr);
}
