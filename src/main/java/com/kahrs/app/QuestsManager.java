package com.kahrs.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.kahrs.model.Quest;

/**
 * Classe QuestsManager permettant de gérer les quêtes.
 * 
 * @author Hugo ARNAUD
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class QuestsManager {

    // ==================== ATTRIBUTS ====================

    /** Fichier de sauvegarde locale des Quests */
    private static final File QUESTS_FILE = new File("quests.json");

    /** Outil pour lire et écrire dans le fichier de sauvegarde */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /** Liste des Quests */
    private static ArrayList<Quest> quests = new ArrayList<>();

    // ================== CONSTRUCTEUR ==================

    /** Méthode d'initialisation des Quests
     * 
     * @since 0.2
     */
    static {
        QuestsManager.createQuests();
    }

    /** Constructeur privé pour résoudre warning de Javadoc */
    private QuestsManager() {
    }

    /**
     * Méthode pour créer les Quests
     * 
     * @since 0.2
     */
    public static void createQuests() {
        if (!QUESTS_FILE.exists()) {
            quests.add(new Quest("1", Quest.QuestCategory.PLAYERS_BUY, 0, 2, 1500));
            quests.add(new Quest("2", Quest.QuestCategory.MATCH_PLAY, 0, 1, 1000));
            quests.add(new Quest("3", Quest.QuestCategory.QUESTS_FINISH, 0, 1, 1000));
            quests.add(new Quest("4", Quest.QuestCategory.CHAMPIONSHIP_PLAY, 0, 1, 5000));
            quests.add(new Quest("5", Quest.QuestCategory.CHAMPIONSHIP_WIN, 0, 1, 15000));
            quests.add(new Quest("6", Quest.QuestCategory.TRAINING, 0, 5, 2000));
            quests.add(new Quest("7", Quest.QuestCategory.UPGRADE_BUILDING, 0, 3, 5000));
            QuestsManager.saveQuests();
        } else {
            try (FileReader reader = new FileReader(QuestsManager.QUESTS_FILE)) {
                Type listType = new TypeToken<ArrayList<Quest>>() {}.getType();
                quests = GSON.fromJson(reader, listType);
                
                if (quests == null) {
                    quests = new ArrayList<>();
                }
            } catch (IOException e) {
                System.err.println("[ERREUR] Problème lors de la création des quêtes");
            }
        }
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour recupérer les Quests
     * 
     * @return La liste des Quests
     * 
     * @since 0.2
     */
    public static ArrayList<Quest> getQuests() {
        return QuestsManager.quests;
    }

    /**
     * Méthode pour savoir si une Quest est complète
     * 
     * @return Un boolean pour savoir si une Quest est complétée
     * 
     * @since 0.2
     */
    public static boolean isQuestCompleted() {
        for (Quest quest : QuestsManager.quests) {
            if (quest.isCompleted()) {
                return true;
            }
        }

        return false;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour sauvegarder les Quests
     * 
     * @since 0.2
     */
    public static void saveQuests() {
        try (FileWriter writer = new FileWriter(QUESTS_FILE)) {
            GSON.toJson(quests, writer);
        } catch (IOException e) {
            System.err.println("[ERREUR] Problème lors de l'enregistrement des quêtes");
        }
    }

    /**
     * Méthode gérant l'avancement des Quests
     * 
     * @param questCategory La catégorie de la Quest
     * @param nb            L'avancement de la Quest
     * 
     * @since 0.2
     */
    public static void advanceQuest(Quest.QuestCategory questCategory, int nb) {
        for (Quest quest : QuestsManager.quests) {
            if (questCategory == quest.getCategory()) {
                quest.addAdvancement(nb);
                QuestsManager.saveQuests();
            }
        }
    }
}
