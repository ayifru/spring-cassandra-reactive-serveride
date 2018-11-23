package com.bestbuy.recs.datasetselector.convention;

import com.bestbuy.recs.datasetselector.convention.domain.BatchDataset;
import com.bestbuy.recs.datasetselector.convention.domain.BatchDatasetKey;
import com.bestbuy.recs.datasetselector.convention.domain.Item;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.cql.ReactiveCqlOperations;
import org.springframework.data.cassandra.core.cql.RowMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository("batchDatasetRepositoryConvention")
public class BatchDatasetRepository {

    private final ReactiveCassandraTemplate reactiveCassandraTemplate;

    private final ReactiveCqlOperations reactiveCqlOperations;

    private static final String KEYSPACE = "das";
    private static final String TABLE_NAME = "batch_ds";
    private static final String COLUMN_DRIVER = "driver";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ITEMS = "items";
    private static final String COLUMN_LAST_MODIFIED_DATE = "last_modified";
    private static final String COLUMN_VERSION = "version";

    private static final BatchDatasetRepository.BatchDatasetMapper MAPPER = new BatchDatasetRepository.BatchDatasetMapper();


    public BatchDatasetRepository(@Autowired ReactiveCassandraTemplate reactiveCassandraTemplate) {
        this.reactiveCassandraTemplate = reactiveCassandraTemplate;
        this.reactiveCqlOperations = this.reactiveCassandraTemplate.getReactiveCqlOperations();
    }

    public Mono<BatchDataset> findById(BatchDatasetKey batchDatasetKey) {
        Select select = QueryBuilder.select().from(KEYSPACE, TABLE_NAME);
        select.where(QueryBuilder.eq(COLUMN_DRIVER, batchDatasetKey.getDriver()))
                .and(QueryBuilder.eq(COLUMN_NAME, batchDatasetKey.getName()))
                .and(QueryBuilder.eq(COLUMN_VERSION, batchDatasetKey.getVersion()))
                .setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);

        return reactiveCqlOperations.queryForObject(select, MAPPER);
    }

    public Mono<Boolean> update(BatchDataset batchDataset) {
        Update update = QueryBuilder.update(KEYSPACE, TABLE_NAME);

        update.with(QueryBuilder.set(COLUMN_ITEMS, batchDataset.getItems()))
                .and(QueryBuilder.set(COLUMN_LAST_MODIFIED_DATE, batchDataset.getLastModified()));

        update.where(QueryBuilder.eq(COLUMN_DRIVER, batchDataset.getBatchDatasetKey().getDriver()))
                .and(QueryBuilder.eq(COLUMN_NAME,batchDataset.getBatchDatasetKey().getName()))
                .and(QueryBuilder.eq(COLUMN_VERSION, batchDataset.getBatchDatasetKey().getVersion()))
                .setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);

        return reactiveCqlOperations.execute(update);
    }

    private static class BatchDatasetMapper implements RowMapper<BatchDataset> {

        BatchDatasetMapper() {
            super();
        }

        @Override
        public BatchDataset mapRow(Row row, int rowNum) {
            BatchDataset batchDataset = new BatchDataset();
            BatchDatasetKey batchDatasetKey = new BatchDatasetKey();
            batchDatasetKey.setDriver(row.getString(COLUMN_DRIVER));
            batchDatasetKey.setName(row.getString(COLUMN_NAME));
            batchDatasetKey.setVersion(row.getString(COLUMN_VERSION));
            batchDataset.setItems(row.getList(COLUMN_ITEMS, Item.class));
            batchDataset.setLastModified(row.getTimestamp(COLUMN_LAST_MODIFIED_DATE));
            batchDataset.setBatchDatasetKey(batchDatasetKey);
            return batchDataset;
        }
    }
}
