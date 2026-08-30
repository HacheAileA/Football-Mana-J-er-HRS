package com.kahrs.view.panels;

import com.kahrs.model.CalendarEvent;
import com.kahrs.controller.CalendarController;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSLabels;
import com.kahrs.view.visuals.HRSLanguages;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Classe CalendrierPanel gérant l'affichage du calendrier.
 *
 * @author Ruben FOALEM
 *
 * @since 0.2
 *
 * @version 0.1
 */
public class CalendarPanel extends JPanel {

    /** La GameView principale */
    private GameView view;
    /** Calendrier */
    private CalendarController calendar;
    /** Bouton pour changer de jour */
    private JButton fastForward;
    /** Bouton pour changer de semaine */
    private JButton fastForwardWeek;
    /** Bouton pour revenir à l'écran d'accueil */
    private JButton closeButton;
    /** Bouton pour visiter le mois précédent */
    private JButton lastMonth;
    /** Bouton pour visiter le mois suivant */
    private JButton nextMonth;
    /** Liste de tous les panels contenus dans le calendrier*/
    private ArrayList<JPanel> dayPanels;
    /** Liste de toutes les dates contenues dans le calendrier*/
    private ArrayList<LocalDate> date;
    /** JPanel principal du calendrier */
    private JPanel calendarPanel;
    /** Date d'affichage **/
    private LocalDate viewDate;
    

    /**
     * Constructeur de CalendarPanel avec la GameView principale.
     *
     * @param view La GameView principale
     *
     *
     * @since 0.2
     */
    public CalendarPanel(GameView view) {
        this.view = view;
        this.viewDate = this.view.getModel().getCurrentDate();
        this.calendar = new CalendarController(this.view.model);

        this.setLayout(new BorderLayout());

        this.createButtons();
        this.initLayout();

    }

    /**
     * Méthode pour définir la viewDate.
     *
     * @param date La viewDate utilisée
     *
     * @since 0.2
     */
    public void setViewDate(LocalDate date) {
        this.viewDate = date;
    }

    /**
     * Getter pour récuperer le calendrier
     * 
     * @return Le calendrier
     * 
     * @since 0.2
     */
    public CalendarController getCalendar() {
        return this.calendar;
    }

    /**
     * Méthode pour créer les boutons de la page.
     *
     * @since 0.2
     */
    private void createButtons() {
        this.fastForward = new HRSButtons("▶");
        this.fastForward.addActionListener(e -> this.eventAvanceRapide(1));

        this.fastForwardWeek = new HRSButtons("▶▶");
        this.fastForwardWeek.addActionListener(e -> this.eventAvanceRapide(7));

        this.closeButton = new HRSButtons(HRSLanguages.getCloseButtonText());
        this.closeButton.addActionListener(e -> this.eventCloseButton());

        this.lastMonth = new HRSButtons("◀");
        this.lastMonth.addActionListener(e -> this.eventChangeMonth(-1));

        this.nextMonth = new HRSButtons("▶");
        this.nextMonth.addActionListener(e -> this.eventChangeMonth(1));


    }

    /**
     * Méthode pour créer le panel Calendar.
     *
     * @since 0.2
     */
    private void initLayout(){
        this.removeAll();

        this.topPanel();
        this.tableCalendarPanel();
        this.add(calendarPanel, BorderLayout.CENTER);
        this.bottomPanel();

        this.updateCalendar();
        this.revalidate();
        this.repaint();
    }

    /**
     * Méthode pour passer d'un jour selectionné à un autre
     * tout en activant et désactivant les boutons en lien avec l'event du jour
     * 
     * @param days Le nombre de jour
     *
     * @since 0.2
     */
    private void avanceRapide(int days) {

        this.calendar.advanceDay(days);

        LocalDate nextDay = this.view.model.getCurrentDate();

        this.view.getMainPanel().updateDate(nextDay);

        this.viewDate = nextDay;

        this.refreshCalendar();
    }

    /**
     * Méthode pour créer le haut du Panel Calendar
     *
     * @since 0.2
     */
    private void topPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(HRSColors.FM_DARK_GREEN);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.setBackground(HRSColors.FM_DARK_GREEN);

        for(int i = 1; i <= 7; i++){
            JLabel dayLabel = new HRSLabels(HRSLanguages.Calendar.getDayLabelText(i), JLabel.CENTER);
            headerPanel.add(dayLabel);
        }

        JPanel title = new JPanel(new GridLayout(1, 3));
        title.setBackground(HRSColors.FM_DARK_GREEN);
        JLabel month = new HRSLabels(HRSLanguages.Calendar.getMonthLabelText(this.viewDate.getMonth().getValue()), JLabel.CENTER);

