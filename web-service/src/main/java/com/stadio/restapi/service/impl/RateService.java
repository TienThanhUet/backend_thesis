package com.stadio.restapi.service.impl;

import com.stadio.model.documents.Movie;
import com.stadio.model.documents.Rate;
import com.stadio.model.documents.User;
import com.stadio.model.repository.MovieRepository;
import com.stadio.model.repository.RateRepository;
import com.stadio.model.repository.UserRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService extends BaseService implements IRateService{
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserService userService;

    @Autowired
    RateRepository rateRepository;

    @Override
    public ResponseResult getRate(String tconst, String token) {
        User user = userService.getCurrentUser(token);
        if(user!=null){
            Rate rate = rateRepository.findFirstByTconstAndUserId(tconst,user.getId());
            if(rate!=null)
                return ResponseResult.newSuccessInstance(rate);
            else return ResponseResult.newErrorInstance("400",null);
        }else
            return ResponseResult.newErrorInstance("400",null);
    }

    @Override
    public ResponseResult addRate(String tconst, Integer score, String token) {
        User user = userService.getCurrentUser(token);
        Movie movie = movieRepository.findFirstByTconst(tconst);
        long numVotes = movie.getNumVotes();
        double averageRating = movie.getAverageRating();
        if(user!=null) {
            Rate rate = rateRepository.findFirstByTconstAndUserId(tconst,user.getId());
            if(rate !=null){
                int oldScore = rate.getScore();
                averageRating = (averageRating*numVotes-oldScore+score)/(numVotes*1.0);
                movie.setAverageRating(averageRating);
                movie.setTotalScore(averageRating*numVotes);
                movieRepository.save(movie);
                //
                rate.setScore(score);
            }else{
                averageRating = (averageRating*numVotes+score)/(numVotes*1.0+1.0);
                numVotes+=1;
                movie.setAverageRating(averageRating);
                movie.setNumVotes(numVotes);
                movie.setTotalScore(averageRating*numVotes);
                movieRepository.save(movie);
                //
                rate =new Rate();
                rate.setTconst(tconst);
                rate.setScore(score);
                rate.setUserId(user.getId());
            }
            rateRepository.save(rate);
            return ResponseResult.newSuccessInstance(score);
        }else{
            return ResponseResult.newErrorInstance("400",null);
        }
    }

    @Override
    public ResponseResult deleteRate(String tconst, String token) {
        User user = userService.getCurrentUser(token);
        if(user!=null) {
            Movie movie = movieRepository.findFirstByTconst(tconst);
            long numVotes = movie.getNumVotes();
            double averageRating = movie.getAverageRating();

            Rate rate = rateRepository.findFirstByTconstAndUserId(tconst,user.getId());
            if(rate!=null){
                int score = rate.getScore();
                averageRating = (averageRating*numVotes-score)/(numVotes*1.0-1);
                numVotes=numVotes-1;

                movie.setNumVotes(numVotes);
                movie.setAverageRating(averageRating);
                movieRepository.save(movie);
            }

            rateRepository.deleteByTconstAndUserId(tconst,user.getId());
        }
        return ResponseResult.newSuccessInstance("delete rate sucess");
    }
}
