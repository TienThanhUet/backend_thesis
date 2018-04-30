package com.stadio.restapi.service.impl;

import com.stadio.model.documents.MovieCompare;
import com.stadio.model.documents.MovieRecommend;
import com.stadio.model.documents.MovieTfIdf;
import com.stadio.model.repository.MovieCompareRepository;
import com.stadio.model.repository.MovieRecommedationRepository;
import com.stadio.model.repository.MovieStopWordRepository;
import com.stadio.model.repository.MovieTfIdfRepository;
import com.stadio.restapi.service.IMovieRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieRecommendationServiceImpl implements IMovieRecommendation {

    @Autowired
    MovieTfIdfRepository movieTfIdfRepository;

    @Autowired
    MovieRecommedationRepository movieRecommedationRepository;

    @Autowired
    MovieCompareRepository movieCompareRepository;

    @Override
    public void calcutateMovieRecommendation() {
        movieCompareRepository.deleteAll();

        long wordQuantity = movieTfIdfRepository.count();
        int pageSize = 30;
        long pageQuantity = wordQuantity/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<MovieTfIdf> movieTfIdfList = movieTfIdfRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieTfIdfList.forEach(movieTfIdf -> {
                String tconst = movieTfIdf.getTconst();

                MovieRecommend movieRecommend;

                MovieRecommend movieRecommendSave = movieRecommedationRepository.findFirstByTconst(tconst) ;
                if(movieRecommendSave==null){
                    movieRecommend = new MovieRecommend();
                    movieRecommend.setTconst(tconst);
                }else{
                    movieRecommend = movieRecommendSave;
                }
                int page1 = 0;

                //calculate similarity and add recommend
                while (page1<=pageQuantity){

                    List<MovieTfIdf> movieTfIdfList1 = movieTfIdfRepository.findAll(new PageRequest(page1,pageSize)).getContent();
                    movieTfIdfList1.stream().forEach(movieTfIdf1 -> {
                        if(movieTfIdf.getType().equals(movieTfIdf1.getType())){
                            String tconst1 = movieTfIdf1.getTconst();
                            MovieCompare movieCompare = movieCompareRepository.findFirstByTconst1AndTconst2(tconst,tconst1);
                            if(movieCompare==null){
                                movieCompare = new MovieCompare();
                                double similarity = movieTfIdf.calculateCosine(movieTfIdf1);
                                movieCompare.setTconst1(tconst);
                                movieCompare.setTconst2(tconst1);
                                movieCompare.setSimilarity(similarity);

                                movieCompareRepository.save(movieCompare);
                            }

                            //add recommend
                            movieRecommend.checkAndAddMovie2ListRecommend(movieCompare);
                        }else return;// next sang doi tuong tiep theo.
                    });

                    page1+=1;
                }

                //save movie recommend
                movieRecommedationRepository.save(movieRecommend);
            });

            page+=1;
        }

        System.out.println("log: compare movie done");

    }

}
