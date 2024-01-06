package com.epam.victor.storage;

import com.epam.victor.model.BookingEntity;
import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.epam.victor.storage.util.BookingJsonTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class ObjectStorage{

    @Value("${booking.json.path}")
    public String filePath;
    private Map<String, List<? extends BookingEntity>> entityMap;

    private final BookingJsonTool bookingJsonTool;

    @Autowired
    public ObjectStorage(BookingJsonTool bookingJsonTool) {
        this.bookingJsonTool = bookingJsonTool;
    }

    public enum Classes {
        TICKET(Ticket.class),
        EVENT(Event.class),
        USER(User.class);
        public final Class<? extends BookingEntity> entityClass;

        Classes(Class<? extends BookingEntity> clazz){
            this.entityClass = clazz;
        }

    }


    @PostConstruct
    public void initStorage(){
        try {
            entityMap = bookingJsonTool.fromJson(filePath);
            //System.out.println("Deserialize " + entityMap);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Jackson Exception", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void backupStorage(){
        try {
            //System.out.println(entityMap);
            bookingJsonTool.toJson(entityMap, filePath);
        } catch (JsonProcessingException e) {
                throw new RuntimeException("Jackson Exception", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<? extends BookingEntity> getEntityList(Class<? extends BookingEntity> clazz){
        return entityMap.get(clazz.getName());
    }

}
