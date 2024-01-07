package com.epam.victor.storage.util;

import com.epam.victor.model.BookingEntity;
import com.epam.victor.storage.ObjectStorage;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;



public class MapDeserializer extends StdDeserializer<Map<String, List<BookingEntity>>> {

    public static final String TYPE_FIELD = "@type";
    private ObjectMapper objectMapper = new ObjectMapper();

    public MapDeserializer() {
        this(null);
    }

    protected MapDeserializer(Class<?> vc) {
        super(vc);
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, List<BookingEntity>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Map<String, List<BookingEntity>> entityMap = ObjectStorage.createEmptyMap();
        if (node.isArray()){
            for (JsonNode jsonNode : node){
                BookingEntity bookingEntity = objectMapper.treeToValue(jsonNode, BookingEntity.class);
                entityMap.get(jsonNode.get(TYPE_FIELD).asText()).add(bookingEntity);
            }
        }
        return entityMap;
    }
}
