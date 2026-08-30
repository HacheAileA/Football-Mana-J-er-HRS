package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kahrs.controller.CalendarController;
import com.kahrs.model.CalendarEvent;
import com.kahrs.model.GameModel;

public class TestCalendarController {

    private ArrayList<CalendarEvent> calendarEvents;
    private LocalDate localDate;
    private CalendarController calendarController;
    private GameModel mockModel;

    @BeforeEach
    void setup() {
        calendarEvents = new ArrayList<>();
        localDate = LocalDate.of(2026,8,1);
        mockModel = new GameModel();

        calendarController = new CalendarController(mockModel);
        calendarController.setEvents(calendarEvents);
        mockModel.setCurrentDate(localDate);
    }

    @Test
    void testEvents() {
        assertEquals(calendarController.getEvents().size(), 0);
        ArrayList<CalendarEvent> events = new ArrayList<>();
        calendarController.setEvents(events);
        assertEquals(calendarController.getEvents(), events);

        CalendarEvent calendarEvent = new CalendarEvent(CalendarEvent.Event.FRIENDLYMATCH, LocalDate.of(2026, 8, 1));
        calendarController.addEvent(calendarEvent);
        assertEquals(calendarController.getEvents().size(), 1);

        calendarController.removeEvent(calendarEvent);
        assertEquals(calendarController.getEvents().size(), 0);
    }

    @Test
    void testDate() {
        assertEquals(mockModel.getCurrentDate(), localDate);
        LocalDate date = LocalDate.of(2023, 1, 1);
        mockModel.setCurrentDate(date);
        assertEquals(mockModel.getCurrentDate(), date);
    }

    @Test
    void testNextDay() {
        calendarController.nextDay();

        LocalDate localDate2 = localDate.plusDays(1);
        assertEquals(localDate2, mockModel.getCurrentDate());
    }

    @Test
    void testIndexCalendarEvent() {
        CalendarEvent event1 = new CalendarEvent(CalendarEvent.Event.CHAMPIONSHIPMATCH, LocalDate.of(2021,5,21));
        CalendarEvent event2 = new CalendarEvent(CalendarEvent.Event.TRAINING, LocalDate.of(2020,8,20));

        calendarController.addEvent(event1);
        calendarController.addEvent(event2);

        assertEquals(calendarController.getIndexCalendarEvent(0), event1);
        assertEquals(calendarController.getIndexCalendarEvent(1), event2);
    }

    @Test
    void testAdvanceDay() {
        int days = 5;
        LocalDate localDate2 = mockModel.getCurrentDate();
        calendarController.advanceDay(days);
        assertEquals(mockModel.getCurrentDate(), localDate2.plusDays(days));
    }

    @Test
    void testNewSeason() {
        LocalDate localDate2 = LocalDate.of(2026, 7, 31);
        mockModel.setCurrentDate(localDate2);
        calendarController.getEvents().add(new CalendarEvent(CalendarEvent.Event.REST, localDate2));

        calendarController.nextDay();

        assertEquals(mockModel.getCurrentDate(), LocalDate.of(2026, 8, 1));
        assertTrue(calendarController.getEvents().size() == 1);
        assertEquals(CalendarEvent.Event.REST, calendarController.getEvents().get(0).getEvent());
    }
}
