package com.bestbuy.recs.datasetselector.annotation.domain;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType
public class Item {

    @Column(value = "sku")
    @CassandraType(type = DataType.Name.TEXT)
    private String sku;

    @Column(value = "score")
    @CassandraType(type = DataType.Name.DOUBLE)
    private double score;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
