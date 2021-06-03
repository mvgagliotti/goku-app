package com.goku.gokubackend.infrastructure.hazelcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

public class BaseJacksonSerializer<T> implements StreamSerializer<T> {

    private final ObjectMapper mapper;
    private final Class<T> serializationClass;

    public BaseJacksonSerializer(ObjectMapper mapper, Class<T> serializationClass) {
        this.mapper = mapper;
        this.serializationClass = serializationClass;
    }

    @Override
    public void write(ObjectDataOutput out, T object) throws IOException {
        out.writeString(mapper.writeValueAsString(object));
    }

    @Override
    public T read(ObjectDataInput in) throws IOException {
        return mapper.readValue(in.readString(), serializationClass);
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
