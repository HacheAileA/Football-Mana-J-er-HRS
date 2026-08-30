package com.kahrs.model;

import com.kahrs.app.QuestsManager;

/**
 * Classe Quest permettant de représenter une quête.
 *
 * @author Hugo ARNAUD
 *
 * @since 0.2
 *
 * @version 0.1
 */
public class Quest {

    /** Type de quêtes
     * 
     * @since 0.2
     */
    public enum QuestCategory {
        /** Jouer un match de championnat */CHAMPIONSHIP_PLAY,
        /** Gagner un championnat */CHAMPIONSHIP_WIN,
        /** Jouer un match */MATCH_PLAY,
        /** Gagner un match */MATCH_WIN,
        /** Acheter un joueur */PLAYERS_BUY,
        /** Finir des quêtes */QUESTS_FINISH,
        /** Faire des entraînements */TRAINING,
        /** Améliorer une infrastructure */UPGRADE_BUILDING
    }

    // ==================== ATTRIBUTS ====================

    /** Id de la quête **/
    private String id;
    /** Type de la quête **/
    private QuestCategory category;
    /** Valeur d'avancement de la quête **/
    private int advancement;
    /** Nombre d'objectifs **/
    private int objective;
    /** Récompense **/
    private int reward;

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de Quête
     *
     * @param id       L'id de la quête
     * @param category     Type de la quête
     * @param advancement Valeur d'avancement
     * @param objective   Nombre d'objectifs
     * @param reward     Récompense
     *
     * @since 0.2
     */
    public Quest(String id, QuestCategory category, int advancement, int objective, int reward) {
        this.id = id;
        this.category = category;
        this.advancement = advancement;
        this.objective = objective;
        this.reward = reward;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer l'id de la quête.
     *
     * @return L'id de la quête
     *
     * @since 0.2
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter pour récuperer le type de la quête.
     *
     * @return Type de la quête
     *
     * @since 0.2
     */
    public QuestCategory getCategory() {
        return this.category;
    }

    /**
     * Getter pour récuperer la valeur d'avancement de la quête.
     *
     * @return Valeur d'avancement de la quête
     *
     * @since 0.2
     */
    public int getAdvancement() {
        return this.advancement;
    }

    /**
     * Getter pour récuperer l'id de la quête.
     *
     * @return L'id de la quête
     *
     * @since 0.2
     */
    public int getObjective() {
        return this.objective;
    }

    /**
     * Getter pour récuperer l'id de la quête.
     *
     * @return L'id de la quête
     *
     * @since 0.2
     */
    public int getReward() {
        return this.reward;
    }

    /**
     * Méthode qui vérifie que une quête est complétée.
     *
     * @return boolean
     *
     * @since 0.2
     */
    public boolean isCompleted() {
        return this.advancement == this.objective;
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour augmenter la valeur d'avancement de la quête.
     *
     * @param nb augmentation
     *
     * @since 0.2
     */
    public void addAdvancement(int nb) {
        this.advancement += nb;
    }

    /**
     * Méthode pour augmenter le niveau de la quête
     *
     * @since 0.2
     */
    public void levelUp() {
        this.advancement = this.advancement - this.objective;
        this.objective *= 2;
        this.reward *= 1.5;

        QuestsManager.saveQuests();
    }
}
