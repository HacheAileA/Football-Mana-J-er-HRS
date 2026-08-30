package com.kahrs.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.kahrs.model.Infrastructure;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe UserConfig permettant de gérer les paramètres de jeu de l'utilisateur.
 * 
 * @author Hugo ARNAUD
 * @author Ruben FOALEM
 * @author Sofyane HARISSE
 * 
 * 
 * @since 0.1
 * 
 * @version 0.2
 */
public class UserConfig {

    // ==================== ATTRIBUTS ====================

    /** Fichier de configuration */
    private static final File USER_CONFIG_FILE = new File("user.json");
    /** Outil pour lire et écrire */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /** Constructeur privé pour résoudre warning de Javadoc */
    private UserConfig() {
    }

    // ==================== ACCESSEURS ===================

    /**
     * Méthode permettant de rcupérer la configuration actuelle.
     * 
     * @return La configuration actuelle
     * 
     * 
     * @since 0.1
     */
    public static JsonObject getConfig() {
        if (!USER_CONFIG_FILE.exists()) {
            return new JsonObject();
        }
        try (FileReader reader = new FileReader(UserConfig.USER_CONFIG_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);

            if (json != null) {
                return json;
            }
        } catch (IOException e) {
            System.err.println("[ERREUR] Impossible de récupérer la configuration de l'utilisateur");
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
        try (FileWriter writer = new FileWriter(UserConfig.USER_CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            System.err.println("[ERREUR] Impossible d'écrire dans la configuration de l'utilisateur");
        }
    }

    /**
     * Méthode permettant de récupérer l'id du Manager de l'utilisateur se situant
     * dans un fichier local.
     * 
     * @return L'id local
     * 
     * 
     * @since 0.1
     */
    public static int getManagerId() {
        JsonObject json = UserConfig.getConfig();

        if (json.has("ManagerId")) {
            return json.get("ManagerId").getAsInt();
        }
        return -1;
    }

    /**
     * Méthode permettant de sauvegarder l'id du Manager de l'utilisateur.
     * 
     * @param manager_id L'id du Manager
     * 
     * 
     * @since 0.1
     */
    public static void setManagerId(int manager_id) {
        JsonObject json = UserConfig.getConfig();
        json.addProperty("ManagerId", manager_id);

        UserConfig.setConfig(json);
    }

    /**
     * Méthode permettant de récupérer la langue de l'utilisateur.
     * 
     * @return La langue
     * 
     * 
     * @since 0.1
     */
    public static String getLanguage() {
        JsonObject json = UserConfig.getConfig();

        if (json.has("preferences")) {
            JsonObject preferences = json.getAsJsonObject("preferences");
            if (preferences.has("language")) {
                return preferences.get("language").getAsString();
            }
        }
        return HRSLanguages.FRENCH[0];
    }

    /**
     * Méthode permettant de sauvegarder la langue.
     * 
     * @param language La langue
     * 
     * 
     * @since 0.1
     */
    public static void setLanguage(String language) {
        JsonObject json = UserConfig.getConfig();
        JsonObject preferences = json.getAsJsonObject("preferences");
        preferences.addProperty("language", language);

        UserConfig.setConfig(json);
    }

    /**
     * Méthode permettant de récupérer le volume du jeu.
     * 
     * @return Le volume du jeu
     * 
     * 
     * @since 0.2
     */
    public static int getVolume() {
        JsonObject json = UserConfig.getConfig();

        if (json.has("preferences")) {
            JsonObject preferences = json.getAsJsonObject("preferences");
            if (preferences.has("volume")) {
                return preferences.get("volume").getAsInt();
            }
        }

        return 0;
    }

    /**
     * Méthode permettant de sauvegarder le volume du jeu.
     * 
     * @param volume Le volume du jeu
     * 
     * 
     * @since 0.2
     */
    public static void setVolume(int volume) {
        JsonObject json = UserConfig.getConfig();
        JsonObject preferences = json.getAsJsonObject("preferences");
        preferences.addProperty("volume", volume);

        UserConfig.setConfig(json);
    }

    /**
     * Méthode permettant de récupérer les volume des bruitages.
     * 
     * @return Le volume des bruitages
     * 
     * 
     * @since 0.2
     */
    public static int getSoundEffects() {
        JsonObject json = UserConfig.getConfig();

        if (json.has("preferences")) {
            JsonObject preferences = json.getAsJsonObject("preferences");
            if (preferences.has("soundEffects")) {
                return preferences.get("soundEffects").getAsInt();
            }
        }

        return 0;
    }

    /**
     * Méthode permettant de sauvegarder le volume des bruitages.
     * 
     * @param sound_effects Le volume des bruitages
     * 
     * 
     * @since 0.2
     */
    public static void setSoundEffects(int sound_effects) {
        JsonObject json = UserConfig.getConfig();
        JsonObject preferences = json.getAsJsonObject("preferences");
        preferences.addProperty("soundEffects", sound_effects);

        UserConfig.setConfig(json);
    }

    /**
     * Méthode pour récupérer la liste des ID des titulaires
     * 
     * @return La liste des ID des titulaires
     * 
     * @since 0.2
     */
    public static ArrayList<Integer> getStarters() {
        ArrayList<Integer> starters = new ArrayList<>();
        JsonObject json = UserConfig.getConfig();
        if (json.has("starters")) {
            JsonArray array = json.getAsJsonArray("starters");
            for (int i = 0; i < array.size(); i++) {
                starters.add(array.get(i).getAsInt());
            }
        }
        return starters;
    }

    /**
     * Méthode pour sauvegarder la liste des ID des titulaires
     * 
     * @param starters La liste des titulaires
     * 
     * @since 0.2
     */
    public static void setStarters(ArrayList<Integer> starters) {
        JsonObject json = UserConfig.getConfig();
        JsonArray array = new JsonArray();
        for (Integer id : starters) {
            array.add(id);
        }
        json.add("starters", array);
        UserConfig.setConfig(json);
    }

    /**
     * Méthode pour récupérer le niveau d'une infrastructure
     * 
     * @param type L'infrastrcture à récupérer
     * 
     * @return Le niveau de l'infrastructure
     * 
     * @since 0.2
     */
    public static int getInfrastructureLevel(Infrastructure.InfrastructureType type) {
        JsonObject json = UserConfig.getConfig();

        if (json.has("infrastructures")) {
            JsonObject infrastructures = json.getAsJsonObject("infrastructures");
            switch (type) {
                case RACE_TRACK:
                    if (infrastructures.has("raceTrackLevel")) {
                        return infrastructures.get("raceTrackLevel").getAsInt();
                    }
                    break;
                case FIELD:
                    if (infrastructures.has("fieldLevel")) {
                        return infrastructures.get("fieldLevel").getAsInt();
                    }
                    break;
                case CAGE:
                    if (infrastructures.has("cageLevel")) {
                        return infrastructures.get("cageLevel").getAsInt();
                    }
                    break;
                default:
                    break;
            }
        }

        return 1;
    }

    /**
     * Méthode pour sauvegarder le niveau d'une infrastructure
     * 
     * @param infrastructure L'infrastrcture à sauvegarder
     * 
     * @since 0.2
     */
    public static void setInfrastructureLevel(Infrastructure infrastructure) {
        JsonObject json = UserConfig.getConfig();
        if (!json.has("infrastructures")) {
            json.add("infrastructures", new JsonObject());
        }
        
        JsonObject infrastructures = json.getAsJsonObject("infrastructures");
        switch (infrastructure.getInfrastructureType()) {
            case RACE_TRACK:
                infrastructures.addProperty("raceTrackLevel", infrastructure.getLevel());
                break;
            case FIELD:
                infrastructures.addProperty("fieldLevel", infrastructure.getLevel());
                break;
            case CAGE:
                infrastructures.addProperty("cageLevel", infrastructure.getLevel());
                break;
            default:
                break;
        }
        
        UserConfig.setConfig(json);
    }
}
