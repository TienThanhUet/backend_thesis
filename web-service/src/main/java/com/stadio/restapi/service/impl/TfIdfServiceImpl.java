package com.stadio.restapi.service.impl;

import com.stadio.model.documents.MovieStopWord;

import com.stadio.model.documents.MovieTfIdf;
import com.stadio.model.documents.Word;
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
        calcutatorTfIdf();
    }

    @Autowired
    public void createWordLibrary(){
        wordRepository.deleteAll();
        long movieListSize =  movieStopWordRepository.count();
        int pageSize = 30;
        long pageQuantity = movieListSize/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<MovieStopWord> movieList = movieStopWordRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieList.forEach(movieStopWord -> {
                String tconst = movieStopWord.getTconst();
                String storyline =  movieStopWord.getStoryStandard().trim();
                String title = movieStopWord.getTitle().trim();
                String genres =  movieStopWord.getGenres().trim();

                // check in storyline
                wordInStoryLine(storyline,tconst);
                // check in storyline
                wordInTitle(title,tconst);
                // check in storyline
                wordInGenres(genres,tconst);

            });
            page+=1;
            System.out.println("page: "+page);
        }
        // ket thuc co thu vien tu
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

    @Autowired
    public void calcutatorTfIdf(){
        calcutatorIdf();
        calcutatorTf();
    }

    private void calcutatorIdf(){
        long wordQuantity = wordRepository.count();
        int pageSize = 30;
        long pageQuantity = wordQuantity/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<Word> wordList = wordRepository.findAll(new PageRequest(page,pageSize)).getContent();
            wordList.forEach(word -> {
                long docAppear = word.getDocAppear();
                double idf = Math.log10(1.0+wordQuantity/(docAppear*1.0));
                word.setIdf(idf);
                wordRepository.save(word);
            });

            page+=1;
        }
    }

    private void calcutatorTf(){
        movieTfIdfRepository.deleteAll();

        long movieListSize =  movieStopWordRepository.count();
        int pageSize = 30;
        long pageQuantity = movieListSize/pageSize;
        int page = 0;
        while (page<=pageQuantity){
            List<MovieStopWord> movieList = movieStopWordRepository.findAll(new PageRequest(page,pageSize)).getContent();
            movieList.forEach(movieStopWord -> {
                MovieTfIdf movieTfIdf = new MovieTfIdf();

                String tconst = movieStopWord.getTconst();

                List<Word> wordList = wordRepository.findByDocAppearStrRegex(tconst);

                Map<Long,Double> tfVector = new HashMap<>();
                Map<Long,Double> tfidfVector = new HashMap<>();

                wordList.forEach(word -> {
                    Integer countAppearInThisDoc = word.getCountAppear().get(tconst);
                    long wordIdx = word.getWordIdx();
                    double idf = word.getIdf();

                    double tf = 1.0 + Math.log10(countAppearInThisDoc);
                    double tfidf = tf*idf;

                    tfVector.put(wordIdx,tf);
                    tfidfVector.put(wordIdx,idf);
                });

                movieTfIdf.setTconst(tconst);
                movieTfIdf.setTfVector(tfVector);
                movieTfIdf.setTfidfVector(tfidfVector);

                movieTfIdfRepository.save(movieTfIdf);

            });

            page+=1;
        }
    }


}