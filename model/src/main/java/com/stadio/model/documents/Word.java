package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "tab_word")
public class Word {
    String word;
    long countAppear;
    Map<String,Integer> appearDoc;
    int wordIdx;

    public Word(){
        word ="";
        countAppear =0;
        wordIdx = 0 ;
        appearDoc = new HashMap<>();
    }

    public Word(String word, long countAppear, int wordIdx) {
        this.word = word;
        this.countAppear = countAppear;
        this.wordIdx = wordIdx;
        appearDoc = new HashMap<>();
    }

    public void incrementCountAppear(){
        countAppear+=1;
    }

    public void addAppearInDoc(String tconst){
        if(appearDoc.containsKey(tconst)){
            appearDoc.put(tconst,appearDoc.get(tconst) + 1);
        }else{
            appearDoc.put(tconst,1);
        }
    }
}
