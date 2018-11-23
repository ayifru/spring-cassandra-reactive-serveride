package com.bestbuy.recs.datasetselector.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class DatasetDTO implements Serializable {

    private String name;
    private String version;
    private String description;
    private Set<String> driverFields;
    private Date lastModified;
    private String storageType;

    public DatasetDTO(String name, String version, String description, Set<String> driverFields,
                      Date lastModified, String storageType) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.driverFields = driverFields;
        this.lastModified = lastModified;
        this.storageType = storageType;
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
