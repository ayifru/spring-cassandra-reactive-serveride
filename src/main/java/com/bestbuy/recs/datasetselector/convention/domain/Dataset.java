package com.bestbuy.recs.datasetselector.convention.domain;

import java.util.Date;
import java.util.Set;

public class Dataset {

    private DatasetKey datasetKey;
    private String description;
    private Set<String> driverFields;
    private Date lastModified;
    private String storageType;

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
