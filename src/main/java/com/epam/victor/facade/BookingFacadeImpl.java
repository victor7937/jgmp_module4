package com.epam.victor.facade;

import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.epam.victor.service.EventService;
import com.epam.victor.service.TicketService;
import com.epam.victor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class BookingFacadeImpl implements BookingFacade{


    private final EventService eventService;
    private final UserService userService;

    private final TicketService ticketService;

    @Autowired
    public BookingFacadeImpl(EventService eventService, UserService userService, TicketService ticketService) {
        this.eventService = eventService;
        this.userService = userService;
        this.ticketService = ticketService;
    }


    @Override
    public Event getEventById(Long id) {
        return eventService.getById(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum) {
        return eventService.getOfDate(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.create(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.update(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.delete(eventId);
    }

    @Override
    public User getUserById(Long id) {
        return userService.getById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        return userService.create(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userService.delete(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketService.book(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBooked(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBooked(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.cancel(ticketId);
    }
}
