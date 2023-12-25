package com.epam.victor;

import com.epam.victor.config.SpringConfig;
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
import java.util.Date;

public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        userRepository.update(new User(3L,"USER333","user3@email.com"));
        System.out.println(userRepository.findAll());
        System.out.println(userRepository.findById(1L));
        EventRepository eventRepository = context.getBean(EventRepository.class);
        //eventRepository.create(new Event(7L,"Event7", Instant.now()));
        System.out.println(eventRepository.findAll());
        System.out.println(eventRepository.findById(1L));
        TicketRepository ticketRepository = context.getBean(TicketRepository.class);
        System.out.println(ticketRepository.findAll());
        System.out.println(ticketRepository.findById(1L));
       // ticketRepository.create(new Ticket(5L,2L, 3L, Ticket.Category.STANDARD,17));
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));

//
//        System.out.println(naruto);
//
//        context.close();

    }
}
