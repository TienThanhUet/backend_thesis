package com.stadio.model.model;

import lombok.Data;

import java.util.Objects;

@Data
public class RecommendItem {
    private String tconst;

    private Double similarity;

    public RecommendItem(String tconst, Double similarity) {
        this.tconst = tconst;
        this.similarity = similarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecommendItem that = (RecommendItem) o;
        return Objects.equals(tconst, that.tconst);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), tconst);
    }
}
