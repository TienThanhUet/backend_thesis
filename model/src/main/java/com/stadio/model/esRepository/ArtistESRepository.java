package com.stadio.model.esRepository;

import com.stadio.model.esDocuments.ArtistES;
import com.stadio.model.esDocuments.MovieES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArtistESRepository extends ElasticsearchRepository<ArtistES,String>{
}
