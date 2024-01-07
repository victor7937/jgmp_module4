package com.epam.victor.storage;

import com.epam.victor.model.BookingEntity;
import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.epam.victor.storage.util.jackson.BookingJsonTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ObjectStorage{

    @Value("${booking.json.path}")
    public String filePath;
    private Map<String, List<BookingEntity>> entityMap;

    private final BookingJsonTool bookingJsonTool;

    @Autowired
    public ObjectStorage(BookingJsonTool bookingJsonTool) {
        this.bookingJsonTool = bookingJsonTool;
    }

    @PostConstruct
    public void initStorage(){
        try {
            entityMap = bookingJsonTool.fromJson(filePath);
            log.info("Storage was initialized from json file " + filePath );
        } catch (JsonProcessingException e) {
            log.error("Jackson Exception while taking storage from the file ", e);
            throw new RuntimeException("Jackson Exception", e);
        } catch (IOException e) {
            log.error("IOException Exception while taking storage from the file ", e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void backupStorage(){
        try {
            bookingJsonTool.toJson(entityMap, filePath);
            log.info("Storage was backed up to json file " + filePath);
        } catch (JsonProcessingException e) {
            log.error("Jackson Exception while writing storage to the file ", e);
            throw new RuntimeException("Jackson Exception", e);
        } catch (IOException e) {
            log.error("IOException Exception while writing storage to the file ", e);
            throw new RuntimeException(e);
        }
    }

    public static Map<String, List<BookingEntity>> createEmptyMap(){
        Map<String, List<BookingEntity>> map = new HashMap<>();
        map.put(User.class.getName(), new ArrayList<>());
        map.put(Event.class.getName(), new ArrayList<>());
        map.put(Ticket.class.getName(), new ArrayList<>());
        return map;
    }

    public List<? extends BookingEntity> getEntityList(Class<? extends BookingEntity> clazz){
        return entityMap.get(clazz.getName());
    }

}
