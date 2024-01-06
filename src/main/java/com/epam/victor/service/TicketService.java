package com.epam.victor.service;

import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;

import java.util.List;

public interface TicketService {
    Ticket book(long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBooked(User user, int pageSize, int pageNum);

    List<Ticket> getBooked(Event event, int pageSize, int pageNum);

    boolean cancel(long ticketId);

    Ticket getById(long id);
}
