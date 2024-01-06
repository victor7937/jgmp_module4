package com.epam.victor.storage.util;

import com.epam.victor.model.BookingEntity;
import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.victor.storage.ObjectStorage.Classes.EVENT;
import static com.epam.victor.storage.ObjectStorage.Classes.TICKET;
import static com.epam.victor.storage.ObjectStorage.Classes.USER;


public class MapDeserializer extends StdDeserializer<Map<String, List<? extends BookingEntity>>> {

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
    public Map<String, List<? extends BookingEntity>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Map<String, List<? extends BookingEntity>> bookingEntityMap = new LinkedHashMap<>();
        putToMap(bookingEntityMap, (Class<Event>) EVENT.entityClass, node, new TypeReference<>() {});
        putToMap(bookingEntityMap, (Class<Ticket>) TICKET.entityClass, node, new TypeReference<>() {});
        putToMap(bookingEntityMap, (Class<User>) USER.entityClass, node, new TypeReference<>() {});
        return bookingEntityMap;
    }

    private <T extends BookingEntity> void putToMap(
            Map<String,
            List<? extends BookingEntity>> map,
            Class<T> tClass,
            JsonNode node,
            TypeReference<List<T>> typeReference
    ) throws JsonProcessingException {

        List<T> entityList = objectMapper.treeToValue(node.get(tClass.getName()), typeReference);
        map.put(tClass.getName(), entityList);
    }

}
