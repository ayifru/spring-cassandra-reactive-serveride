package com.bestbuy.recs.datasetselector.convention;

import com.bestbuy.recs.datasetselector.convention.domain.Dataset;
import com.bestbuy.recs.datasetselector.convention.domain.DatasetKey;
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

@Repository(value = "datasetRepositoryConvention")
public class DatasetRepository {

    private final ReactiveCassandraTemplate reactiveCassandraTemplate;

    private final ReactiveCqlOperations reactiveCqlOperations;

    private static final String KEYSPACE = "das";
    private static final String DATASET_TABLENAME = "datasets";
    private static final String COLUMN_VERSION = "version";
    private static final String COLUMN_DRIVER_FIELDS = "driver_fields";
    private static final String COLUMN_LAST_MODIFIED = "last_modified";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STORAGE_TYPE = "storage_type";

    private static final DatasetRepository.DatasetMapper MAPPER = new DatasetRepository.DatasetMapper();

    public DatasetRepository(@Autowired ReactiveCassandraTemplate reactiveCassandraTemplate) {
        this.reactiveCassandraTemplate = reactiveCassandraTemplate;
        this.reactiveCqlOperations = this.reactiveCassandraTemplate.getReactiveCqlOperations();
    }

    public Flux<Dataset> findByDatasetKeyNameAndDatasetKeyVersion(String name, String version) {
        Select select = QueryBuilder.select().from(KEYSPACE, DATASET_TABLENAME);
        select.where(QueryBuilder.eq(COLUMN_NAME, name)).and(QueryBuilder.eq(COLUMN_VERSION, version))
                .setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
        return reactiveCqlOperations.query(select, MAPPER);
    }

    public Mono<Boolean> update(Dataset dataset) {
        Update update = QueryBuilder.update(KEYSPACE, DATASET_TABLENAME);
        update.with(QueryBuilder.set(COLUMN_DESCRIPTION, dataset.getDescription()))
                .and(QueryBuilder.set(COLUMN_DRIVER_FIELDS, dataset.getDriverFields()))
                .and(QueryBuilder.set(COLUMN_LAST_MODIFIED, dataset.getLastModified()))
                .and(QueryBuilder.set(COLUMN_STORAGE_TYPE, dataset.getStorageType()));
        update.where(QueryBuilder.eq(COLUMN_NAME, dataset.getDatasetKey().getName()))
                .and(QueryBuilder.eq(COLUMN_VERSION, dataset.getDatasetKey().getVersion()));

        return reactiveCqlOperations.execute(update);
    }

    private static class DatasetMapper implements RowMapper<Dataset> {

        DatasetMapper() {
            super();
        }

        @Override
        public Dataset mapRow(Row row, int rowNum) {
            Dataset ret = new Dataset();
            DatasetKey datasetKey = new DatasetKey();
            datasetKey.setName(row.getString(COLUMN_NAME));
            datasetKey.setVersion(row.getString(COLUMN_VERSION));
            ret.setDatasetKey(datasetKey);
            ret.setDescription(row.getString(COLUMN_DESCRIPTION));
            ret.setDriverFields(row.getSet(COLUMN_DRIVER_FIELDS, String.class));
            ret.setLastModified(row.getTimestamp(COLUMN_LAST_MODIFIED));
            return ret;
        }
    }

}
