package com.bestbuy.recs.datasetselector;

import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;

import java.util.function.Function;

public class UserTypeCodecRegistration {
    private final String keyspace;
    private final Function<TypeCodec<UDTValue>, TypeCodec<?>> newTypeCodec;
    private final String userType;

    public UserTypeCodecRegistration(String keyspace, String userType, Function<TypeCodec<UDTValue>, TypeCodec<?>> newTypeCodec) {
        this.keyspace = keyspace;
        this.userType = userType;
        this.newTypeCodec = newTypeCodec;
    }

    public String getKeyspace() {
        return this.keyspace;
    }

    public TypeCodec<?> getTypeCodec(TypeCodec<UDTValue> src) {
        return (TypeCodec)this.newTypeCodec.apply(src);
    }

    public String getUserType() {
        return this.userType;
    }

}
