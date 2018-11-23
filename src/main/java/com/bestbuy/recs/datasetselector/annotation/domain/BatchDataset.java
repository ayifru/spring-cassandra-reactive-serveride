package com.bestbuy.recs.datasetselector.annotation.domain;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Table(value = "batch_ds")
public class BatchDataset {

    @PrimaryKey private BatchDatasetKey batchDatasetKey;
    @Column("items") private List<Item> items;
    @Column("last_modified") private Date lastModified;

    public BatchDatasetKey getBatchDatasetKey() {
        return batchDatasetKey;
    }

    public void setBatchDatasetKey(BatchDatasetKey batchDatasetKey) {
        this.batchDatasetKey = batchDatasetKey;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
