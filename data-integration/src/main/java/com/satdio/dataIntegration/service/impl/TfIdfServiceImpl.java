package com.satdio.dataIntegration.service.impl;

import TFIDF.TfIdfCalculateImprove;
import com.satdio.dataIntegration.service.ITfIdfService;
import com.stadio.model.documents.Movie;
import com.stadio.model.documents.MovieStopWord;
import com.stadio.model.repository.MovieRepository;
import com.stadio.model.repository.MovieStopWordRepository;
import com.stadio.model.repository.MovieTfIdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TfIdfServiceImpl implements ITfIdfService {

    @Autowired
    MovieTfIdfRepository movieTfIdfRepository;

    @Autowired
    MovieStopWordRepository movieStopWordRepository;

    @Override
    public void analyze() {
        movieTfIdfRepository.deleteAll();

        List<MovieStopWord> movieStopWordList = movieStopWordRepository.findAll();
        List<String> stopWordList = new ArrayList<>();

        movieStopWordList.forEach(movieStopWord -> {
            stopWordList.add(movieStopWord.getStoryStandard());
        });

        TfIdfCalculateImprove tfIdfCalculate = new TfIdfCalculateImprove(stopWordList);
    }
}
