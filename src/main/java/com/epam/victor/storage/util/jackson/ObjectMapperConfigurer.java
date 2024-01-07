package com.epam.victor.storage.util.jackson;

import com.epam.victor.model.BookingEntity;
import com.epam.victor.storage.ObjectStorage;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ObjectMapperConfigurer {

    public ObjectMapper getObjectMapper (){
        log.info("Object Mapper configuration start");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.addMixIn(BookingEntity.class, BookingEntityMixin.class);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        MapDeserializer mapDeserializer = new MapDeserializer();
        mapDeserializer.setObjectMapper(objectMapper);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Map.class, mapDeserializer);
        objectMapper.registerModule(module);
        log.info("Deserializer module was registered");
        log.info("Object Mapper was configured successfully");
        return objectMapper;
    }

    public static class MapDeserializer extends StdDeserializer<Map<String, List<BookingEntity>>> {

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


}
