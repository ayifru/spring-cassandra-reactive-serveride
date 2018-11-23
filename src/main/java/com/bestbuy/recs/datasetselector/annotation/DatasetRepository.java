package com.bestbuy.recs.datasetselector.annotation;

import com.bestbuy.recs.datasetselector.annotation.domain.Dataset;
import com.bestbuy.recs.datasetselector.annotation.domain.DatasetKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "datasetRepositoryAnnotation")
public interface DatasetRepository extends ReactiveCassandraRepository<Dataset, DatasetKey> {

}
