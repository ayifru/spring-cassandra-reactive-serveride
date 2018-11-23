package com.bestbuy.recs.datasetselector.annotation.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class BatchDatasetKey {

    @PrimaryKeyColumn(value = "driver", type = PrimaryKeyType.PARTITIONED) private String driver;
    @PrimaryKeyColumn(value = "name", type = PrimaryKeyType.PARTITIONED) private String name;
    @PrimaryKeyColumn(value = "version", type = PrimaryKeyType.PARTITIONED) private String version;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

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
