package com.epam.victor.repository.impl;

import com.epam.victor.model.Event;
import com.epam.victor.repository.BookingRepository;
import com.epam.victor.storage.ObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository extends BookingRepository<Event> {

    @Autowired
    public EventRepository(ObjectStorage objectStorage) {
        super(Event.class, objectStorage);
    }
}
