package com.bestbuy.recs.datasetselector;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

public class UserTypeCodecRegistrar {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeCodecRegistrar.class);
    private final Cluster cluster;
    private final List<UserTypeCodecRegistration> registrations;

    public UserTypeCodecRegistrar(Cluster cluster, List<UserTypeCodecRegistration> registrations) {
        this.cluster = cluster;
        this.registrations = registrations;
    }

    @PostConstruct
    public void onPostConstrcut() {
        this.registerUserTypes();
    }

    private void registerUserType(UserTypeCodecRegistration registration) {
        try {
            CodecRegistry codecRegistry = this.cluster.getConfiguration().getCodecRegistry();
            UserType userType = this.cluster.getMetadata().getKeyspace(registration.getKeyspace()).getUserType(registration.getUserType());
            TypeCodec<UDTValue> innerUserTypeCodec = codecRegistry.codecFor(userType);
            TypeCodec<?> itemTypeCodec = registration.getTypeCodec(innerUserTypeCodec);
            codecRegistry.register(itemTypeCodec);
            LOGGER.debug("registered user type {}", registration);
        } catch (Exception var6) {
            LOGGER.error("error registering user type {}", registration, var6);
        }

    }

    private void registerUserTypes() {
        LOGGER.info("registering user type codecs");
        Iterator var1 = this.registrations.iterator();

        while(var1.hasNext()) {
            UserTypeCodecRegistration registration = (UserTypeCodecRegistration)var1.next();
            this.registerUserType(registration);
        }

    }

}