        title.add(this.lastMonth);
        title.add(month);
        title.add(this.nextMonth);

        this.lastMonth.setEnabled(this.viewDate.getMonth().getValue() != 8);
        this.nextMonth.setEnabled(this.viewDate.getMonth().getValue() != 4);

        topPanel.add(title);
        topPanel.add(headerPanel);

        this.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Méthode pour créer le calendrier du Panel Calendar
     *
     * @since 0.2
     */
    private void tableCalendarPanel() {

        this.calendarPanel = new JPanel();
        this.calendarPanel.setLayout(new GridLayout(6,7));
        this.calendarPanel.setBackground(HRSColors.FM_DARK_GREEN);

        this.dayPanels = new ArrayList<>();
        this.date = new ArrayList<>();

        LocalDate begin = this.viewDate.withDayOfMonth(1);

        int numberDaysinMonth = begin.lengthOfMonth();

        LocalDate firstDay = begin;
        int offset = firstDay.getDayOfWeek().getValue() - 1;

        this.offsetStartCalendar(offset);

        for (int i = 0; i < numberDaysinMonth; i++){

            LocalDate currentDate = begin.plusDays(i);
            this.addDayPanel(currentDate);
            this.date.add(currentDate);

        }

        int totalCases = offset + numberDaysinMonth;
        this.offsetEndCalendar(totalCases);

    }

    /**
     * Méthode pour créer le bas du Panel Calendar
     *
     * @since 0.2
     */
    private void bottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(HRSColors.FM_DARK_GREEN);
        bottomPanel.setOpaque(true);

        bottomPanel.add(this.fastForward);
        bottomPanel.add(this.fastForwardWeek);
        bottomPanel.add(this.closeButton);

