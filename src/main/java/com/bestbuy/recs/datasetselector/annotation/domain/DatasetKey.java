package com.bestbuy.recs.datasetselector.annotation.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
public class DatasetKey implements Serializable {
    @PrimaryKeyColumn(value = "name", type = PrimaryKeyType.PARTITIONED) private String name;
    @PrimaryKeyColumn(value = "version", type = PrimaryKeyType.PARTITIONED) private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
