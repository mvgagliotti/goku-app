package com.goku.gokubackend.infrastructure.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

public class JacksonUtils {

    public static <T> Module createDeserializerFor(Class<T> clazz, Function<JsonNode, T> converter) {

        SimpleModule module = new SimpleModule();

        module.addDeserializer(clazz, new JsonDeserializer<T>() {
            @Override
            public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                JsonNode node = p.getCodec().readTree(p);
                return converter.apply(node);
            }
        });

        return module;
    }
}
