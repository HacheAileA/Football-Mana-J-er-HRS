package com.kahrs.controller;

import com.kahrs.app.CalendarManager;
import com.kahrs.model.CalendarEvent;
import com.kahrs.model.GameModel;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe CalendarController qui gère le système de calendrier.
 *
 * @author Ruben FOALEM
 *
 * @since 0.2
 *
 * @version 0.1
 */
public class CalendarController {

    // ==================== ATTRIBUTS ====================

    /** Liste de tous les évènements de la saison */
    private ArrayList<CalendarEvent> events;
    /** Model du jeu */
    private GameModel model;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de GameCalendar pour créer un objet avec un events(ArrayList) et le model(GameModel).
     * 
     * @param model GameModel
     *
     * @since 0.2
     */
    public CalendarController(GameModel model) {
        this.events = new ArrayList<>();
        this.model = model;
        this.newSeason(this.model.getCurrentDate().getYear());
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récupérer la liste de tous les évènements de la saison.
     *
     * @return la liste de tous les évènements de la saison
     *
     * @since 0.2
     */
    public ArrayList<CalendarEvent> getEvents() {
        return events;
    }

    /**
     * Setter pour définir la liste de tous les évènements de la saison.
     *
     * @param events La nouvelle liste de tous les évènements de la saison
     *
     * @since 0.2
     */
    public void setEvents(ArrayList<CalendarEvent> events) {
        this.events = events;
    }

    /**
     * Méthode pour ajouter un évènement
     *
     * @param event évènement à ajouter
     *
     * @since 0.2
     */
    public void addEvent(CalendarEvent event){
        if (this.isDayFree(event.getDate())) {
            events.add(event);
        } else {
            System.out.println("Jour de Championnat");
        }
    }

    /**
     * Méthode pour supprimer un évènement
     *
     * @param event évènement à supprimer
     *
     * @since 0.2
     */
    public void removeEvent(CalendarEvent event){
        events.remove(event);
    }

    /**
     * Méthode pour aller au prochain jour
     *
     * @since 0.2
     */
    public void nextDay() {
        LocalDate currentDate = this.model.getCurrentDate();
        LocalDate nextDate = currentDate.plusDays(1);
        LocalDate seasonEndDate = LocalDate.of(currentDate.getYear(), 4, 30);

        if (currentDate.isAfter(seasonEndDate)) {
            this.model.setCurrentDate(nextDate);
        } else if (nextDate.isAfter(seasonEndDate)) {
            this.model.setCurrentDate(LocalDate.of(currentDate.getYear(), 8, 1));
            this.newSeason(currentDate.getYear());
        } else {
            this.model.setCurrentDate(nextDate);
        }
    }

    /**
     * Méthode pour récupérer l'évènement à un certain index
     *
     * @param index indice
     *
     * @return l'évènement à l'indice i
     *
     * @since 0.2
     */
    public CalendarEvent getIndexCalendarEvent(int index) {
        CalendarEvent event = events.get(index);
        return event;
    }

    /**
     * Méthode pour avancer de plusieurs jours
     *
     * @param days le nombre de jours passer
     *
     * @since 0.2
     */
    public void advanceDay(int days) {
        for (int i = 0; i < days; i++) {
            nextDay();
        }
    }

    /**
     * Méthode pour vérifier que le jour sélectionné est libre
     *
     * @param date Jour sélectionné
     *
     * @return boolean
     *
     * @since 0.2
     */
    public boolean isDayFree(LocalDate date) {
        for (CalendarEvent day : this.getEvents()) {
            if (day.getDate().equals(date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode pour lancer une nouvelle saison selon uné anneée sélectionnée
     *
     * @param year année sélectionnée
     *
     * @since 0.2
     */
    public void newSeason(int year) {
        this.events.clear();
        ArrayList<CalendarEvent> saveCalendar = CalendarManager.getCalendarEvents();
        if (this.isCalendarValid(saveCalendar) && saveCalendar.get(0).getDate().getYear() == year) {
            this.setEvents(saveCalendar);
        } else {
            this.createSeason(year);
        }
    }

    /**
     * Méthode pour vérifier que le calendrier sauvegardé est valide
     *
     * @param saveCalendar calendrier sauvegardé
     *
     * @return boolean
     *
     * @since 0.2
     */
    public boolean isCalendarValid(ArrayList<CalendarEvent> saveCalendar){
        if (saveCalendar.isEmpty() || saveCalendar.size() < 50) {
            return false;
        }
        for (CalendarEvent event : saveCalendar) {
            if ( event.getEvent() == null || event.getDate() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode pour créer une saison selon l'année sélectionnée
     *
     * @param year l'année sélectionnée
     *
     * @since 0.2
     */
    public void createSeason(int year) {
        LocalDate startSeasonDay = LocalDate.of(year, 8, 1);
        int week = 0;
        while (week < 38) {
            if (week == 0) {
                this.addMarket(startSeasonDay, week);
            } else if ( week < 21 ) {
                this.addMatchChampionship(startSeasonDay,week);
            } else if (week == 21) {
                this.addMarket(startSeasonDay, week);
            } else {
                this.addMatchChampionship(startSeasonDay,week);
            }
            week++;
        }
    }

    /**
     * Méthode pour ajouter un évènemenent de type championnat à la date et la semaine sélectionnée
     *
     * @param startSeasonDay date sélectionnée
     * @param weeks semaine sélectionnée
     *
     * @since 0.2
     */
    public void addMatchChampionship(LocalDate startSeasonDay, int weeks){
        LocalDate startday =  startSeasonDay.plusWeeks(weeks);
        this.events.add(new CalendarEvent(CalendarEvent.Event.CHAMPIONSHIPMATCH, startday.plusDays(5)));
    }

    /**
     * Méthode pour ajouter un évènemenent de type marché à la date et la semaine sélectionnée
     *
     * @param startSeasonDay date sélectionnée
     * @param weeks semaine sélectionnée
     *
     * @since 0.2
     */
    public void addMarket(LocalDate startSeasonDay, int weeks){
        LocalDate startday =  startSeasonDay.plusWeeks(weeks);
        for (int i = 0; i < 7; i++) {
            this.events.add(new CalendarEvent(CalendarEvent.Event.MARKET, startday.plusDays(i)));
        }
    }

    /**
     * Méthode pour sauvegarder le calendrier et la date du jeu
     *
     * @since 0.2
     */
    public void saveCalendar() {
        CalendarManager.setCalendarEvents(this.events);
        CalendarManager.setDateGame(this.model.getCurrentDate());
    }

    /**
     * Méthode envoyant un boolean en lien avec le type d'événement de la journée
     *
     * @param type : Le type d'événements
     *
     * @return boolean
     *
     * @since 0.2
     *
     */
    public boolean canUse(CalendarEvent.Event type ) {
        LocalDate currentDay = this.model.getCurrentDate();
        for (CalendarEvent date : this.getEvents()) {
            if (date.getDate().isEqual(currentDay)) {
                switch (date.getEvent()) {
                    case CHAMPIONSHIPMATCH:
                        return type == CalendarEvent.Event.CHAMPIONSHIPMATCH;
                    case REST:
                        return false;
                    case FRIENDLYMATCH:
                        return type == CalendarEvent.Event.FRIENDLYMATCH;
                    case TRAINING:
                        return type == CalendarEvent.Event.TRAINING;
                    case MARKET:
                        return type == CalendarEvent.Event.MARKET;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    /**
     * Méthode envoyant un boolean selon que le jour actuel a un événement ou non
     *
     * @return boolean
     *
     * @since 0.2
     *
     */
    public boolean hasNotEventToday(){
        LocalDate currentDay = this.model.getCurrentDate();
        for (CalendarEvent date : this.getEvents()) {
            if (date.getDate().isEqual(currentDay)) {
                return true;
            }
        }
        return false;
    }

}
