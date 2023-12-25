package com.epam.victor.service.impl;

import com.epam.victor.model.Event;
import com.epam.victor.repository.impl.EventRepository;
import com.epam.victor.service.EventService;
import com.epam.victor.service.exception.IdAlreadyExistException;
import com.epam.victor.service.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Component
public class EvenServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EvenServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new IdNotFoundException("Event with id " + id + "not found"));
    }

    @Override
    public List<Event> getByTitle(String title, int pageSize, int pageNum) {
        return eventRepository.findAllOfPageWithCondition(pageSize, pageNum, e -> e.getTitle().equals(title));
    }

    @Override
    public List<Event> getOfDate(LocalDate day, int pageSize, int pageNum) {
        return eventRepository.findAllOfPageWithCondition(pageSize, pageNum,
                e -> LocalDate.ofInstant(e.getDate(), ZoneOffset.UTC).equals(day));
    }

    @Override
    public Event create(Event event) {
        Long id = event.getId();
        if (id == null){
            id = eventRepository.newId();
        }
        else if (eventRepository.existsById(id)){
            throw new IdAlreadyExistException("Event with id " + id + " already exist");
        }
        eventRepository.create(event);
        return eventRepository.findById(id).get();
    }

    @Override
    public Event update(Event event) {
        eventRepository.update(event);
        return eventRepository.findById(event.getId()).get();
    }

    @Override
    public boolean delete(long eventId) {
        if (!eventRepository.existsById(eventId)){
            return false;
        }
        eventRepository.delete(eventId);
        return true;
    }
}
