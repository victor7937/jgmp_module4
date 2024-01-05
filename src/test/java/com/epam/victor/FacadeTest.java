package com.epam.victor;

import com.epam.victor.config.SpringConfig;
import com.epam.victor.facade.BookingFacade;
import com.epam.victor.model.Event;
import com.epam.victor.service.exception.IdNotFoundException;
import com.epam.victor.storage.ObjectStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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


    private static final List<Event> EVENT_COLLECTION = List.of(
            new Event(1L,"Event1", Instant.parse("2024-01-01T00:00:00Z")),
            new Event(2L,"Event2", Instant.parse("2024-01-02T00:00:00Z")),
            new Event(3L,"Event3", Instant.parse("2024-01-01T00:00:00Z")),
            new Event(4L,"Event4", Instant.parse("2024-01-02T00:00:00Z")),
            new Event(5L,"Event4", Instant.parse("2024-01-03T00:00:00Z"))
    );

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private ObjectStorage objectStorage;

    @Test
    void contextShouldBeUp(){}

    @Test
    void getEventByIdTest(){
        Event expected = EVENT_COLLECTION.getFirst();
        Event actual = bookingFacade.getEventById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getEventsForDayTest(){
        List<Event> expected = List.of(EVENT_COLLECTION.get(1), EVENT_COLLECTION.get(3));
        List<Event> actual = bookingFacade.getEventsForDay(LocalDate.of(2024,01,02),5,0);
        assertEquals(expected, actual);
    }

    @Test
    void getEventsByTitleTest(){
        List<Event> expected = List.of(EVENT_COLLECTION.get(3), EVENT_COLLECTION.get(4));
        List<Event> actual = bookingFacade.getEventsByTitle("Event4",5,0);
        assertEquals(expected, actual);
    }

    @Test
    void createEventTest() {
        Event newEvent = new Event(6L,"Event6", Instant.now());
        bookingFacade.createEvent(newEvent);
        Event actual = bookingFacade.getEventById(6L);
        assertEquals(newEvent, actual);
    }

    @Test
    void updateEventTest() {
        Event current = bookingFacade.getEventById(5L);
        Event updated = new Event(current.getId(), current.getTitle() + "_updated",current.getDate());
        bookingFacade.updateEvent(updated);
        assertEquals(updated, bookingFacade.getEventById(5L));
    }

    @Test
    void deleteEventTest(){
        assertDoesNotThrow(() -> bookingFacade.getEventById(5L));
        boolean deleted = bookingFacade.deleteEvent(5L);
        assertTrue(deleted);
        assertThrows(IdNotFoundException.class, () -> bookingFacade.getEventById(5L));
    }


    @AfterEach
    void storageStateToSource() throws IOException {
        Files.copy(Path.of(fileSource), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        objectStorage.initStorage();
    }





}
