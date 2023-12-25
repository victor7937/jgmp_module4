package com.epam.victor;

import com.epam.victor.config.SpringConfig;
import com.epam.victor.facade.BookingFacade;
import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.epam.victor.repository.BookingRepository;
import com.epam.victor.repository.impl.EventRepository;
import com.epam.victor.repository.impl.TicketRepository;
import com.epam.victor.repository.impl.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookingFacade bookingFacade = context.getBean(BookingFacade.class);
        System.out.println(bookingFacade.getEventsForDay(LocalDate.of(2023,12,23),5,0));
        System.out.println(bookingFacade.bookTicket(1,5,11, Ticket.Category.BAR));
        System.out.println(bookingFacade.getBookedTickets(bookingFacade.getUserById(1L),5,0));
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));
    }
}
