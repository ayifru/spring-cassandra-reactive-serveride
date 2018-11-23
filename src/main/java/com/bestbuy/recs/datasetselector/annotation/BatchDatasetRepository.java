package com.bestbuy.recs.datasetselector.annotation;

import com.bestbuy.recs.datasetselector.annotation.domain.BatchDataset;
import com.bestbuy.recs.datasetselector.annotation.domain.BatchDatasetKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository("batchDatasetRepositoryAnnotation")
public interface BatchDatasetRepository extends ReactiveCassandraRepository<BatchDataset, BatchDatasetKey> {

}
