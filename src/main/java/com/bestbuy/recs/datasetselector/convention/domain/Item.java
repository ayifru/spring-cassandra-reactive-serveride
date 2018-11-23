package com.bestbuy.recs.datasetselector.convention.domain;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private double score;
    private String sku;

    public Item(){}

    public Item(String sku, double score) {
        this.score = score;
        this.sku = sku;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
