package com.stadio.model.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Word {
    String word;
    int countAppear;
    Map<Integer,Integer> appearDoc;
    int wordIdx;

    public Word(){
        word ="";
        countAppear =0;
        wordIdx = 0 ;
        appearDoc = new HashMap<>();
    }

    public Word(String word, int countAppear, int wordIdx) {
        this.word = word;
        this.countAppear = countAppear;
        this.wordIdx = wordIdx;
        appearDoc = new HashMap<>();
    }

    public void incrementCountAppear(){
        countAppear+=1;
    }

    public void addAppearInDoc(Integer docIdx){
         if(appearDoc.containsKey(docIdx)){
             appearDoc.put(docIdx,appearDoc.get(docIdx) + 1);
         }else{
             appearDoc.put(docIdx,1);
         }
    }
}
