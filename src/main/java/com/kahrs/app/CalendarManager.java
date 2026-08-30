package com.kahrs.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kahrs.model.CalendarEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Classe CalendarManager permettant de sauvegarder et charger le calendrier, date du jeu
 *
 * @author Ruben Foalem
 *
 * @since 0.2
 *
 * @version 0.2
 */
public class CalendarManager {

    /** Fichier de configuration */
    private static final File CALENDAR_FILE = new File("calendar.json");
    /** Outil pour lire et écrire */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    /** Constructeur privé pour résoudre warning de Javadoc */
    private CalendarManager() {
    }

    /**
     * Méthode permettant de rcupérer la configuration actuelle.
     *
     * @return La configuration actuelle
     *
     *
     * @since 0.1
     */
    private static JsonObject getConfig() {
        if (!CALENDAR_FILE.exists()) {
            return new JsonObject();
        }
        try (FileReader reader = new FileReader(CalendarManager.CALENDAR_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);

            if (json != null) {
                return json;
            }
        } catch (IOException e) {
            System.err.println("[ERREUR] Impossible de récupérer le fichier calendar.json");
        }
        return new JsonObject();
    }

    /**
     * Méthode permettant de sauvegarder la configuration actuelle.
     *
     * @param json La configuration à sauvegarder
     *
     *
     * @since 0.1
     */
    private static void setConfig(JsonObject json) {
        try (FileWriter writer = new FileWriter(CalendarManager.CALENDAR_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            System.err.println("[ERREUR] Impossible d'écrire dans le fichier calendar.json");
        }
    }

    /**
     * Méthode permettant de sauvegarder le calendrier.
     *
     * @param events Le calendrier
     *
     * @since 0.2
     */
    public static void setCalendarEvents(ArrayList<CalendarEvent> events) {
        JsonObject json = CalendarManager.getConfig();
        JsonArray array = new JsonArray();
        for (CalendarEvent event : events) {
            JsonObject saveEvent = new JsonObject();
            saveEvent.addProperty("type", String.valueOf(event.getEvent()));
            saveEvent.addProperty("date",event.getDate().toString());
            array.add(saveEvent);
        }
        json.add("calendarEvents", array);
        CalendarManager.setConfig(json);
    }


    /**
     * Méthode permettant de récupérer le calendrier.
     *
     * @return Le calendrier du jeu
     *
     * @since 0.2
     */
    public static ArrayList<CalendarEvent> getCalendarEvents() {
        ArrayList<CalendarEvent> events = new ArrayList<>();
        JsonObject json = CalendarManager.getConfig();
        if (json.has("calendarEvents")) {
            JsonArray array = json.getAsJsonArray("calendarEvents");
            for (int i = 0; i < array.size(); i++) {
                JsonObject saveEvent = array.get(i).getAsJsonObject();
                LocalDate date = LocalDate.parse(saveEvent.get("date").getAsString());
                CalendarEvent.Event type = CalendarEvent.Event.valueOf(saveEvent.get("type").getAsString());
                events.add(new CalendarEvent(type, date));
            }
        }
        return events;
    }

    /**
     * Méthode permettant de sauvegarder la date du jeu.
     *
     * @param dateGame Le date du jeu
     *
     * @since 0.2
     */
    public static void setDateGame(LocalDate dateGame) {
        JsonObject json = CalendarManager.getConfig();
        json.addProperty("dateGame",dateGame.toString());
        CalendarManager.setConfig(json);
    }

    /**
     * Méthode permettant de récupérer la date du jeu.
     *
     * @return Le date du jeu
     *
     * @since 0.2
     */
    public static LocalDate getDateGame() {
        JsonObject json = CalendarManager.getConfig();
        if(json.has("dateGame")) {
            return LocalDate.parse(json.get("dateGame").getAsString());
        } else {
            return LocalDate.of(2026,8,1);
        }
    }
}
