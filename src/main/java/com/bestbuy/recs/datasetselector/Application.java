package com.bestbuy.recs.datasetselector;

import com.bestbuy.recs.datasetselector.convention.domain.Item;
import com.bestbuy.recs.datasetselector.convention.domain.ItemCodec;
import com.datastax.driver.core.Cluster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Collections;

@SpringBootApplication
@EnableReactiveCassandraRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public UserTypeCodecRegistration itemCodec(Cluster cluster) {
        UserTypeCodecRegistration userTypeCodecRegistration =
                new UserTypeCodecRegistration("das", "item",
                        (codec) -> new ItemCodec(codec, Item.class));

        UserTypeCodecRegistrar userTypeCodecRegistrar = new UserTypeCodecRegistrar(cluster,
                Collections.singletonList(userTypeCodecRegistration));

        userTypeCodecRegistrar.onPostConstrcut();

        return userTypeCodecRegistration;
    }

}