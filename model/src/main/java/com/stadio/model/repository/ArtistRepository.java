package com.stadio.model.repository;

import com.stadio.model.documents.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArtistRepository extends MongoRepository<Artist, String> {
        List<Artist> findByNconst(String nconst);
}
