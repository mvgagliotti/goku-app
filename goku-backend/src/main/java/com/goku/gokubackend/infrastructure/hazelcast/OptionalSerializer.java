package com.goku.gokubackend.infrastructure.hazelcast;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;
import java.util.Optional;

public class OptionalSerializer implements StreamSerializer<Optional> {

    @Override
    public void write(ObjectDataOutput out, Optional object) throws IOException {
        if (object.isPresent()) {
            out.writeObject(object.get());
        } else {
            out.writeObject(null);
        }
    }

    @Override
    public Optional read(ObjectDataInput in) throws IOException {
        Object result = in.readObject();
        return result == null ? Optional.empty() : Optional.of(result);
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {

    }
}