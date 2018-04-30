package com.stadio.model.model;

import lombok.Data;

@Data
public class RecommendItem {
    private String tconst;

    private Double similarity;

    public RecommendItem(String tconst, Double similarity) {
        this.tconst = tconst;
        this.similarity = similarity;
    }
}
