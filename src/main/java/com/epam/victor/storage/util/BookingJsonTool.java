package com.epam.victor.storage.util;

import com.epam.victor.model.BookingEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class BookingJsonTool {

    private final ObjectMapper objectMapper;

    @Autowired
    public BookingJsonTool(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void toJson (Map<String, List<BookingEntity>> map, String path) throws IOException {
        List<? extends BookingEntity> entityList = map
                .values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
        objectMapper.writerFor(new TypeReference<List<BookingEntity>>() {}).writeValue(new File(path), entityList);
    }

    public Map<String, List<BookingEntity>> fromJson (String path) throws IOException {
        return objectMapper.readValue(new File(path), new TypeReference<>() {
        });
    }


}
