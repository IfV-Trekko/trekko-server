package com.trekko.api.utils;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {
    @Override
    public void serialize(final ObjectId objectId, final JsonGenerator generator, final SerializerProvider serializers)
            throws IOException {
        generator.writeString(objectId.toString());
    }
}
