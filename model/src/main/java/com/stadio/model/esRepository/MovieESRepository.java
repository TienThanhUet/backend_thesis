package com.stadio.model.esRepository;

import com.stadio.model.esDocuments.MovieES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


import java.util.List;

public interface MovieESRepository extends ElasticsearchRepository<MovieES,String> {

    Page<MovieES> findByPrimaryTitleIsLikeOrderByNumVotesDesc(String text, Pageable pageable);

    Page<MovieES> findByPrimaryTitleRegexOrderByNumVotesDesc(String text, Pageable pageable);

    Page<MovieES> findByPrimaryTitleMatchesOrderByNumVotesDesc(String text, Pageable pageable);

    Page<MovieES> findByPrimaryTitleNearOrderByNumVotesDesc(String text, Pageable pageable);

}