        this.add(bottomPanel, BorderLayout.SOUTH);


    }

    /**
     * Méthode pour ajouter un panel jour dans le calendrier du Panel Calendar
     *
     * @param currentDate Date à ajouter
     *
     * @since 0.2
     */
    private void addDayPanel(LocalDate currentDate) {
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel,BoxLayout.Y_AXIS));

        dayPanel.setBackground(HRSColors.FM_DARK_GREEN);
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel dayDate = new JLabel(currentDate.toString());
        dayDate.setForeground(HRSColors.FM_TEXT_WHITE);
        dayPanel.add(dayDate);

        for(CalendarEvent eventDate : calendar.getEvents()) {
            if (eventDate.getDate().isEqual(currentDate)) {
                boolean afterCurrentDate = eventDate.getDate().isAfter(this.view.getModel().getCurrentDate());
                switch (eventDate.getEvent()) {
                    case CHAMPIONSHIPMATCH:
                        JLabel championshipEvent = new JLabel(HRSLanguages.Calendar.getChampionshipMatchText());
                        championshipEvent.setForeground(HRSColors.FM_TEXT_WHITE);
                        dayPanel.add(championshipEvent);
                        break;
                    case REST:
                        JLabel restEvent = new JLabel(HRSLanguages.Calendar.getRestText());
                        restEvent.setForeground(HRSColors.FM_TEXT_WHITE);
                        dayPanel.add(restEvent);
                        if (afterCurrentDate) {
                            JButton cancelRestEvent = new HRSButtons("×");
                            cancelRestEvent.addActionListener(e -> this.eventCancelEvent(eventDate));
                            dayPanel.add(cancelRestEvent);
                        }
                        break;
                    case FRIENDLYMATCH:
                        JLabel friendlyMatchEvent = new JLabel(HRSLanguages.Calendar.getFriendlyMatchText());
                        friendlyMatchEvent.setForeground(HRSColors.FM_TEXT_WHITE);
                        dayPanel.add(friendlyMatchEvent);
                        if (afterCurrentDate) {
                            JButton cancelFriendlyMatchEvent = new HRSButtons("×");
                            cancelFriendlyMatchEvent.addActionListener(e -> this.eventCancelEvent(eventDate));
                            dayPanel.add(cancelFriendlyMatchEvent);
                        }
                        break;
                    case TRAINING:
                        JLabel trainingEvent = new JLabel(HRSLanguages.Calendar.getTrainingText());
                        trainingEvent.setForeground(HRSColors.FM_TEXT_WHITE);
                        dayPanel.add(trainingEvent);
                        if (afterCurrentDate) {
                            JButton cancelTrainingEvent = new HRSButtons("×");
                            cancelTrainingEvent.addActionListener(e -> this.eventCancelEvent(eventDate));
                            dayPanel.add(cancelTrainingEvent);
                        }
                        break;
                    case MARKET:
                        JLabel event = new JLabel(HRSLanguages.Calendar.getMarketText());
                        event.setForeground(HRSColors.FM_TEXT_WHITE);
                        dayPanel.add(event);
                        break;
                    default:
                        break;
                }
            }

        }

        if (this.calendar.isDayFree(currentDate)) {
            JButton addEvents = new HRSButtons("+");
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem training = new JMenuItem(HRSLanguages.Calendar.getTrainingText());
            JMenuItem rest = new JMenuItem(HRSLanguages.Calendar.getRestText());
            JMenuItem friendlyMatch = new JMenuItem(HRSLanguages.Calendar.getFriendlyMatchText());

            training.addActionListener(e -> this.eventAdd(currentDate, CalendarEvent.Event.TRAINING));
            rest.addActionListener(e ->this.eventAdd(currentDate, CalendarEvent.Event.REST));
            friendlyMatch.addActionListener(e ->this.eventAdd(currentDate, CalendarEvent.Event.FRIENDLYMATCH));

            popupMenu.add(training);
            popupMenu.add(rest);
            popupMenu.add(friendlyMatch);

            addEvents.addActionListener(e -> {
                popupMenu.show(addEvents, 0, addEvents.getHeight());
            });

            dayPanel.add(addEvents);
        }

        this.dayPanels.add(dayPanel);
        this.calendarPanel.add(dayPanel);

    }

    /**
     * Méthode pour faire le décalage du début du calendrier du Panel Calendar
     *
     * @param offset décalage
     *
     * @since 0.2
     */
    private void offsetStartCalendar(int offset) {
        for (int i = 0; i < offset; i++) {
            JPanel empty = new JPanel();
            empty.setBackground(HRSColors.FM_DARK_GREEN);
            calendarPanel.add(empty);
        }
    }

    /**
     * Méthode pour faire le décalage de fin du calendrier du Panel Calendar
     *
     * @param offset décalage
     *
     * @since 0.2
     */
    private void offsetEndCalendar(int offset) {
        for (int i = offset; i < 42; i++) {
            JPanel empty = new JPanel();
            empty.setBackground(HRSColors.FM_DARK_GREEN);
            this.calendarPanel.add(empty);
        }
    }


    /**
     * Méthode pour rafraichir le calendrier du Panel Calendar
     *
     * @since 0.2
     */
    public void refreshCalendar(){
        this.removeAll();
        this.initLayout();
        this.revalidate();
        this.repaint();
    }

    /**
     * Méthode pour afficher le jour sélectionné dans le calendrier du Panel Calendar
     *
     * @since 0.2
     */
    private void updateCalendar(){
        for(JPanel panel : this.dayPanels){
            panel.setBackground(HRSColors.FM_DARK_GREEN);
        }

        for (int i = 0; i < this.dayPanels.size(); i++) {
            JPanel panel = this.dayPanels.get(i);
            LocalDate eventDate = this.date.get(i);
            if (eventDate.equals(this.view.model.getCurrentDate())) {
                panel.setBackground(new Color(0, 100, 0));
            }
        }

    }

    /**
     * Gère l'évênement du bouton "Fermer".
     *
     * @since 0.1
     */
    private void eventCloseButton(){
        this.calendar.saveCalendar();
        this.view.eventCloseButton();
    }

    /**
     * Gère l'évênement du bouton "▶▶".
     *
     * @param days Le nombre de jours à avancer
     *
     * @since 0.2
     */
    private void eventAvanceRapide(int days) {
        this.avanceRapide(days);
    }

    /**
     * Gère l'évênement du bouton "+".
     *
     * @param date La date de l'évènement
     * @param event Le type d'évènement
     *
     * @since 0.2
     */
    private void eventAdd(LocalDate date, CalendarEvent.Event event) {
        CalendarEvent newEvent = new CalendarEvent(event, date);
        this.calendar.addEvent(newEvent);
        this.refreshCalendar();
    }

    /**
     * Gère l'évênement des boutons "▶" et "◀".
     *
     * @param nb Nombre de mois
     * 
     * @since 0.2
     */
    private void eventChangeMonth(int nb) {
        this.viewDate = this.viewDate.plusMonths(nb);
        this.refreshCalendar();
    }

    /**
     * Gère l'évênement du bouton "×".
     *
     * @param newEvent L'évènement à supprimer
     *
     * @since 0.2
     */
    private void eventCancelEvent(CalendarEvent newEvent){
        this.calendar.removeEvent(newEvent);
        this.refreshCalendar();
    }
}
