package com.epam.victor.repository.impl;

import com.epam.victor.model.Ticket;
import com.epam.victor.repository.BookingRepository;
import com.epam.victor.storage.ObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends BookingRepository<Ticket> {
    @Autowired
    public TicketRepository(ObjectStorage objectStorage) {
        super(Ticket.class, objectStorage);
    }
}
