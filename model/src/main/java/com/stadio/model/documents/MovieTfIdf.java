package com.stadio.model.documents;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@Data
@Document(collection = "tab_movie_tfidf")
public class MovieTfIdf {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "type")
    private String type;

    @Field(value = "tfVector")
    private Map<Long,Double> tfVector;

    @Field(value = "tfidfVector")
    private Map<Long,Double> tfidfVector;

    @Field(value = "lengthTfIdfVector")
    private Double lengthTfIdfVector;

    public double calculateCosine(MovieTfIdf movieTfIdf){

        double length1 = this.calculateLengthTfIdfVector();
        double length2 = movieTfIdf.calculateLengthTfIdfVector();

        List<Long> wordIdxList = new ArrayList<>();
        wordIdxList.addAll(this.tfidfVector.keySet());

        double scalar = 0.0;
        for(int pos=0;pos<wordIdxList.size();pos++){
            Long wordIdx = wordIdxList.get(pos);
            if(movieTfIdf.getTfidfVector().containsKey(wordIdx)){
                scalar+=this.tfidfVector.get(wordIdx)*movieTfIdf.getTfidfVector().get(wordIdx);
            }
        }

        double similarity = scalar/(length1*length2);

        return similarity;
    }

    public double calculateLengthTfIdfVector(){
        if(this.lengthTfIdfVector!=null)
            return this.lengthTfIdfVector;
        //else don't calculate
        List<Double> tfidfVectorValue = new ArrayList<>(this.tfidfVector.values());
        double lengthPower = tfidfVectorValue.stream().reduce(0.0,(p1, p2) -> p1 + Math.pow(p2,2));
        this.lengthTfIdfVector = Math.sqrt(lengthPower);
        return lengthTfIdfVector;
    }
}
