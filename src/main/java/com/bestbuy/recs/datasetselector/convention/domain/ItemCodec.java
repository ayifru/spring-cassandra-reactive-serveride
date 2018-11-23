package com.bestbuy.recs.datasetselector.convention.domain;

import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;

import java.nio.ByteBuffer;

public class ItemCodec extends TypeCodec<Item> {
    private final TypeCodec<UDTValue> innerCodec;

    private final UserType userType;

    public ItemCodec(TypeCodec<UDTValue> innerCodec, Class<Item> javaType) {
        super(innerCodec.getCqlType(), javaType);
        this.innerCodec = innerCodec;
        this.userType = (UserType) innerCodec.getCqlType();
    }

    private static Item toItem(UDTValue value) {
        return value == null ? null : new Item(value.getString("sku"), value.getDouble("score"));
    }

    @Override
    public Item deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) {
        return toItem(innerCodec.deserialize(bytes, protocolVersion));
    }

    @Override
    public String format(Item value) {
        return value == null ? null : innerCodec.format(toUdtValue(value));
    }

    @Override
    public Item parse(String value) {
        return value == null || value.isEmpty() || value.equals(null) ? null : toItem(innerCodec.parse(value));
    }

    @Override
    public ByteBuffer serialize(Item value, ProtocolVersion protocolVersion) {
        return innerCodec.serialize(toUdtValue(value), protocolVersion);
    }

    private UDTValue toUdtValue(Item value) {
        return value == null ? null
                : userType.newValue().setString("sku", value.getSku()).setDouble("score", value.getScore());
    }
}
