package com.stadio.model.documents;

import com.google.common.collect.Lists;
import com.stadio.model.model.RecommendItem;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Data
@Document(collection = "tab_movie_recommend")
public class MovieRecommend {
    @Id
    private String id;

    @Field(value = "tconst")
    private String tconst;

    @Field(value = "recommendation")
    private List<RecommendItem> recommendation;

    public MovieRecommend() {
        this.recommendation = new ArrayList<>();
    }

    public void checkAndAddMovie2ListRecommend(MovieCompare movieCompare){
        //if size <8 list not enough
        this.recommendation.add(new RecommendItem(movieCompare.getTconst2(),movieCompare.getSimilarity()));
        if(recommendation.size()<9) return;

        this.recommendation.sort(Comparator.comparingDouble(RecommendItem::getSimilarity).reversed());

        this.recommendation = new ArrayList<RecommendItem>(this.recommendation.subList(0,9));
    }
}
