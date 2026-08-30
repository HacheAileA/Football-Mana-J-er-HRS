package com.kahrs.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.kahrs.model.Championship;

/**
 * Classe ChampionshipManager permettant de sauvegarder et charger le Championnat
 * 
 * @author Hugo ARNAUD
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class ChampionshipManager {

    // ==================== ATTRIBUTS ====================

    /** Fichier de sauvegarde locale du Championnat */
    private static final File CHAMPIONSHIP_FILE = new File("championship.json");

    /** Outil pour lire et écrire dans le fichier de sauvegarde */
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
        .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
        .setPrettyPrinting().create();

    /** Championnat du jeu */
    private static Championship championship;

    // ================== CONSTRUCTEUR ==================

    /** Méthode d'initialisation du Championnat
     * 
     * @since 0.2
     */
    static {
        ChampionshipManager.loadChampionship();
    }

    /** Constructeur privé pour résoudre warning de Javadoc */
    private ChampionshipManager() {
    }

    /**
     * Méthode pour charger le Championnat
     * 
     * @since 0.2
     */
    private static void loadChampionship() {
        if (!CHAMPIONSHIP_FILE.exists()) {
            championship = new Championship();
            ChampionshipManager.saveChampionship();
        } else {
            try (FileReader reader = new FileReader(ChampionshipManager.CHAMPIONSHIP_FILE)) {
                championship = GSON.fromJson(reader, Championship.class);
                
                if (championship == null) {
                    championship = new Championship();
                }
            } catch (IOException e) {
                System.err.println("[ERREUR] Problème lors du chargement du championnat: " + e.getMessage());
                championship = new Championship();
            }
        }
    }

    // ==================== ACCESSEUR ===================

    /**
     * Getter pour recupérer le Championnat
     * 
     * @return Le Championnat
     * 
     * @since 0.2
     */
    public static Championship getChampionship() {
        return ChampionshipManager.championship;
    }

    // ==================== METHODE =====================

    /**
     * Méthode pour sauvegarder le Championnat
     * 
     * @since 0.2
     */
    public static void saveChampionship() {
        try (FileWriter writer = new FileWriter(CHAMPIONSHIP_FILE)) {
            GSON.toJson(championship, writer);
        } catch (IOException e) {
            System.err.println("[ERREUR] Problème lors de la sauvegarde du championnat: " + e.getMessage());
        }
    }
}
