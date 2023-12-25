package com.epam.victor.service;

import com.epam.victor.model.Event;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventService {
    Event getById(Long id);

    List<Event> getByTitle(String title, int pageSize, int pageNum);

    List<Event> getOfDate(LocalDate day, int pageSize, int pageNum);

    Event create(Event event);

    Event update(Event event);

    boolean delete(long eventId);


}
