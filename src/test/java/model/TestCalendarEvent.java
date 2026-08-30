package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kahrs.model.CalendarEvent;

public class TestCalendarEvent {
    
    private CalendarEvent.Event event;
    private LocalDate date;
    private CalendarEvent calendarEvent;

    @BeforeEach
    void setup() {
        event = CalendarEvent.Event.FRIENDLYMATCH;
        date = LocalDate.of(2023, 1, 1);
        calendarEvent = new CalendarEvent(event, date);
    }

    @Test
    void testConstructor() {
        assertEquals(calendarEvent.getEvent(), event);
        assertEquals(calendarEvent.getDate(), date);
    }

    @Test
    void testEvent() {
        assertEquals(calendarEvent.getEvent(), event);
        CalendarEvent.Event event2 = CalendarEvent.Event.TRAINING;
        calendarEvent.setEvent(event2);
        assertEquals(calendarEvent.getEvent(), event2);
    }

    @Test
    void testDate() {
        assertEquals(calendarEvent.getDate(), date);
        LocalDate date2 = LocalDate.of(2026, 12, 12);
        calendarEvent.setDate(date2);
        assertEquals(calendarEvent.getDate(), date2);
    }
}
