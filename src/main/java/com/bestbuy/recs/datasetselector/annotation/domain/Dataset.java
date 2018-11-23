package com.bestbuy.recs.datasetselector.annotation.domain;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Table("datasets")
public class Dataset implements Serializable {
    @PrimaryKey private DatasetKey datasetKey;
    @Column("description") private String description;
    @Column("driver_fields") private Set<String> driverFields;
    @Column("last_modified") private Date lastModified;
    @Column("storage_type") private String storageType;

    public DatasetKey getDatasetKey() {
        return datasetKey;
    }

    public void setDatasetKey(DatasetKey datasetKey) {
        this.datasetKey = datasetKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getDriverFields() {
        return driverFields;
    }

    public void setDriverFields(Set<String> driverFields) {
        this.driverFields = driverFields;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}
