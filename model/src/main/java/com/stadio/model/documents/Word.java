package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "tab_word")
public class Word {
    @Id
    String id;

    @Field(value = "wordStr")
    String wordStr;

    @Field(value = "docAppear")
    long docAppear;

    @Field(value = "docAppearStr")
    String docAppearStr;

    @Field(value = "countAppear")
    Map<String,Integer> countAppear;

    @Field(value = "wordIdx")
    long wordIdx;

    @Field(value = "idf")
    double idf;

    public Word(String wordStr){
        this.wordStr = wordStr;
        this.docAppear =0;
        this.wordIdx = 0 ;
        this.docAppearStr = "";
        this.countAppear = new HashMap<>();
        this.idf=0;

    }

    public void incrementDocAppear(){
        docAppear+=1;
    }

    public void addDocAppear(String tconst){
        docAppearStr = docAppearStr+" "+tconst;
    }

    public void incrementCountAppear(String tconst){
        if(countAppear.containsKey(tconst)){
            countAppear.put(tconst,countAppear.get(tconst) + 1);
        }else{
            countAppear.put(tconst,1);
        }
    }

    public void incrementCountAppearInTitle(String tconst){
        if(countAppear.containsKey(tconst)){
            countAppear.put(tconst,countAppear.get(tconst) + 3);
        }else{
            countAppear.put(tconst,3);
        }
    }

    public void incrementCountAppearInGenres(String tconst){
        if(countAppear.containsKey(tconst)){
            countAppear.put(tconst,countAppear.get(tconst) + 2);
        }else{
            countAppear.put(tconst,2);
        }
    }

    public void incrementCountAppearInArtists(String tconst){
        if(countAppear.containsKey(tconst)){
            countAppear.put(tconst,countAppear.get(tconst) + 3);
        }else{
            countAppear.put(tconst,3);
        }
    }

    public boolean checkDocAppearWord(String tconst){
        return countAppear.containsKey(tconst);
    }


}
