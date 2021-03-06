package com.stadio.restapi.service.impl;

import com.stadio.model.documents.MovieStopWord;

import com.stadio.model.documents.MovieTfIdf;
import com.stadio.model.documents.Word;
import com.stadio.model.repository.MovieArtistRepository;
import com.stadio.model.repository.MovieStopWordRepository;
import com.stadio.model.repository.MovieTfIdfRepository;
import com.stadio.model.repository.WordRepository;
import com.stadio.restapi.service.ITfIdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TfIdfServiceImpl implements ITfIdfService {

    @Autowired
    MovieTfIdfRepository movieTfIdfRepository;

    @Autowired
    MovieStopWordRepository movieStopWordRepository;

    @Autowired
    WordRepository wordRepository;

    @Override
    public void analyze() {
        createWordLibrary();
        calculateTfIdf();
    }

    @Override
    public void createWordLibrary(){
        wordRepository.deleteAll();

        long movieListSize =  movieStopWordRepository.count();
        int pageSize = 30;
        long pageQuantity = movieListSize/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<MovieStopWord> movieList = movieStopWordRepository.findAll(new PageRequest(page,pageSize)).getContent();
            //not pararell becase have idx duplicate
            movieList.stream().forEach(movieStopWord -> {
                String tconst = movieStopWord.getTconst();
                String storyline =  movieStopWord.getStoryStandard().trim();
                String title = movieStopWord.getTitle().trim();
                String genres =  movieStopWord.getGenres().trim();
                String artists = movieStopWord.getArtists().trim();

                // check in storyline
                wordInStoryLine(storyline,tconst);
                // check in storyline
                wordInTitle(title,tconst);
                // check in storyline
                wordInGenres(genres,tconst);
                //check in artists
                wordInArtists(artists,tconst);


            });
            page+=1;
            System.out.println("page: "+page);
        }
        // ket thuc co thu vien tu
        System.out.println("log create word libary done");
    }


    @Override
    public void calculateTfIdf(){
        calculateIdf();
        calculateTf();
    }

    private void calculateIdf(){
        long wordQuantity = wordRepository.count();
        long movieQuantity = movieStopWordRepository.count();// so tai lieu
        int pageSize = 30;
        long pageQuantity = wordQuantity/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<Word> wordList = wordRepository.findAll(new PageRequest(page,pageSize)).getContent();
            wordList.parallelStream().forEach(word -> {
                long docAppear = word.getDocAppear();// so tai lieu co tu xuat hien
                double idf = Math.log10(1.0+movieQuantity/(docAppear*1.0));
                word.setIdf(idf);
                wordRepository.save(word);
            });

            page+=1;
        }
        System.out.println("log : idf done");
    }

    private void calculateTf(){
        movieTfIdfRepository.deleteAll();

        long movieListSize =  movieStopWordRepository.count();
        int pageSize = 30;
        long pageQuantity = movieListSize/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<MovieStopWord> movieList = movieStopWordRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieList.parallelStream().forEach(movieStopWord -> {
                MovieTfIdf movieTfIdf = new MovieTfIdf();

                String tconst = movieStopWord.getTconst();

                List<Word> wordList = wordRepository.findByDocAppearStrRegex(tconst);

                Map<Long,Double> tfVector = new HashMap<>();
                Map<Long,Double> tfidfVector = new HashMap<>();

                wordList.forEach(word -> {
                    Integer countAppearInThisDoc = word.getCountAppear().get(tconst);
                    long wordIdx = word.getWordIdx();
                    double idf = word.getIdf();

                    double tf =Math.log10(1.0+countAppearInThisDoc);
                    double tfidf = tf*idf;

                    tfVector.put(wordIdx,tf);
                    tfidfVector.put(wordIdx,tfidf);
                });

                movieTfIdf.setTconst(tconst);
                movieTfIdf.setType(movieStopWord.getType());
                movieTfIdf.setTfVector(tfVector);
                movieTfIdf.setTfidfVector(tfidfVector);
                movieTfIdf.calculateLengthTfIdfVector();

                movieTfIdfRepository.save(movieTfIdf);

            });

            page+=1;
        }
        System.out.println("log: tfidf done");
    }


    private void wordInStoryLine(String storyline,String tconst){
        if(storyline!=null && !storyline.equals("")) {
            String[] wordList = storyline.split("\\s+");
            for (int pos = 0; pos < wordList.length; pos++) {
                String wordStr = wordList[pos];
                Word word = wordRepository.findFirstByWordStr(wordStr);
                if (word == null) {// day la tu moi
                    word = new Word(wordStr);
                    word.incrementDocAppear();// tang so cac van ban xuat hien tu
                    word.addDocAppear(tconst);// them ma van ban
                    word.incrementCountAppear(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    word.setWordIdx(wordRepository.count() + 1);

                } else {// day khong phai la tu moi
                    // kiem tra tu da xuat hien trong van ban hay chua
                    boolean appear = word.checkDocAppearWord(tconst);
                    if (appear) {//neu xuat hien roi
                        word.incrementCountAppear(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    } else {// neu chua xuat hien
                        word.incrementDocAppear();// tang so cac van ban xuat hien tu
                        word.addDocAppear(tconst);
                        word.incrementCountAppear(tconst);

                    }
                }
                wordRepository.save(word);
            }
        }
    }

    private void wordInTitle(String title,String tconst){
        if(title!=null && !title.equals("")) {
            String[] wordList = title.split("\\s+");
            for (int pos = 0; pos < wordList.length; pos++) {
                String wordStr = wordList[pos];
                Word word = wordRepository.findFirstByWordStr(wordStr);
                if (word == null) {// day la tu moi
                    word = new Word(wordStr);
                    word.incrementDocAppear();// tang so cac van ban xuat hien tu
                    word.addDocAppear(tconst);// them ma van ban
                    word.incrementCountAppearInTitle(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    word.setWordIdx(wordRepository.count() + 1);

                } else {// day khong phai la tu moi
                    // kiem tra tu da xuat hien trong van ban hay chua
                    boolean appear = word.checkDocAppearWord(tconst);
                    if (appear) {//neu xuat hien roi
                        word.incrementCountAppearInTitle(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    } else {// neu chua xuat hien
                        word.incrementDocAppear();// tang so cac van ban xuat hien tu
                        word.addDocAppear(tconst);
                        word.incrementCountAppearInTitle(tconst);
                    }
                }
                wordRepository.save(word);
            }
        }
    }

    private void wordInGenres(String genres,String tconst){
        if(genres!=null && !genres.equals("")) {
            String[] wordList = genres.split("\\s+");
            for (int pos = 0; pos < wordList.length; pos++) {
                String wordStr = wordList[pos];
                Word word = wordRepository.findFirstByWordStr(wordStr);
                if (word == null) {// day la tu moi
                    word = new Word(wordStr);
                    word.incrementDocAppear();// tang so cac van ban xuat hien tu
                    word.addDocAppear(tconst);// them ma van ban
                    word.incrementCountAppearInGenres(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    word.setWordIdx(wordRepository.count() + 1);

                } else {// day khong phai la tu moi
                    // kiem tra tu da xuat hien trong van ban hay chua
                    boolean appear = word.checkDocAppearWord(tconst);
                    if (appear) {//neu xuat hien roi
                        word.incrementCountAppearInGenres(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    } else {// neu chua xuat hien
                        word.incrementDocAppear();// tang so cac van ban xuat hien tu
                        word.addDocAppear(tconst);
                        word.incrementCountAppearInGenres(tconst);
                    }
                }
                wordRepository.save(word);
            }
        }
    }

    private void wordInArtists(String artists,String tconst){
        if(artists!=null && !artists.equals("")) {
            String[] wordList = artists.split("\\s+");
            for (int pos = 0; pos < wordList.length; pos++) {
                String wordStr = wordList[pos];
                Word word = wordRepository.findFirstByWordStr(wordStr);
                if (word == null) {// day la tu moi
                    word = new Word(wordStr);
                    word.incrementDocAppear();// tang so cac van ban xuat hien tu
                    word.addDocAppear(tconst);// them ma van ban
                    word.incrementCountAppearInArtists(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    word.setWordIdx(wordRepository.count() + 1);

                } else {// day khong phai la tu moi
                    // kiem tra tu da xuat hien trong van ban hay chua
                    boolean appear = word.checkDocAppearWord(tconst);
                    if (appear) {//neu xuat hien roi
                        word.incrementCountAppearInArtists(tconst);// tang bien dem so lan xuat hien tu trong van ban nay
                    } else {// neu chua xuat hien
                        word.incrementDocAppear();// tang so cac van ban xuat hien tu
                        word.addDocAppear(tconst);
                        word.incrementCountAppearInArtists(tconst);
                    }
                }
                wordRepository.save(word);
            }
        }
    }


}