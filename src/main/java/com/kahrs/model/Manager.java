package com.kahrs.model;

/**
 * Classe Manager implémentant un manager.
 *
 * @author Ruben FOALEM
 *
 * @since 0.1
 *
 * @version 0.1
 */
public class Manager {

    // ==================== ATTRIBUTS ====================

    /** L'id du Manager */private int id;
    /** Le nom du Manager */private final String name;

    /** L'id de l'équipe du Manager */private final Integer team_id;

    /** L'argent du Manager */private Long money;
    /** Le nombre de victoires du Manager */private Integer wins;
    /** Le nombre de Match nuls du Manager */private Integer draws;
    /** Le nombre de defaites du Manager */private Integer loses;

    /** Etat de modification **/private boolean isModified;

    // ================== CONSTRUCTEURS ==================

    /**
     * Constructeur de Manager pour créer un objet avec un id (int), un name
     * (String), un team_id (Integer), un money (Integer), un wins (Integer), un
     * draws (Integer) et un loses (Integer).
     *
     * @param id      L'id du manager
     * @param name    Le nom du Manager
     * @param team_id L'id de sa Team
     * @param money   L'argent du Manager
     * @param wins    Les victoires du Manager
     * @param draws   Les matchs nuls du Manager
     * @param loses   Les défaites du Manager
     *
     * @since 0.1
     */
    public Manager(int id, String name, Integer team_id, Long money, Integer wins, Integer draws, Integer loses) {
        this.id = id;
        this.name = name;
        this.team_id = team_id;
        this.money = money;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;

        this.isModified = false;
    }

    /**
     * Constructeur de Manager pour créer un objet avec un name (String) et un
     * team_id (Integer).
     *
     * @param name    Le nom du manager
     * @param team_id L'id de sa Team
     *
     * @since 0.1
     */
    public Manager(String name, int team_id) {
        this(0, name, team_id, 100000L, 0, 0, 0);

        this.isModified = true;
    }

    // ==================== ACCESSEURS ===================

    /**
     * Getter pour récuperer l'id du Manager.
     *
     * @return L'id
     *
     * @since 0.1
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour définir l'id du Manager.
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
     * Getter pour récuperer le nom du Manager.
     *
     * @return Le nom du Manager
     *
     * @since 0.1
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pour récuperer l'id de la Team.
     *
     * @return L'id de la team
     *
     * @since 0.1
     */
    public Integer getTeamId() {
        return this.team_id;
    }

    /**
     * Getter pour récuperer l'argent du Manager.
     *
     * @return L'argent du Manager
     *
     * @since 0.1
     */
    public Long getMoney() {
        return this.money;
    }

    /**
     * Setter pour définir l'argent du Manager.
     *
     * @param money Le nouvel argent
     *
     * @since 0.1
     */
    public void setMoney(Long money) {
        this.money = Long.max(0L, money);
        this.setModified(true);
    }

    /**
     * Getter pour récuperer les wins du Manager.
     *
     * @return Les wins du Manager
     *
     * @since 0.1
     */
    public Integer getWins() {
        return this.wins;
    }

    /**
     * Setter pour définir les victoires du Manager.
     *
     * @param wins Les nouvelles wins
     *
     * @since 0.1
     */
    public void setWins(Integer wins) {
        this.wins = Integer.max(0, wins);
        this.setModified(true);
    }

    /**
     * Getter pour récuperer les draws du Manager.
     *
     * @return Les draws du Manager
     *
     * @since 0.1
     */
    public Integer getDraws() {
        return this.draws;
    }

    /**
     * Setter pour définir les draws du Manager.
     *
     * @param draws Les nouvelles draws
     *
     * @since 0.1
     */
    public void setDraws(Integer draws) {
        this.draws = Integer.max(0, draws);
        this.setModified(true);
    }

    /**
     * Getter pour récuperer les loses du Manager.
     *
     * @return Les loses du Manager
     *
     * @since 0.1
     */
    public Integer getLoses() {
        return this.loses;
    }

    /**
     * Setter pour définir les loses du Manager.
     *
     * @param loses Les nouvelles loses
     *
     * @since 0.1
     */
    public void setLoses(Integer loses) {
        this.loses = Integer.max(0, loses);
        this.setModified(true);
    }

    /**
     * Getter pour récuperer l'état de modification.
     *
     * @return Etat de modification
     *
     * @since 0.2
     */
    public boolean isModified() {
        return this.isModified;
    }

    /**
     * Setter pour définir l'état de modification.
     *
     * @param modified  La nouvel état de modification
     *
     * @since 0.1
     */
    public void setModified(boolean modified) {
        this.isModified = modified;
    }
}
