package com.kahrs.model;

import java.time.LocalDate;

/**
 * Classe CalendarEvent implémentant un event.
 *
 * @author Ruben FOALEM
 *
 * @since 0.2
 *
 * @version 0.1
 */
public class CalendarEvent {

    /**
     * Enum pour les évènements du calendrier
     */
    public enum Event {
        /** Evénement d'entrainement */    TRAINING,
        /** Evénement match amical */ FRIENDLYMATCH,
        /** Evénement match de championnat */ CHAMPIONSHIPMATCH,
        /** Evénement repos */   REST,
        /** Evénement marché */ MARKET
    }

    // ==================== ATTRIBUTS ====================

    /** Nom de l'évènement */
    private Event event;
    /** Date de l'évènement */
    private LocalDate date;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de CalendarEvent pour créer un objet avec un event(String) et une date(LocalDate).
     *
     * @param event Nom de l'évènement
     * @param date  Date de l'évènement
     *
     * @since 0.2
     */
    public CalendarEvent(Event event, LocalDate date) {
        this.event = event;
        this.date = date;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer le nom de l'évènement.
     *
     * @return Le nom de l'évènement
     *
     * @since 0.2
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Setter pour définir l'évènement.
     *
     * @param event Le nouveau évènement
     *
     * @since 0.2
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Getter pour récuperer la date de l'évènement.
     *
     * @return La date de l'évènement
     *
     * @since 0.2
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Setter pour définir la date de l'évènement.
     *
     * @param date La nouvelle date de l'évènement
     *
     * @since 0.2
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
