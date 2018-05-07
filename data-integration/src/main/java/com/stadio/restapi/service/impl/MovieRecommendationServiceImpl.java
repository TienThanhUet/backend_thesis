package com.stadio.restapi.service.impl;

import com.stadio.model.documents.*;
import com.stadio.model.dtos.MovieItemDTO;
import com.stadio.model.model.RecommendItem;
import com.stadio.model.repository.*;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IMovieRecommendation;
import com.stadio.restapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class MovieRecommendationServiceImpl implements IMovieRecommendation {

    @Autowired
    MovieTfIdfRepository movieTfIdfRepository;

    @Autowired
    MovieRecommedationRepository movieRecommedationRepository;

    @Autowired
    MovieCompareRepository movieCompareRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    IUserService userService;

    @Autowired
    UserHistoryRepository userHistoryRepository;

    public MovieRecommend calculateMovieRecommendation(String tconst) {
        MovieRecommend movieRecommend = movieRecommedationRepository.findFirstByTconst(tconst);
        if(movieRecommend!=null)
            movieRecommedationRepository.delete(movieRecommend.getId());

        long wordQuantity = movieTfIdfRepository.count();
        int pageSize = 30;
        long pageQuantity = wordQuantity/pageSize;
        int page = 0;

        MovieRecommend movieRecommendNew = new MovieRecommend();
        movieRecommendNew.setTconst(tconst);

        MovieTfIdf movieTfIdf = movieTfIdfRepository.findFirstByTconst(tconst);

        if(movieTfIdf ==null){
            movieRecommedationRepository.save(movieRecommendNew);
            return movieRecommendNew;
        }

        //calculate similarity and add recommend
        while (page<=pageQuantity){
            List<MovieTfIdf> movieTfIdfList = movieTfIdfRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieTfIdfList.stream().forEach(movieTfIdf1 -> {
                if(!movieTfIdf.getType().equals(movieTfIdf1.getType()))
                    return;// next sang doi tuong tiep theo.
                if(movieTfIdf.getTconst().equals(movieTfIdf1.getTconst()))
                    return;


                String tconst1 = movieTfIdf1.getTconst();
//              MovieCompare movieCompare = movieCompareRepository.findFirstByTconst1AndTconst2(tconst1,tconst);
                MovieCompare movieCompare = new MovieCompare();
                double similarity = movieTfIdf.calculateCosine(movieTfIdf1);
                movieCompare.setTconst1(tconst);
                movieCompare.setTconst2(tconst1);
                movieCompare.setSimilarity(similarity);

//              if(movieCompareRepository.count()<20000000)
//                  movieCompareRepository.save(movieCompare);

                //add recommend
                movieRecommendNew.checkAndAddMovie2ListRecommend(movieCompare);
            });

            page+=1;
        }

        //save movie recommend
        movieRecommedationRepository.save(movieRecommendNew);
        return movieRecommendNew;
    }

    @Override
    public ResponseResult recommendMovie(String tconst) {
        MovieRecommend movieRecommend = movieRecommedationRepository.findFirstByTconst(tconst);
        List<MovieItemDTO> movieItemDTOList = new LinkedList<>();
        if(movieRecommend==null) {
            movieRecommend = this.calculateMovieRecommendation(tconst);
        }
        List<RecommendItem> recommendItemList = movieRecommend.getRecommendation();
        recommendItemList.parallelStream().forEach(recommendItem -> {
            Movie movie = movieRepository.findFirstByTconst(recommendItem.getTconst());
            movieItemDTOList.add(MovieItemDTO.with(movie));
        });
        return ResponseResult.newSuccessInstance(movieItemDTOList);
    }

    @Override
    public ResponseResult recommendUser(String token) {
        List<MovieItemDTO> movieItemDTOList = new ArrayList<>();

        User user =  userService.getCurrentUser(token);
        if(user!=null){
            PageRequest pageRequest = new PageRequest(0,20);
            List<UserHistory> userHistoryList = userHistoryRepository.findByUserIdOrderByCreateDateDesc(user.getId(),pageRequest);

            List<MovieTfIdf> movieTfIdfList  = new LinkedList<>();
            List<MovieTfIdf> userHistoryTfIdfList = new LinkedList<>();


            if(userHistoryList!=null && userHistoryList.size()>0){
                userHistoryList.parallelStream().forEach(userHistory -> {
                    MovieTfIdf userHistoryTfIdf =  movieTfIdfRepository.findFirstByTconst(userHistory.getTconst());
                    userHistoryTfIdfList.add(userHistoryTfIdf);

                    MovieRecommend movieRecommend = movieRecommedationRepository.findFirstByTconst(userHistory.getTconst());
                    if(movieRecommend==null) {
                        movieRecommend = this.calculateMovieRecommendation(userHistory.getTconst());
                    }
                    movieRecommend.getRecommendation().parallelStream().forEach(recommendItem -> {
                        MovieTfIdf movieTfIdf =  movieTfIdfRepository.findFirstByTconst(recommendItem.getTconst());
                        if(movieTfIdf!=null)
                            movieTfIdfList.add(movieTfIdf);
                        else{
                            System.out.println("log: tconst null "+recommendItem.getTconst());
                        }
                    });
                });

                List<String> tconstList = new ArrayList<>();// check duplicate tconst
                List<RecommendItem> recommendation= new ArrayList<>();
                for(int pos = 0;pos<movieTfIdfList.size();pos++){

                    double similarityTotal = 0;
                    MovieTfIdf movieTfIdf = movieTfIdfList.get(pos);
                    if(tconstList.contains(movieTfIdf.getTconst()))
                        continue;

                    for(int pos1 =0;pos1<userHistoryTfIdfList.size();pos1++){
                        double similarity = movieTfIdf.calculateCosine(userHistoryTfIdfList.get(pos1));
                        similarityTotal+=similarity;
                    }
                    RecommendItem recommendItem = new RecommendItem(movieTfIdf.getTconst(),similarityTotal);
                    recommendation = checkAndAddMovie2ListRecommend(recommendation,recommendItem);

                    tconstList.add(movieTfIdf.getTconst());
                };

                recommendation.stream().forEach(recommendItem -> {
                    Movie movie = movieRepository.findFirstByTconst(recommendItem.getTconst());
                    movieItemDTOList.add(MovieItemDTO.with(movie));
                });

            }else{
                List<Movie> movieList = movieRepository.topMovie();
                movieList.parallelStream().forEach(movie -> {
                    movieItemDTOList.add(MovieItemDTO.with(movie));
                });
            }


        }
        return ResponseResult.newSuccessInstance(movieItemDTOList);
    }

    private List<RecommendItem> checkAndAddMovie2ListRecommend(List<RecommendItem> recommendation,RecommendItem recommendItem){
        //if size <12 list not enough
        recommendation.add(recommendItem);
        if(recommendation.size()<12) return recommendation;

        recommendation.sort(Comparator.comparingDouble(RecommendItem::getSimilarity).reversed());

        return new ArrayList<RecommendItem>(recommendation.subList(0,12));
    }

}
