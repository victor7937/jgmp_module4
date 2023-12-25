package com.epam.victor.service.impl;

import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.epam.victor.repository.impl.TicketRepository;
import com.epam.victor.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket book(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new Ticket(ticketRepository.newId(), eventId, userId, category, place);
        ticketRepository.create(ticket);
        return ticketRepository.findById(ticket.getId()).get();
    }

    @Override
    public List<Ticket> getBooked(User user, int pageSize, int pageNum) {
        return ticketRepository.findAllOfPageWithCondition(pageSize, pageNum,
                t -> t.getUserId().equals(user.getId()));
    }

    @Override
    public List<Ticket> getBooked(Event event, int pageSize, int pageNum) {
        return ticketRepository.findAllOfPageWithCondition(pageSize, pageNum,
                t -> t.getEventId().equals(event.getId()));
    }

    @Override
    public boolean cancel(long ticketId) {
        if (!ticketRepository.existsById(ticketId)){
            return false;
        }
        ticketRepository.delete(ticketId);
        return true;
    }
}
