package com.epam.victor;

import com.epam.victor.config.SpringConfig;
import com.epam.victor.facade.BookingFacade;
import com.epam.victor.model.Event;
import com.epam.victor.model.Ticket;
import com.epam.victor.model.User;
import com.epam.victor.service.exception.IdNotFoundException;
import com.epam.victor.storage.ObjectStorage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class FacadeTest {

    @Value("${booking.json.path}")
    public String filePath;

    @Value("${booking.json.path.source}")
    public String fileSource;


    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private ObjectStorage objectStorage;

    @Test
    void contextShouldBeUp(){}

    @Nested
    class EventTests {

        private static final List<Event> EVENT_COLLECTION = List.of(
                new Event(1L,"Event1", Instant.parse("2024-01-01T00:00:00Z")),
                new Event(2L,"Event2", Instant.parse("2024-01-02T00:00:00Z")),
                new Event(3L,"Event3", Instant.parse("2024-01-01T00:00:00Z")),
                new Event(4L,"Event4", Instant.parse("2024-01-02T00:00:00Z")),
                new Event(5L,"Event4", Instant.parse("2024-01-03T00:00:00Z"))
        );

        @Test
        void eventShouldBeGotById(){
            Event expected = EVENT_COLLECTION.getFirst();
            Event actual = bookingFacade.getEventById(1L);
            assertEquals(expected, actual);
        }

        @Test
        void notExistingIdShouldThrowException(){
            assertThrows(IdNotFoundException.class, () -> bookingFacade.getEventById(6L));
        }

        @Test
        void allEventsOfDayShouldBeTaken(){
            List<Event> expected = List.of(EVENT_COLLECTION.get(1), EVENT_COLLECTION.get(3));
            List<Event> actual = bookingFacade.getEventsForDay(LocalDate.of(2024,01,02),5,0);
            assertEquals(expected, actual);
        }

        @Test
        void allEventsOfTitleShouldBeTaken(){
            String title = "Event4";
            List<Event> expected = EVENT_COLLECTION.stream().filter(e -> e.getTitle().equals(title)).toList();
            List<Event> actual = bookingFacade.getEventsByTitle(title,5,0);
            assertEquals(expected, actual);
        }

        @Test
        void eventShouldBeCreated() {
            Event newEvent = new Event(6L,"Event6", Instant.now());
            bookingFacade.createEvent(newEvent);
            Event actual = bookingFacade.getEventById(6L);
            assertEquals(newEvent, actual);
        }

        @Test
        void eventShouldBeUpdated() {
            Event current = bookingFacade.getEventById(5L);
            Event updated = new Event(current.getId(), current.getTitle() + "_updated",current.getDate());
            bookingFacade.updateEvent(updated);
            assertEquals(updated, bookingFacade.getEventById(5L));
        }

        @Test
        void eventShouldBeDeleted (){
            Long eventId = 4L;
            assertDoesNotThrow(() -> bookingFacade.getEventById(eventId));
            boolean deleted = bookingFacade.deleteEvent(eventId);
            assertTrue(deleted);
            assertThrows(IdNotFoundException.class, () -> bookingFacade.getEventById(eventId));
        }

    }

    @Nested
    class UserTest{

        private static final List<User> USER_COLLECTION = List.of(
                new User(1L,"User1","user1@mail.com"),
                new User(2L,"User2","user2@mail.com"),
                new User(3L,"User3","user3@mail.com"),
                new User(4L,"User3","user4@mail.com")
        );
        @Test
        void userShouldBeGetById(){
            User expected = USER_COLLECTION.getFirst();
            User actual = bookingFacade.getUserById(1L);
            assertEquals(expected, actual);
        }

        @Test
        void notExistingIdShouldThrowException(){
            assertThrows(IdNotFoundException.class, () -> bookingFacade.getUserById(5L));
        }

        @Test
        void userShouldBeCreated() {
            User newUser = new User(5L,"User5","user5@mail.com");
            bookingFacade.createUser(newUser);
            User actual = bookingFacade.getUserById(5L);
            assertEquals(newUser, actual);
        }

        @Test
        void userShouldBeUpdated() {
            User current = bookingFacade.getUserById(4L);
            User updated = new User(current.getId(), current.getName() + "_updated", current.getEmail());
            bookingFacade.updateUser(updated);
            assertEquals(updated, bookingFacade.getUserById(4L));
        }

        @Test
        void userShouldBeDeleted (){
            Long userId = 4L;
            assertDoesNotThrow(() -> bookingFacade.getUserById(userId));
            boolean deleted = bookingFacade.deleteUser(userId);
            assertTrue(deleted);
            assertThrows(IdNotFoundException.class, () -> bookingFacade.getUserById(userId));
        }

        @Test
        void userShouldBeGotByEmail(){
            User expected = USER_COLLECTION.getFirst();
            User actual = bookingFacade.getUserByEmail(expected.getEmail());
            assertEquals(expected, actual);
        }

        @Test
        void usersShouldBeGotByName(){
            String userName = "User3";
            List<User> expected = USER_COLLECTION.stream().filter(u -> u.getName().equals("User3")).toList();
            List<User> actual = bookingFacade.getUsersByName(userName,5,0);
            assertEquals(expected, actual);
        }

    }

    @Nested
    class TicketTest{

        private static final List<Ticket> TICKET_COLLECTION = List.of(
                new Ticket(1L, 1L, 1L, Ticket.Category.PREMIUM, 11),
                new Ticket(2L, 2L, 1L, Ticket.Category.BAR, 22),
                new Ticket(3L, 2L, 3L, Ticket.Category.BAR, 22),
                new Ticket(4L, 2L, 1L, Ticket.Category.BAR, 23)
        );

        @Test
        void ticketShouldBeGotById (){
            Ticket expected = TICKET_COLLECTION.getFirst();
            Ticket actual = bookingFacade.getTicketById(1L);
            assertEquals(expected, actual);
        }

        @Test
        void notExistingIdShouldThrowException(){
            assertThrows(IdNotFoundException.class, () -> bookingFacade.getTicketById(5L));
        }
        @Test
        void ticketShouldBeBooked(){
            Long userId = 1L;
            Long eventId = 2L;
            Integer place = 23;
            Ticket.Category category = Ticket.Category.BAR;
            Ticket newTicket = bookingFacade.bookTicket(userId, eventId, place, category);
            assertEquals(new Ticket(newTicket.getId(), eventId, userId, category, place), newTicket);
            assertEquals(bookingFacade.getTicketById(newTicket.getId()), newTicket);
        }

        @Test
        void bookedByUserTicketsShouldBeTaken(){
            Long userId = 1L;
            List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getUserById(userId),5,0);
            List<Ticket> expected = TICKET_COLLECTION.stream().filter(t -> t.getUserId().equals(userId)).toList();
            assertEquals(expected, actual);
        }

        @Test
        void bookedTicketsOfEventShouldBeTaken(){
            Long eventId = 2L;
            List<Ticket> actual = bookingFacade.getBookedTickets(bookingFacade.getEventById(eventId),5,0);
            List<Ticket> expected = TICKET_COLLECTION.stream().filter(t -> t.getEventId().equals(eventId)).toList();
            assertEquals(expected, actual);
        }

        @Test
        void ticketShouldBeCanceled (){
            Long ticketId = 4L;
            assertDoesNotThrow(() -> bookingFacade.getTicketById(ticketId));
            boolean deleted = bookingFacade.cancelTicket(ticketId);
            assertTrue(deleted);
            assertThrows(IdNotFoundException.class, () -> bookingFacade.getTicketById(ticketId));
        }
    }



    @BeforeEach
    void storageStateToSource() throws IOException {
        Files.copy(Path.of(fileSource), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        objectStorage.initStorage();
    }



}
