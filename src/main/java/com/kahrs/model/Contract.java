package com.kahrs.model;

/**
 * Classe Contract implémentant un contrat.
 *
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class Contract {

    // ==================== ATTRIBUTS ====================

    /** Id du Contract*/private int id;
    
    /** Id de la Team */private final Integer team_id;
    /** Id du Player */private final Integer player_id;
    
    /** Durée restante du Contract */private Integer duration;
    /** Durée initiale d'un Contract */public static final Integer DURATION_INIT = 10;
    
    /** Prix de renouvellement */private Integer price;

    /**L'état de modification du contract **/private boolean isModified;

    // ================== CONSTRUCTEURS ==================

    /**
     * Constructeur de Contract pour créer un objet avec un id (int), un team_id
     * (Integer), un player_id (Integer), une duration (Integer) et un price
     * (Integer).
     *
     * @param id        L'id du Contract
     * @param team_id   L'id de la Team
     * @param player_id L'id du Player
     * @param duration  La durée du Contract
     * @param price     Le prix du Contract
     * 
     * @since 0.1
     */
    public Contract(int id, Integer team_id, Integer player_id, Integer duration, Integer price) {
        this.id = id;
        this.team_id = team_id;
        this.player_id = player_id;
        this.duration = duration;
        this.price = price;

        this.isModified = false;
    }

    /**
     * Constructeur de Contract pour créer un objet avec un team_id (Integer) et un
     * player_id (Integer).
     *
     * @param team_id   L'id de la Team
     * @param player_id L'id du Player
     * 
     * @since 0.1
     */
    public Contract(Integer team_id, Integer player_id) {
        this(0, team_id, player_id, Contract.DURATION_INIT, Contract.DURATION_INIT * 100);

        this.isModified = true;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer l'id du Contract.
     *
     * @return L'id
     *
     * @since 0.1
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter pour définir l'id du Contract.
     *
     * @param id Le nouvel id
     *
     * @since 0.1
     */
    public void setId(int id) {
        this.id = id;
        this.setModified(true);
    }

    /**
     * Getter pour récuperer l'id de la Team.
     *
     * @return L'id
     *
     * @since 0.1
     */
    public Integer getTeamId() {
        return this.team_id;
    }

    /**
     * Getter pour récuperer l'id du Player.
     *
     * @return L'id
     *
     * @since 0.1
     */
    public Integer getPlayerId() {
        return this.player_id;
    }

    /**
     * Getter pour récuperer la durée du Contract.
     *
     * @return La duration
     *
     * @since 0.1
     */
    public Integer getDuration() {
        return this.duration;
    }

    /**
     * Setter pour définir la durée du Contract.
     *
     * @param duration La nouvelle durée
     *
     * @since 0.1
     */
    public void setDuration(int duration) {
        this.duration = Math.max(0, duration);
        this.setModified(true);
    }

    /**
     * Méthode pour réduire la durée du contract
     *
     * @param nb La valeur de réduction
     *
     * @since 0.1
     */
    public void reduceDuration(int nb) {
        this.setDuration(this.duration - nb);
    }

    /**
     * Getter pour récuperer le prix du Contract.
     *
     * @return Le prix
     *
     * @since 0.1
     */
    public Integer getPrice() {
        this.calculPrice();
        return this.price;
    }

    /**
     * Méthode pour calculer le prix du Contract.
     *
     * @since 0.1
     */
    private void calculPrice() {
        int cost = 1000;
        this.price = Integer.max(cost, this.duration * cost);
    }

    /**
     * Getter pour récuperer l'état de modification du contract.
     *
     * @return l'état de modification du contract
     *
     * @since 0.1
     */
    public boolean isModified() {
        return this.isModified;
    }

    /**
     * Setter pour définir l'état de modification du contract
     *
     * @param modified Le nouveau état de modification du contract
     *
     * @since 0.1
     */
    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    // ==================== METHODES =====================

    /**
     * Vérifie si le contrat est expiré (plus de matchs restants).
     * 
     * @return true si la durée est à 0, false sinon.
     * @since 0.2
     */
    public boolean isExpired() {
        return this.duration <= 0;
    }
}
