package com.bestbuy.recs.datasetselector;

import com.bestbuy.recs.datasetselector.annotation.BatchDatasetRepository;
import com.bestbuy.recs.datasetselector.annotation.domain.BatchDataset;
import com.bestbuy.recs.datasetselector.annotation.domain.BatchDatasetKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

    @Autowired @Qualifier("batchDatasetRepositoryAnnotation")
    private BatchDatasetRepository batchDatasetRepositoryAnnotation;

    @Autowired @Qualifier("batchDatasetRepositoryConvention")
    private com.bestbuy.recs.datasetselector.convention.BatchDatasetRepository batchDatasetRepositoryConvention;

    @PostMapping(path = "/convention/das/batchdataset", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings(value = "all")
    public String insertBatchDatasetByConvention(@RequestBody com.bestbuy.recs.datasetselector.convention.domain.BatchDataset batchDataset) {
        LocalDateTime start = LocalDateTime.now();
        LOGGER.info("Received rest message at: {}", start);
        batchDatasetRepositoryConvention.update(batchDataset).subscribe();
        LocalDateTime end = LocalDateTime.now();
        LOGGER.info("Completed insert at: {}", end);
        return "Start: " + start + ", End:" + end;
    }

    @PostMapping(path = "/annotation/das/batchdataset", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings(value = "all")
    public String insertBatchDatasetByAnnotation(@RequestBody BatchDataset batchDataset) {
        LocalDateTime start = LocalDateTime.now();
        LOGGER.info("Received rest message at: {}", start);
        batchDatasetRepositoryAnnotation.insert(batchDataset).subscribe();
        LocalDateTime end = LocalDateTime.now();
        LOGGER.info("Completed insert at: {}", end);
        return "Start: " + start + ", End:" + end;
    }

    @PostMapping(path = "/convention/das/batchdataset/find", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings(value = "all")
    public List<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset>
    getBatchDatasetByConvention(@RequestBody List<com.bestbuy.recs.datasetselector.convention.domain.BatchDatasetKey> batchDatasetKeys) {

        Mono<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> result1 =
                batchDatasetRepositoryConvention.findById(batchDatasetKeys.get(0));
        Mono<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> result2 =
                batchDatasetRepositoryConvention.findById(batchDatasetKeys.get(1));
        Mono<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> result3 =
                batchDatasetRepositoryConvention.findById(batchDatasetKeys.get(2));
        List<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> mergedFlux =
                Flux.concat(result1, result2, result3).collectList().block(Duration.ofMillis(20));

        return mergedFlux;
    }

    @PostMapping(path = "/convention/das/batchdataset/find/flux", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings(value = "all")
    public Flux<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset>
    getBatchDatasetByFlux(@RequestBody List<com.bestbuy.recs.datasetselector.convention.domain.BatchDatasetKey> batchDatasetKeys) {

        Mono<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> result1 =
                batchDatasetRepositoryConvention.findById(batchDatasetKeys.get(0));
        Mono<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> result2 =
                batchDatasetRepositoryConvention.findById(batchDatasetKeys.get(1));
        Mono<com.bestbuy.recs.datasetselector.convention.domain.BatchDataset> result3 =
                batchDatasetRepositoryConvention.findById(batchDatasetKeys.get(2));
        return Flux.concat(result1, result2, result3);
    }

    @PostMapping(path = "/annotation/das/batchdataset/find", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings(value = "all")
    public List<BatchDataset> getBatchDatasetByAnnotation(@RequestBody List<BatchDatasetKey> batchDatasetKeys) throws ExecutionException, InterruptedException {
        Mono<BatchDataset> result1 = batchDatasetRepositoryAnnotation.findById(batchDatasetKeys.get(0));
        Mono<BatchDataset> result2 = batchDatasetRepositoryAnnotation.findById(batchDatasetKeys.get(1));
        Mono<BatchDataset> result3 = batchDatasetRepositoryAnnotation.findById(batchDatasetKeys.get(2));
        return Flux.concat(result1, result2, result3).collectList().block(Duration.ofMillis(500));
    }

    @PostMapping(path = "/annotation/das/batchdataset/find/flux", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE},
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @SuppressWarnings(value = "all")
    @ResponseBody
    public Flux<BatchDataset> getBatchDatasetByAnnotationFlux(@RequestBody List<BatchDatasetKey> batchDatasetKeys)
            throws ExecutionException, InterruptedException {
        Flux<BatchDataset> result1 = batchDatasetRepositoryAnnotation.findAllById(batchDatasetKeys);
        return result1;
    }
}
